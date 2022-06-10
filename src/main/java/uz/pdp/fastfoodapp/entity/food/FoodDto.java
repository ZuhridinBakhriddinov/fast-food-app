package uz.pdp.fastfoodapp.entity.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
@Builder
public class FoodDto {

    String nameUz;
    String nameRu;
    String nameOz;
    String nameEn;
    String descriptionUz;
    String descriptionRu;
    String descriptionOz;
    String descriptionEn;
    Double price;
    MultipartFile image;




}
