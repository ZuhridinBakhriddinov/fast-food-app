/**
 * 
 */
package uz.pdp.fastfoodapp.elasticSearch.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import io.pratik.elasticsearch.models.Product;
//import io.pratik.elasticsearch.services.SearchService;
import lombok.extern.slf4j.Slf4j;
import uz.pdp.fastfoodapp.elasticSearch.services.SearchService;
import uz.pdp.fastfoodapp.entity.food.Food;

/**
 * @author Pratik Das
 *
 */
@Controller
@Slf4j
public class UIController {
	
	private SearchService searchService;

	@Autowired
	public UIController(SearchService searchService) { 
	    this.searchService = searchService;
	}

	@GetMapping("/search")
    public String home(Model model) {
		List<Food> foods = searchService.fetchFoodNamesContaining("Hornby");
        
		List<String> names = foods.stream().flatMap(food->{
			return Stream.of(food.getNameUz());
		}).collect(Collectors.toList());
		log.info("food names {}", names);
        model.addAttribute("names", names);
        return "search";
    }
 
}
