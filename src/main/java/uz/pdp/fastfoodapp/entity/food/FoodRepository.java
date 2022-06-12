package uz.pdp.fastfoodapp.entity.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {

    @Query(value = "select cast(f.id as varchar) foodId,\n" +
            "       f.name_uz as name,\n" +
            "       price as price,\n" +
            "       cast(a.id as varchar) as imageId,\n" +
            "       c.name_uz as categoryName\n" +
            "from foods f join attachments a on a.id = f.image_id\n" +
            "join categories c on c.id = f.category_id",nativeQuery = true)
    List<FoodProjection> getFoods();
}
