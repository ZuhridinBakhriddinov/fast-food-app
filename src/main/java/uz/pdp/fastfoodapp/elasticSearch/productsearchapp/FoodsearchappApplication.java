package uz.pdp.fastfoodapp.elasticSearch.productsearchapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

//import io.pratik.elasticsearch.models.Product;
//import io.pratik.elasticsearch.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import uz.pdp.fastfoodapp.elasticSearch.repositories.FoodRepos;
import uz.pdp.fastfoodapp.entity.food.Food;

@SpringBootApplication
@Slf4j
public class FoodsearchappApplication {
	
	private static final String COMMA_DELIMITER = ",";

	@Autowired
	private ElasticsearchOperations esOps;

	@Autowired
	  FoodRepos foodRepos;

	public static void main(String[] args) {
		SpringApplication.run(FoodsearchappApplication.class, args);
	}
	
	@PreDestroy
	public void deleteIndex() {
		esOps.indexOps(Food.class).delete();
	}
	
	
	@PostConstruct
	public void buildIndex() {

		esOps.indexOps(Food.class).refresh();
		foodRepos.deleteAll();
		foodRepos.saveAll(prepareDataset());
	}

	private Collection<Food> prepareDataset() {
		Resource resource = new ClassPathResource("fashion-products.csv");
		List<Food> foodList = new ArrayList<Food>();

		try (
			InputStream input = resource.getInputStream();
			Scanner scanner = new Scanner(resource.getInputStream());) {
			int lineNo = 0;
			while (scanner.hasNextLine()) {
				++lineNo;				
				String line = scanner.nextLine();
				if(lineNo == 1) continue;
				Optional<Food> food =
						csvRowToFoodMapper(line);
				if(food.isPresent())
				foodList.add(food.get());
			}
		} catch (Exception e) {
			log.error("File read error {}",e);;
		}
		return foodList;
	}

	private Optional<Food> csvRowToFoodMapper(final String line) {
		try (			
			Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);
			while (rowScanner.hasNext()) {
				String name = rowScanner.next();
				String description = rowScanner.next();
				String manufacturer = rowScanner.next();
				return Optional.of(
						Food.builder()
						.nameEn(name)
						.nameRu(name)
						.nameUz(name)
						.nameOz(name)
//						.description(description)
//						.manufacturer(manufacturer)
						.build());

			}
		}
		return Optional.of(null);
	}

}
