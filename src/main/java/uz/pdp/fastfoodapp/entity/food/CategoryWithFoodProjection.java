package uz.pdp.fastfoodapp.entity.food;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public interface CategoryWithFoodProjection {
    UUID getCategoryId();
    String getCategoryName();
    @Value("#{@foodRepository.getFoods(target.categoryId )}")
    List<FoodProjection> getFoodInfo();

}
