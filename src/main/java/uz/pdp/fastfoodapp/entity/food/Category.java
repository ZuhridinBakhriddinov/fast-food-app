package uz.pdp.fastfoodapp.entity.food;

import lombok.*;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PackagePrivate
@Entity(name = "categories")
public class Category extends AbsEntity {
    String nameUz;
    String nameRu;
    String nameOz;
    String nameEn;
    String descriptionUz;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    Integer preparationTimeInMin;
}
