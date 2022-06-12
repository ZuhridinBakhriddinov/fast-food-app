/**
 * 
 */
package uz.pdp.fastfoodapp.elasticSearch.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

//import io.pratik.elasticsearch.models.Product;
import uz.pdp.fastfoodapp.entity.food.Food;

/**
 * @author Pratik Das
 *
 */
@Repository
public interface FoodRepos extends ElasticsearchRepository<Food, UUID> {
    List<Food> findByName(String name);
    
    List<Food> findByNameContaining(String name);
 
    List<Food> findByNameUzAndNameEnAndNameOzAndNameRu
            (String nameUz,String nameEn, String nameOz, String nameRu);
}

