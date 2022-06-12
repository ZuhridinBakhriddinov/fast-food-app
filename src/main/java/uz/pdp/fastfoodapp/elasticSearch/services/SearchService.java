/**
 * 
 */
package uz.pdp.fastfoodapp.elasticSearch.services;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

//import io.pratik.elasticsearch.models.Product;
//import io.pratik.elasticsearch.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import uz.pdp.fastfoodapp.elasticSearch.repositories.FoodRepos;
import uz.pdp.fastfoodapp.entity.food.Food;

/**
 * @author Pratik Das
 *
 */
@Service
@Slf4j
public class SearchService {
	
	private FoodRepos foodRepos;
	
	private  ElasticsearchOperations elasticsearchOperations;
 
	@Autowired
	public SearchService(FoodRepos productRepository, ElasticsearchOperations elasticsearchOperations) {
		super();
		this.foodRepos = productRepository;
		this.elasticsearchOperations = elasticsearchOperations;
	}



	public List<Food> fetchFoodNames(final String nameUz, final String nameEn, final String nameOz, final String nameRu){
		
		return foodRepos.findByNameUzAndNameEnAndNameOzAndNameRu(nameUz,nameEn,nameOz,nameRu);
	}
	
	public List<Food> fetchFoodNamesContaining(final String name){
		
		
	      
		return foodRepos.findByNameContaining(name);
	}

}
