package com.application.service;


import com.application.front.request.auth.AuthenticationRequest;
import com.application.front.response.AuthenticationResponse;
import com.application.front.request.auth.ConfirmRequest;
import com.application.front.request.auth.RegisterRequest;
import com.application.entity.UserEntity;
import com.application.enums.EmailStatus;
import com.application.entity.RoleEntity;
import com.application.handler.exception.InvalidEmailException;
import com.application.handler.exception.TokenExpiration;
import com.application.handler.exception.TokenInValidationException;
import com.application.repository.RoleRepository;
import com.application.entity.TokenEntity;
import com.application.repository.TokenRepository;
import com.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {

    private final AuthenticationManager authenticationManager ;
    private final PasswordEncoder passwordEncoder;


    @Value("${application.security.mail.secret-key}")
    private String ALPHANUMERIC_CHARACTERS ;

    @Value("${application.security.mail.lengthCodeValidation}")
    private int lengthCodeValidation ;
    private static final SecureRandom RANDOM = new SecureRandom();




    private final JwtService jwtService ;
    private final UserRepository userRepository ;
    private final RoleRepository roleRepository ;
    private final EmailService emailService ;
    private final TokenRepository tokenRepository ;
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var claims = new HashMap<String,Object>() ;
        UserEntity userEntity =(UserEntity) auth.getPrincipal() ;
        claims.put("fullName", userEntity.getFullName()) ;
        var jwtToken = jwtService.generateToken(claims, userEntity) ;
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build() ;

    }


    @Transactional
    public void register(RegisterRequest registerRequest) throws IOException {

        Optional<UserEntity> userOptional = userRepository.findByEmail(registerRequest.getEmail()) ;

        Optional<RoleEntity> role = roleRepository.findByRoleName("USER") ;

        if (!role.isPresent())
            throw new IllegalArgumentException("ROLE USER was not initialized ") ;



        if(userOptional.isPresent())
            throw new InvalidEmailException("This email is already used") ;


        String base64Image = encodeToBase64(registerRequest.getImage() );

        var user = UserEntity.builder()
                .nom(registerRequest.getNom())
                .prenom(registerRequest.getPrenom())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .tel(registerRequest.getTel())
                .cin(registerRequest.getCin())
                .image(base64Image)

                .roleEntities(List.of(role.get()))

                .isValid(false)
                .isEnabled(false)
                .isAccountLocked(true)
                .build()
                ;

        MultipartFile image = registerRequest.getImage() ;
        if (image!=null)
        {
            String fullNmae = registerRequest.getNom() + " " + registerRequest.getPrenom() ;
            String imagePath = saveImage(image,fullNmae)  ;
          //  user.setImage(imagePath);

        }

        UserEntity userEntityCreated = userRepository.save(user);


        sendEmail(registerRequest.getEmail(), userEntityCreated) ;



    }


    @Async
    protected void sendEmail(String email, UserEntity userEntity) {

        String codeValidation = generateCode(lengthCodeValidation) ;

        TokenEntity tokenEntity = TokenEntity.builder()
                        .token(codeValidation)
                        .isValid(true)
                        .dateExpiration(LocalDateTime.now().plusMinutes(4))
                        .user(userEntity)
                        .build()  ;

        tokenRepository.save(tokenEntity) ;


        emailService.sendEmail(email, EmailStatus.CONFIRMATION_SIGN_UP.getSubject(), EmailStatus.CONFIRMATION_SIGN_UP.getDescription() + codeValidation);


    }

    private   String generateCode(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length());
            code.append(ALPHANUMERIC_CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    private String saveImage(MultipartFile image, String fullname) {
        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image file is required.");
        }
        try {
            // Create the path using the provided directory and fullname
            String directoryPath = "C:\\Users\\samsung\\Desktop\\SpringProjects\\Peer-to-Peer Shipping-project\\data\\" + fullname;
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


    @Transactional
    public void confirm(ConfirmRequest confirmRequest) {

       String email = confirmRequest.getEmail() ;
       String codeValidation = confirmRequest.getCodeValidation() ;

       Optional<TokenEntity> token = tokenRepository.findByTokenAndUserEmail(codeValidation,email) ;

       if (token.isPresent())
       {
           boolean isExpired = LocalDateTime.now().isAfter(token.get().getDateExpiration()) ;

           if (!isExpired)
           {
               log.warn("Success confirmation");

               UserEntity userEntity = token.get().getUser() ;

               userEntity.setValid(true);
               userEntity.setEnabled(true);
               userEntity.setAccountLocked(false); ;

               userRepository.save(userEntity) ;

           }
           else
           {
               log.warn("This token is expired ,we sent other token");
               sendEmail(email,token.get().getUser());

               token.get().setValid(false);
               tokenRepository.save(token.get()) ;
               throw new TokenExpiration("This token is expired ,we sent other token") ;

           }


       }
       else
       {
           log.warn("This token or email , not exist");

           throw new TokenInValidationException("This token or email not valid") ;
       }


    }

    private String encodeToBase64(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

}
