package uz.pdp.fastfoodapp.entity.user.verificationCodes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationRepository extends JpaRepository<VerificationCodes, UUID> {
    Optional<VerificationCodes> findByPhoneNumber(String phoneNumber);
}
