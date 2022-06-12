/**
 * 
 */
package uz.pdp.fastfoodapp.elasticSearch.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import io.pratik.elasticsearch.models.Food;
//import io.pratik.elasticsearch.repositories.FoodRepos;
import lombok.extern.slf4j.Slf4j;
import uz.pdp.fastfoodapp.elasticSearch.repositories.FoodRepos;
import uz.pdp.fastfoodapp.entity.food.Food;

/**
 * @author Pratik Das
 *
 */
@Service
@Slf4j
public class FoodSearchServiceWithRepo {

	private FoodRepos foodRepos;

	@Autowired
	public FoodSearchServiceWithRepo(final FoodRepos foodRepos) {
		super();
		this.foodRepos = foodRepos;
	}

	public void createFoodIndexBulk(final List<Food> foods) {
		foodRepos.saveAll(foods);
	}

	public void createFoodIndex(final Food food) {
		foodRepos.save(food);
	}
//
//	public List<Food> findFoodsByManufacturerAndCategory(final String manufacturer, final String category) {
//			return foodRepos.findByManufacturerAndCategory(manufacturer, category);
//	}

	public List<Food> findByFoodName(final String foodName) {
		return foodRepos.findByName(foodName);
	}

	public List<Food> findByFoodMatchingNames(final String foodName) {
		return foodRepos.findByNameContaining(foodName);
	}

}
