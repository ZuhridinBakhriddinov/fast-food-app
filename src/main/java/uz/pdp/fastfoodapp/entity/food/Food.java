package uz.pdp.fastfoodapp.entity.food;

import lombok.*;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.entity.attachment.Attachment;
import uz.pdp.fastfoodapp.entity.category.Category;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    String nameUz;
    String nameRu;
    String nameOz;
    String nameEn;
    String descriptionUz;
    String descriptionRu;
    String descriptionOz;
    String descriptionEn;
    Double price;
    @OneToOne
    @JoinColumn(name = "image_id")
    Attachment image;
    Boolean isAvailable;
    LocalTime availableFrom;
    LocalTime availableTo;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    Integer preparationTimeInMin;


}
