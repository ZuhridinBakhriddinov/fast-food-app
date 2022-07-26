package uz.pdp.fastfoodapp.entity.food;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.fastfoodapp.common.MessageService;
import uz.pdp.fastfoodapp.entity.attachment.Attachment;
import uz.pdp.fastfoodapp.entity.attachment.AttachmentService;
import uz.pdp.fastfoodapp.entity.category.Category;
import uz.pdp.fastfoodapp.entity.category.CategoryService;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final AttachmentService attachmentService;
    private final CategoryService categoryService;
    private final MessageService messageService;


    public ResponseEntity<?> getAllFoods() {
        List<Food> all = foodRepository.findAll();
        return ResponseEntity.ok(all);
    }

    public ResponseEntity<?> addFood(FoodDto foodDto, MultipartFile image) {
        Attachment savedImage = attachmentService.downloadAndGetImage(image);
        String nameRu = foodDto.getNameRu().isEmpty() ? foodDto.getNameUz() : foodDto.getNameRu();
        String nameOz = foodDto.getNameOz().isEmpty() ? foodDto.getNameUz() : foodDto.getNameOz();
        String nameEn = foodDto.getNameEn().isEmpty() ? foodDto.getNameUz() : foodDto.getNameEn();

        String descriptionRu = foodDto.getDescriptionRu().isEmpty() ? foodDto.getDescriptionUz() : foodDto.getDescriptionRu();
        String descriptionOz = foodDto.getDescriptionOz().isEmpty() ? foodDto.getDescriptionUz() : foodDto.getDescriptionOz();
        String descriptionEn = foodDto.getDescriptionEn().isEmpty() ? foodDto.getDescriptionUz() : foodDto.getDescriptionEn();

        Category category = categoryService.getCategoryById(foodDto.getCategoryId());
        if (category == null) {
            throw new IllegalStateException();
        }

        Food food = Food.builder()
                .nameUz(foodDto.getNameUz())
                .nameRu(nameRu)
                .nameOz(nameOz)
                .nameEn(nameEn)
                .descriptionUz(foodDto.getDescriptionUz())
                .descriptionRu(descriptionRu)
                .descriptionOz(descriptionOz)
                .descriptionEn(descriptionEn)
                .price(foodDto.getPrice())
                .image(savedImage)
                .isAvailable(true)
                .availableFrom(foodDto.getAvailableFrom())
                .availableTo(foodDto.getAvailableTo())
                .category(category)
                .preparationTimeInMin(foodDto.getPreparationTimeInMin())
                .build();
        Food savedFood = foodRepository.save(food);
        return ResponseEntity.ok(savedFood);
    }

    public ResponseEntity<?> getFoodsUz() {

        List<CategoryWithFoodProjectionUz> foods = foodRepository.getFoodsByCategoryUz();


        return ResponseEntity.ok().body(new ApiResponse(MessageService.getMessage("SUCCESS"),true,foods));
    }

    public ResponseEntity<?> getFoodsEn() {

        List<CategoryWithFoodProjectionEn> foods = foodRepository.getFoodsByCategoryEn();


        return ResponseEntity.ok().body(new ApiResponse(MessageService.getMessage("SUCCESS"),true,foods));
    }
}
