package uz.pdp.fastfoodapp.entity.food;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public interface CategoryWithFoodProjectionEn {
    UUID getCategoryId();
    String getCategoryName();
    @Value("#{@foodRepository.getFoodsEn(target.categoryId )}")
    List<FoodProjection> getFoodInfo();

}
