package uz.pdp.fastfoodapp.entity.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {

    @Query(value = "select cast(f.id as varchar) foodId,\n" +
            "                   f.name_uz as name,\n" +
            "                   price as price,\n" +
            "                   cast(a.id as varchar) as imageId\n" +
            "           from foods f join attachments a on a.id = f.image_id\n" +
            "where f.category_id =:categoryId", nativeQuery = true)
    List<FoodProjection> getFoodsUz(UUID categoryId);

    @Query(value = "select cast(f.id as varchar) foodId,\n" +
            "                   f.name_en as name,\n" +
            "                   price as price,\n" +
            "                   cast(a.id as varchar) as imageId\n" +
            "           from foods f join attachments a on a.id = f.image_id\n" +
            "where f.category_id =:categoryId", nativeQuery = true)
    List<FoodProjection> getFoodsEn(UUID categoryId);


    @Query(nativeQuery = true, value = "select cast(c.id as varchar) as categoryId ,c.name_uz as categoryName\n" +
            "from categories c")
    List<CategoryWithFoodProjectionUz> getFoodsByCategoryUz();



    @Query(nativeQuery = true, value = "select cast(c.id as varchar) as categoryId ,c.name_en as categoryName\n" +
            "from categories c")
    List<CategoryWithFoodProjectionEn> getFoodsByCategoryEn();


}
