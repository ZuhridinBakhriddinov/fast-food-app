package uz.pdp.fastfoodapp.entity.food;

import lombok.*;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.entity.attachment.Attachment;
import uz.pdp.fastfoodapp.entity.user.address.Address;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PackagePrivate
@Entity(name = "foods")
public class Food extends AbsEntity {

    String name;
    String description;
    Double price;
    @OneToOne
    @JoinColumn(name = "image_id")
    Attachment image;
    Boolean isAvailable;
    LocalTime availableFrom;
    @OneToOne
    @JoinColumn(name = "address_id")
    Address address;
    LocalTime availableTo;


}
