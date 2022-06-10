package uz.pdp.fastfoodapp.entity.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
//import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class VerificationCodes extends AbsEntity {
    private String phoneNumber;
    private Integer code;
    private LocalDateTime expireAt;

}
