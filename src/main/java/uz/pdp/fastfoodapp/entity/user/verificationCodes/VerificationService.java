package uz.pdp.fastfoodapp.entity.user.verificationCodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationService {
    @Autowired
    VerificationRepository verificationRepository;

    public ApiResponse getAllVerificationCodes() {
        List<VerificationCodes> all = verificationRepository.findAll();
        if (all.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Successfully", true, all);
    }

    public ApiResponse getVerificationCodesById(UUID uuid) {
        Optional<VerificationCodes> byId = verificationRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        return new ApiResponse("Successfully", true, byId);
    }

    public ApiResponse addVerificationCode(VerificationCodes verificationCodes) {
        try {
            VerificationCodes save = verificationRepository.save(verificationCodes);
            return new ApiResponse("Successfully saved", true, save);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse editVerificationCodes(UUID uuid, VerificationCodes verificationCodes) {
        Optional<VerificationCodes> byId = verificationRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new ApiResponse("Not found", false);
        }
        VerificationCodes edit = byId.get();
        edit.setPhoneNumber(verificationCodes.getPhoneNumber());
        edit.setCode(verificationCodes.getCode());
        edit.setCreatedAt(verificationCodes.getCreatedAt());
        edit.setExpireAt(verificationCodes.getExpireAt());
        VerificationCodes save = verificationRepository.save(edit);
        return new ApiResponse("Successfully edited", true, save);
    }

    public ApiResponse delete(UUID uuid) {
        try {
            verificationRepository.deleteById(uuid);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Not found", false);
        }
    }

}
