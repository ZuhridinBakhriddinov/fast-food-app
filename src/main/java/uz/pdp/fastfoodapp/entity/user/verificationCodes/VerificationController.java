package uz.pdp.fastfoodapp.entity.user.verificationCodes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("${app.domain}/verification")
public class VerificationController {

    private final VerificationService verificationService;


    @GetMapping("/send/forRegister/{phoneNumber}")
    public HttpEntity<?> sendSmsForUserRegistration(@PathVariable @NotBlank(message = "Phone Number not blank") String phoneNumber) {
        return verificationService.sendSmsForUserRegistration(phoneNumber);
    }


    @GetMapping("/send/forLogin/{phoneNumber}")
    public HttpEntity<?> sendSmsForUserLogin(@PathVariable String phoneNumber) {
        return verificationService.sendSmsForUserLogin(phoneNumber);
    }

    @PostMapping("/validate/forRegister")
    public HttpEntity<?> validateSmsForUserRegistration(@Valid @RequestBody VerificationCodeDto verificationDto) {
        return verificationService.validateSmsForUserRegistration(verificationDto);
    }

}
