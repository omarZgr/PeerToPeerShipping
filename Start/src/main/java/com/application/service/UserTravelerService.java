package com.application.service;


import com.application.common.PageResponse;
import com.application.entity.ConfirmationEntity;
import com.application.entity.ItemEntity;
import com.application.entity.MeetEntity;
import com.application.entity.UserEntity;
import com.application.enums.EmailStatus;
import com.application.front.request.user.traveler.ConfirmTravelerShippingRequest;
import com.application.front.request.user.traveler.ItemTravelerRequest;
import com.application.front.request.user.traveler.ValidMeetingRequest;
import com.application.front.response.sender.AllItemsResponse;
import com.application.front.response.traveler.ItemTravelerResponse;
import com.application.repository.ConfirmationRepository;
import com.application.repository.ItemRepository;
import com.application.repository.MeetRepository;
import com.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserTravelerService {

    private final ItemRepository itemRepository ;
    private final MeetRepository meetRepository ;
    private final UserRepository userRepository ;
    private final ConfirmationRepository confirmationRepository ;


    private final EmailService emailService ;




    public PageResponse<ItemTravelerResponse> traveler(int page, int size, ItemTravelerRequest itemTravelerRequest) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending()) ;
        Page<ItemEntity> items = itemRepository.findAllByVilleDepartAndVilleDestinationAndIsEnable(
                pageable,
                itemTravelerRequest.getVilleDepart(),
                itemTravelerRequest.getVilleDestination(),true
        ) ;

        List<ItemTravelerResponse> itemEntityList = new ArrayList<>() ;

        for (ItemEntity current:items)
        {
            log.warn("AM HERE");
            itemEntityList.add(new ItemTravelerResponse(
                    current.getId(),
                    current.getNom(),
                    current.getDescription(),
                    current.getType(),
                    current.getPhoto(),
                    current.getVilleDepart(),
                    current.getVilleDestination(),
                    current.getMontant()
            )) ;
        }



        return new PageResponse<>(
                itemEntityList,
                items.getNumber(),
                items.getSize(),
                items.getTotalElements(),
                items.getTotalPages(),
                items.isFirst(),

                items.isLast()) ;

    }

    public void askMeeting(long itemId, Authentication connectedUser)
    {
        Optional<ItemEntity> itemOptional = itemRepository.findById(itemId) ;
        UserEntity userEntity = ((UserEntity) connectedUser.getPrincipal());


        if (!itemOptional.isPresent())
        {
            log.warn("This itemId not exist");
            throw new RuntimeException("This itemId not exist") ;
        }

        Optional<MeetEntity> optionalMeet = meetRepository.findByItemId(itemId) ;

        if (!optionalMeet.isPresent())
        {
            //// sift msg ;
            sendEmail_ASk(userEntity,itemId) ;

        }
        else {
            boolean isConfirm = optionalMeet.get().isConfirmMeeting() ;

            if (!isConfirm)
            {
                // siftt
                sendEmail_ASk(userEntity,itemId) ;
            }
            else {
                log.warn("You can't meeting for this item , already confirm");
                throw new RuntimeException("You can't meeting for this item , already confirm") ;

            }
        }
    }

    public void meetingDone_Traveler(ValidMeetingRequest validMeetingRequest, Authentication connectedUser) {

        UserEntity userEntity = ((UserEntity) connectedUser.getPrincipal());

        MeetEntity meetEntity = meetRepository.findById(validMeetingRequest.getMeetId()).get() ;

        meetEntity.setValidTraveler(true);

        if (validMeetingRequest.getImage()!=null)
        {
            String imagePath ;

            if (validMeetingRequest.getImage()!=null)
            {
                String  path = userEntity.getFullName() +  "\\traveler\\meetingDone\\" + meetEntity.getItem().getNom() ;
                imagePath = saveImage_Final(validMeetingRequest.getImage(),path)  ;

            }
            else
                imagePath = "" ;
            meetEntity.setPhoto(imagePath);
        }

        meetRepository.save(meetEntity) ;

        emailService.sendEmail(
                userEntity.getEmail()
                ,EmailStatus.ASK_MEETING.getSubject(),"We know you meet Sender and item with you . GOODBYE Sir "+ userEntity.getFullName());


    }

    public void confirmShipping_Traveler(ConfirmTravelerShippingRequest confirmTravelerShippingRequest, Authentication authentication) {

        UserEntity userEntity = ((UserEntity) authentication.getPrincipal());


        Optional<ConfirmationEntity> confirmationOptional = confirmationRepository.findByItemId(confirmTravelerShippingRequest.getItemId()) ;


        if (!confirmationOptional.isPresent())
        {
            ConfirmationEntity confirmationEntity = new ConfirmationEntity() ;

            confirmationEntity.setConfirmTraveler(true);
            confirmationEntity.setDateConfirmationTraveler(LocalDateTime.now());
            confirmationEntity.setDescriptionTraveler(confirmTravelerShippingRequest.getDescription().isEmpty() ? "vide" : confirmTravelerShippingRequest.getDescription());
            confirmationEntity.setUserTraveler(userEntity);
            confirmationEntity.setItem(itemRepository.findById(confirmTravelerShippingRequest.getItemId()).get());




            confirmationRepository.save(confirmationEntity) ;
        }
        else
        {

            confirmationOptional.get().setConfirmTraveler(true);
            confirmationOptional.get().setDateConfirmationTraveler(LocalDateTime.now());
            confirmationOptional.get().setDescriptionTraveler(confirmTravelerShippingRequest.getDescription().isEmpty() ? "vide" : confirmTravelerShippingRequest.getDescription());
            confirmationOptional.get().setUserTraveler(userEntity);
            confirmationOptional.get().setItem(itemRepository.findById(confirmTravelerShippingRequest.getItemId()).get());


            confirmationRepository.save(confirmationOptional.get()) ;

        }


        emailService.sendEmail(
                userEntity.getEmail()
                ,EmailStatus.ConfirmationDone.getSubject(), "Hello "+ userEntity.getFullName() +" ,Your are confirmed Shipped.");






    }

    private void sendEmail_ASk(UserEntity userEntity, long itemId)
    {
        UserEntity userEntitySender = userRepository.findByItemsId(itemId) ;
        String message = "HELLO "
                + userEntitySender.getFullName()
                + " , AM " + userEntity.getFullName()
                + " . And I want to meet you for this item "
                + itemRepository.findById(itemId).get().getNom()
                +" MERCI" ;
        emailService.sendEmail(
                userEntitySender.getEmail()
                , EmailStatus.ASK_MEETING.getSubject(),message);

        MeetEntity meetEntityCreated = new MeetEntity() ;

        meetEntityCreated.setItem(itemRepository.findById(itemId).get());
        meetEntityCreated.setUserTraveler(userEntity);
        meetEntityCreated.setDateAsked(LocalDateTime.now());
        meetEntityCreated.setConfirmMeeting(false);
        meetEntityCreated.setUserSender(itemRepository.findById(itemId).get().getUser());

        meetRepository.save(meetEntityCreated) ;
    }

    private String saveImage_Final(MultipartFile image,String subPath) {
        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image file is required.");
        }
        try {
            // Create the path using the provided directory and fullname
            String directoryPath = "C:\\Users\\samsung\\Desktop\\SpringProjects\\Peer-to-Peer Shipping-project\\data\\" + subPath;
            Path directory = Paths.get(directoryPath);

            // Create the directory if it doesn't exist
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // Create the file path for the image
            String imagePath = directoryPath + "\\" + image.getOriginalFilename();
            Path path = Paths.get(imagePath);

            // Save the image file to the specified path
            Files.write(path, image.getBytes());

            return imagePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }


}
