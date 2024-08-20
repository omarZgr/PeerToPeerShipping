package com.application.service;


import com.application.front.request.user.sender.*;
import com.application.common.PageResponse;

import com.application.entity.*;
import com.application.enums.EmailStatus;
import com.application.front.response.sender.AllAskResponse;
import com.application.front.response.sender.AllItemsResponse;
import com.application.enums.StatusItem;
import com.application.handler.exception.InvalidEmailException;
import com.application.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserSenderService {

    private final ItemRepository itemRepository ;
    private final MeetRepository meetRepository ;
    private final ConfirmationRepository confirmationRepository ;
    private final CommentaireRepository commentaireRepository ;
    private final UserRepository userRepository ;
    private final ReclamationRepository reclamationRepository ;



    private final EmailService emailService ;

    private final PasswordEncoder passwordEncoder;





    public void sender(ItemSenderRequest itemSenderRequest, Authentication connectedUser) {

        UserEntity userEntity = ((UserEntity) connectedUser.getPrincipal());

        ItemEntity itemEntity = ItemEntity.builder()
                .nom(itemSenderRequest.getNom())
                .description(itemSenderRequest.getDescription())
                .type(itemSenderRequest.getType())
                .villeDepart(itemSenderRequest.getVilleDepart())
                .villeDestination(itemSenderRequest.getVilleDestination())
                .montant(itemSenderRequest.getMontant())
                .statusItem(StatusItem.WAITING)
                .isEnable(true)
                .user(userEntity)

                .build() ;

        MultipartFile image = itemSenderRequest.getPhoto() ;
        if (image!=null)
        {
            String fullNmae = userEntity.getFullName() ;
            String path = fullNmae +  "\\sender\\items\\" + itemSenderRequest.getNom() ;
            String imagePath = saveImage_Final(image,path)  ;
            userEntity.setImage(imagePath);

        }

        itemRepository.save(itemEntity)  ;



        log.warn("Add item Success");





    }
    public PageResponse<AllItemsResponse> getAllItmes(int page, int size, Authentication connectedUser) {

        UserEntity userEntity = ((UserEntity) connectedUser.getPrincipal());

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending()) ;

        Page<ItemEntity> itemResponsePage  = itemRepository.findAllByUserId(pageable, userEntity.getId()) ;
        List<AllItemsResponse> itemResponseList = new ArrayList<>();

        for (ItemEntity current:itemResponsePage)
        {
            log.warn("AM HERE");
            itemResponseList.add(new AllItemsResponse(
                    current.getId(),
                    current.getNom(),
                    current.getDescription(),
                    current.getType(),
                    current.getPhoto(),
                    current.getVilleDepart(),
                    current.getVilleDestination(),
                    current.getMontant(),
                    current.getStatusItem(),
                    current.isEnable()
            )) ;
        }




        return new PageResponse<>(
                itemResponseList,
                itemResponsePage.getNumber(),
                itemResponsePage.getSize(),
                itemResponsePage.getTotalElements(),
                itemResponsePage.getTotalPages(),
                itemResponsePage.isFirst(),

                itemResponsePage.isLast()) ;

    }
    public PageResponse<AllAskResponse> getAllAsks(int page, int size, Authentication connectedUser) {

        UserEntity userEntity = ((UserEntity) connectedUser.getPrincipal());

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending()) ;

        Page<MeetEntity>  meetResponsePage  = meetRepository.findAllByUserSenderEquals(pageable, userEntity) ;

        List<AllAskResponse> meetResponseList = new ArrayList<>() ;


        for (MeetEntity current:meetResponsePage)
        {
            log.warn("AM HERE");
            meetResponseList.add(new AllAskResponse(
                    current.getId(),
                    current.getDateAsked(),
                    current.getUserTraveler().getFullName(),
                    current.getUserTraveler().getEmail(),
                    current.getUserTraveler().getTel(),
                    current.getUserTraveler().getImage(),
                    current.getItem().getPhoto()))   ;

        }


        return new PageResponse<>(
                meetResponseList,
                meetResponsePage.getNumber(),
                meetResponsePage.getSize(),
                meetResponsePage.getTotalElements(),
                meetResponsePage.getTotalPages(),
                meetResponsePage.isFirst(),

                meetResponsePage.isLast()) ;



    }

    public PageResponse<AllAskResponse> getAllEnableAsks(int page, int size, Authentication connectedUser) {

        UserEntity userEntity = ((UserEntity) connectedUser.getPrincipal());

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending()) ;

        Page<MeetEntity>  meetResponsePage  = meetRepository.findAllByUserSenderEqualsAndConfirmMeeting(pageable, userEntity,false) ;

        List<AllAskResponse> meetResponseList = new ArrayList<>() ;


        for (MeetEntity current:meetResponsePage)
        {
            log.warn("AM HERE");
            meetResponseList.add(new AllAskResponse(
                    current.getId(),
                    current.getDateAsked(),
                    current.getUserTraveler().getFullName(),
                    current.getUserTraveler().getEmail(),
                    current.getUserTraveler().getTel(),
                    current.getUserTraveler().getImage(),
                    current.getItem().getPhoto()))   ;

        }






        return new PageResponse<>(
                meetResponseList,
                meetResponsePage.getNumber(),
                meetResponsePage.getSize(),
                meetResponsePage.getTotalElements(),
                meetResponsePage.getTotalPages(),
                meetResponsePage.isFirst(),

                meetResponsePage.isLast()) ;



    }

    public void confirmMeeting(ConfirmMeetingRequest confirmMeetingRequest, Authentication connectedUser) throws ParseException {

        UserEntity userEntity = ((UserEntity) connectedUser.getPrincipal());

        Optional<MeetEntity> optionalMeet = meetRepository.findByIdAndConfirmMeeting(confirmMeetingRequest.getMeetId(),false) ;

        if (optionalMeet.isPresent())
        {
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            optionalMeet.get().setDateMeeting(dateFormat.parse(confirmMeetingRequest.getDateMeeting()));
            optionalMeet.get().setConfirmMeeting(true);
            optionalMeet.get().setDateConfirmation(LocalDateTime.now());
            optionalMeet.get().setLocation(confirmMeetingRequest.getLocation());

            meetRepository.save(optionalMeet.get())  ;

            ItemEntity itemEntity =  optionalMeet.get().getItem();
            itemEntity.setEnable(false);
            itemRepository.save(itemEntity) ;

            sendEmail_ConfirmASk(optionalMeet.get().getUserTraveler().getEmail(),optionalMeet.get().getUserTraveler().getFullName(), userEntity,confirmMeetingRequest.getDateMeeting()) ;
            log.warn("We send email success to :" +optionalMeet.get().getUserTraveler().getEmail());

        }
        else
        {
            log.warn("meetId don't exist or already confirm : ");
            throw new RuntimeException("meetId don't exist or already confirm") ;
        }


    }

    public void meetingDone_Sender(long meetId, Authentication connectedUser) {

        UserEntity userEntity = ((UserEntity) connectedUser.getPrincipal());

        MeetEntity meetEntity = meetRepository.findById(meetId).get() ;

        meetEntity.setValidSender(true);

        meetRepository.save(meetEntity) ;

        emailService.sendEmail(
                userEntity.getEmail()
                ,EmailStatus.ASK_MEETING.getSubject(),"We know you meet traveler . GOODBYE Sir "+ userEntity.getFullName());

        //todo handle payment ;

        payment(meetId) ;


    }

    public void confirmShipping_Sender(ConfirmSenderShippingRequest confirmSenderShippingRequest, Authentication authentication) {

        UserEntity userEntity = ((UserEntity) authentication.getPrincipal());

        String imagePath ;

        Optional<ConfirmationEntity> confirmationOptional = confirmationRepository.findByItemId(confirmSenderShippingRequest.getItemId()) ;
        if (confirmSenderShippingRequest.getImage()!=null)
        {
           String  path = userEntity.getFullName() +  "\\sender\\confirmShipping\\" + confirmationOptional.get().getItem().getNom() ;
            imagePath = saveImage_Final(confirmSenderShippingRequest.getImage(),path)  ;

        }
        else
             imagePath = "" ;


        if (!confirmationOptional.isPresent())
        {
            ConfirmationEntity confirmationEntity = ConfirmationEntity.builder()
                    .dateConfirmationSender(LocalDateTime.now())
                    .descriptionSender(!confirmSenderShippingRequest.getDescription().isEmpty() ? confirmSenderShippingRequest.getDescription() : "vide")
                    .confirmSender(true)
                    .item(itemRepository.findById(confirmSenderShippingRequest.getItemId()).get())
                    .photo(imagePath)
                    .userSender(userEntity)
                    .build() ;

            confirmationRepository.save(confirmationEntity) ;
        }
        else
        {
            confirmationOptional.get().setConfirmSender(true);
            confirmationOptional.get().setDateConfirmationSender(LocalDateTime.now());
            confirmationOptional.get().setDescriptionSender(confirmSenderShippingRequest.getDescription());
            confirmationOptional.get().setPhoto(imagePath);
            confirmationOptional.get().setItem(itemRepository.findById(confirmSenderShippingRequest.getItemId()).get());

            confirmationOptional.get().setUserSender(userEntity);
            confirmationRepository.save(   confirmationOptional.get()) ;

        }


        emailService.sendEmail(
                userEntity.getEmail()
                ,EmailStatus.ConfirmationDone.getSubject(), "Hello "+ userEntity.getFullName() +" ,Your item is shipped Successfully .");


        payment_confirm() ;

    }

    public void commenter(CommentaireRequest commentaireRequest, Authentication authentication) {

        UserEntity userEntitySender = ((UserEntity) authentication.getPrincipal());

        Optional<ConfirmationEntity> userTraveler = confirmationRepository.findByUserTravelerIdAndUserSenderIdAndConfirmSender(commentaireRequest.getUserTravelerId(), userEntitySender.getId(),true) ;


        if (userTraveler.isPresent())
        {
            CommentaireEntity commentaireEntity = CommentaireEntity.builder()
                    .commentaire(commentaireRequest.getCommentaire())
                    .rate(commentaireRequest.getRate())
                    .userTraveler(userTraveler.get().getUserTraveler())
                    .build() ;

            commentaireRepository.save(commentaireEntity) ;
            log.warn("Commneataire added");
        }

        else
        {
            log.warn("userTraveler not found for id: "+commentaireRequest.getUserTravelerId());
            log.warn("userSender.getId() not found for id: "+ userEntitySender.getId());
            throw  new RuntimeException("userTraveler not found for id: "+commentaireRequest.getUserTravelerId())  ;
        }

    }

    public void updateCommenataire(CommentaireUpdateRequest commentaireUpdateRequest, Authentication authentication) {

        Optional<CommentaireEntity> optionalCommentaire = commentaireRepository.findById(commentaireUpdateRequest.getCommentId()) ;

        if (optionalCommentaire.isPresent())
        {
            optionalCommentaire.get().setCommentaire(commentaireUpdateRequest.getComment());
            optionalCommentaire.get().setRate(commentaireUpdateRequest.getRate());

            log.warn("Comment update");
            commentaireRepository.save(optionalCommentaire.get()) ;
        }
        else
            throw new RuntimeException("This commentaire not exit")  ;

    }

    public void reclamation(ReclamtionRequest reclamtionRequest, Authentication authentication) {
        UserEntity userEntity = ((UserEntity) authentication.getPrincipal());

        ReclamationEntity reclamationEntity = ReclamationEntity.builder()
                .userTraveler(userRepository.findById(reclamtionRequest.getUserTravelerId()).get())
                .reclamation(reclamtionRequest.getReclamation())
                .userSender(userEntity)
                .build() ;

        reclamationRepository.save(reclamationEntity) ;

        log.warn("We get you reclamtion ");
    }

    public void updateProfile(UpdateProfileRequest updateProfileRequest, Authentication authentication) {
        UserEntity userEntity = ((UserEntity) authentication.getPrincipal());

        Optional<UserEntity> userOptional = userRepository.findByEmail(updateProfileRequest.getEmail()) ;

        boolean isEmailValid = userOptional.get().getId() == userEntity.getId();

        if (isEmailValid)
        {
            String imagePath ;

            if (updateProfileRequest.getPhoto()!=null)
            {
                String  path = userEntity.getFullName()  ;
                imagePath = saveImage_Final(updateProfileRequest.getPhoto(),path)  ;

            }
            else
                imagePath = "" ;
            userOptional.get().setImage(imagePath);
            userOptional.get().setPassword(passwordEncoder.encode(updateProfileRequest.getPassword()));
            userOptional.get().setTel(updateProfileRequest.getTel());
            userOptional.get().setEmail(updateProfileRequest.getEmail());

            userRepository.save(userOptional.get())
            ;        }
        else
        {
            log.warn("This email already used");
            log.warn("Email : "+updateProfileRequest.getEmail());
            log.warn("id : "+ userEntity.getId());
            throw  new InvalidEmailException("This email already used");
        }
    }

    public void desactiveAccount(Authentication authentication) {

        UserEntity userEntity = ((UserEntity) authentication.getPrincipal());

        userEntity.setAccountLocked(true);
        userRepository.save(userEntity)  ;
        log.warn("User desactived");

    }











    private void sendEmail_ConfirmASk(String email, String username, UserEntity userEntitySender, String dateMeeting)
    {
        String message = "HELLO "
                + username
                + " , AM " + userEntitySender.getFullName()
                + " . And Am ready to meet you in : "
                +  dateMeeting
                +" \\n MERCI" ;

        emailService.sendEmail(
                email
                , EmailStatus.ASK_MEETING.getSubject(),message);

    }

    private void payment(long meetId) {
        float price = meetRepository.findById(meetId).get().getItem().getMontant() ;

        emailService.sendEmail(
                meetRepository.findById(meetId).get().getUserSender().getEmail()
                ,EmailStatus.ASK_MEETING.getSubject(),"Price : "+price +" , Success");

    }

    private void payment_confirm() {

        log.warn("We send money to traveler ");

        // todo
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
