package uz.pdp.fastfoodapp.entity.user.address;


import lombok.*;
import uz.pdp.fastfoodapp.entity.user.district.District;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "adress")
public class Address extends AbsEntity {
    @ManyToOne
    private User user;
    private String name;
    @ManyToOne
    private District district;
    private String landmark;
    private int houseNumber;
    private int entrance;
    private Integer flat;
    private Integer floor;
    private float latitude;
    private float longitude;
}
