package uz.pdp.fastfoodapp.entity.user.verificationCodes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.entity.user.UserRepository;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ACCOUNT_SID_TWILIO = "ACb879375012eab6a0ed874018705caae6";

    private final String AUTH_TOKEN_TWILIO = "c08af28e76cda883ddc11fec9280bc2f";

    public Integer sendSms(String phoneNumber) {


        /**
         * random code
         */
        int code = (int) ((Math.random() * (9999 - 1000)) + 1000);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(30);

        /**
         * sending sms by using MESSAGE_BIRD
         */
//        MessageBirdService messageBirdService = new MessageBirdServiceImpl(MESSAGE_BIRD_API_KEY);
//        MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
//        List<BigInteger> phones = new ArrayList<>();
//        phones.add(new BigInteger(phoneNumber));
//        try {
//            MessageResponse response = messageBirdClient.sendMessage("FootZone", "Your code is: " + code, phones);
//        } catch (UnauthorizedException | GeneralException ex) {
//            ex.printStackTrace();
//            return null;
//        }

        /**
         *  sending sms by using twilio
         */
/*        Twilio.init(ACCOUNT_SID_TWILIO, AUTH_TOKEN_TWILIO);
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+19107089958"), "Food Delivery \nYour code is: " + code).create();
        System.out.println(message.getSid());*/

        System.out.println(code);

        Optional<VerificationCodes> optionalByPhoneNumber = verificationRepository.findByPhoneNumber(phoneNumber);
        if (optionalByPhoneNumber.isPresent()) {
            VerificationCodes smsCode = optionalByPhoneNumber.get();
            smsCode.setCode(code);
            smsCode.setExpireAt(expirationDate);
            verificationRepository.save(smsCode);
        } else {
            verificationRepository.save(
                    new VerificationCodes(
                            phoneNumber,
                            code,
                            expirationDate
                    )
            );
        }
        return code;
    }


    public HttpEntity<?> sendSmsForUserLogin(String phoneNumber) {
        boolean phoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);

        if (!phoneNumberExists)
            return ResponseEntity.status(404).body(new ApiResponse("User with this phone number does not exist", false));

        Integer smsCode = sendSms(phoneNumber);
        if (smsCode == null)
            return ResponseEntity.status(409).body(new ApiResponse("Something went wrong !!!, Please try again", false));

//        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
//        if (optionalUser.isPresent()) {
//            optionalUser.get().setSmsCode(passwordEncoder.encode(smsCode.toString()));
//            userRepository.save(optionalUser.get());
//        }
        return ResponseEntity.status(200).body(new ApiResponse("SMS successfully sent smsCode: "+smsCode, true));
    }

    public HttpEntity<?> sendSmsForUserRegistration(String phoneNumber) {
        boolean phoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);
        if (phoneNumberExists)
            return ResponseEntity.status(400).body(new ApiResponse("This phone number already exists", false));

        Integer smsCode = sendSms(phoneNumber);
        if (smsCode == null)
            return ResponseEntity.status(409).body(new ApiResponse("Something went wrong !!!, Please try again", false));
        return ResponseEntity.status(200).body(new ApiResponse("SMS successfully sent smsCode: "+smsCode, true));
    }

    public HttpEntity<?> validateSmsForUserRegistration(VerificationCodeDto verificationCodeDto) {
        ApiResponse apiResponse = validateSmsCode(verificationCodeDto.getPhoneNumber(), verificationCodeDto.getCodeSent());
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 404).body(apiResponse);
    }

    public ApiResponse validateSmsCode(String phoneNumber, int code) {
        Optional<VerificationCodes> optionalByPhoneNumber = verificationRepository.findByPhoneNumber(phoneNumber);
        if (!optionalByPhoneNumber.isPresent()) return new ApiResponse("There is no code with this number", false);
        VerificationCodes smsCode = optionalByPhoneNumber.get();
        if (smsCode.getExpireAt().isBefore(LocalDateTime.now()))
            return new ApiResponse("This code is invalid", false);

        if (code != smsCode.getCode())
            return new ApiResponse("Wrong code", false);
        return new ApiResponse("Success", true);
    }
}
