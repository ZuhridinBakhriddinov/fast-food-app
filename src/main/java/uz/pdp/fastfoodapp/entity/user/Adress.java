package uz.pdp.fastfoodapp.entity.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "adress")
public class Adress extends AbsEntity {
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
