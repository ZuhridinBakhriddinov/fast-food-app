package uz.pdp.fastfoodapp.elasticSearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import uz.pdp.fastfoodapp.entity.food.Food;

import java.util.List;
import java.util.UUID;

public interface FoodRepo extends ElasticsearchRepository<Food, UUID> {
//
//    List<Food> findByName(String name);
//
//    List<Food> findByNameContaining(String name);
//
//    List<Food> findByNameUzAndNameEnAndNameRuAndNameOz
//            (String nameUz, String NameEn, String NameRu, String nameOz);
//

}
