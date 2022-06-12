package uz.pdp.fastfoodapp.entity.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
@Builder
public class FoodDto {

    @NotBlank
    String nameUz;
    String nameRu;
    String nameOz;
    String nameEn;
    @NotBlank
    String descriptionUz;
    String descriptionRu;
    String descriptionOz;
    String descriptionEn;
    Double price;
    MultipartFile image;
    LocalTime availableFrom;
    LocalTime availableTo;
    UUID categoryId;
    Integer preparationTimeInMin;




}
