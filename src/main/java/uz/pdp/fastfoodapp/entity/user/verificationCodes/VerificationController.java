package uz.pdp.fastfoodapp.entity.user.verificationCodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("api/verification")
public class VerificationController {
    @Autowired
    VerificationService verificationService;

    @GetMapping
    public HttpEntity<?> getAllVerificationCodes() {
        ApiResponse allVerificationCodes = verificationService.getAllVerificationCodes();
        return ResponseEntity.status(allVerificationCodes.isSuccess() ? 200 : 400).body(allVerificationCodes);
    }

    @GetMapping("/{uuid}")
    public HttpEntity<?> getVerificationCodeById(@PathVariable UUID uuid) {
        ApiResponse allVerificationCodesById = verificationService.getVerificationCodesById(uuid);
        return ResponseEntity.status(allVerificationCodesById.isSuccess() ? 200 : 400).body(allVerificationCodesById);
    }

    @PostMapping
    public HttpEntity<?> addVerificationCode(@RequestBody VerificationCodes verificationCodes) {
        ApiResponse apiResponse = verificationService.addVerificationCode(verificationCodes);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PutMapping("/{uuid}")
    public HttpEntity<?> editVerificationCode(@PathVariable UUID uuid, @RequestBody VerificationCodes verificationCodes) {
        ApiResponse apiResponse = verificationService.editVerificationCodes(uuid, verificationCodes);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        ApiResponse delete = verificationService.delete(uuid);
        return ResponseEntity.status(delete.isSuccess() ? 200 : 400).body(delete);
    }
}
