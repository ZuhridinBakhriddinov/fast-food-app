package uz.pdp.fastfoodapp.entity.user.verificationCodes;


import lombok.*;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
//import java.sql.Timestamp;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class VerificationCodes extends AbsEntity {
    private String phoneNumber;
    private Integer code;
    private LocalDateTime expireAt;

}
