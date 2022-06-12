/**
 * 
 */
package uz.pdp.fastfoodapp.elasticSearch.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import io.pratik.elasticsearch.models.Product;
//import io.pratik.elasticsearch.services.ProductSearchService;
import lombok.extern.slf4j.Slf4j;
import uz.pdp.fastfoodapp.elasticSearch.services.FoodSearchService;
import uz.pdp.fastfoodapp.entity.food.Food;

/**
 * @author Pratik Das
 *
 */
@RestController
@RequestMapping("/")
@Slf4j
public class SearchController {
	
	private FoodSearchService searchService;

	@Autowired
	public SearchController(FoodSearchService searchService) {
	    this.searchService = searchService;
	}
	
	@GetMapping("/food")
	@ResponseBody
	public List<Food> fetchByNameOrDesc(@RequestParam(value = "q", required = false) String query) {
        log.info("searching by name {}",query);
		List<Food> food = searchService.processSearch(query) ;
	    log.info("food {}",food);
		return food;
	  }
	
	@GetMapping("/suggestions")
	@ResponseBody
	public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {                         
        log.info("fetch suggests {}",query);
        List<String> suggests = searchService.fetchSuggestions(query);
        log.info("suggests {}",suggests);
        return suggests;
	  }

}
