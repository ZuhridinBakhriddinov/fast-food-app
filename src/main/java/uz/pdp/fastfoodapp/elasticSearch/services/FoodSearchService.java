/**
 * 
 */
package uz.pdp.fastfoodapp.elasticSearch.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import uz.pdp.fastfoodapp.entity.food.Food;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class FoodSearchService {

	private static final String FOOD_INDEX = "foodindex";

	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	public FoodSearchService(final ElasticsearchOperations elasticsearchOperations) {
		super();
		this.elasticsearchOperations = elasticsearchOperations;
	}

	public List<IndexedObjectInformation> createFoodIndexBulk(final List<Food> foods) {

		List<IndexQuery> queries = foods.stream()
				.map(food -> new IndexQueryBuilder().withId(food.getId().toString()).withObject(food).build())
				.collect(Collectors.toList());


		return elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(FOOD_INDEX));

	}

	public String createFoodIndex(Food food) {

		IndexQuery indexQuery = new IndexQueryBuilder().withId(food.getId().toString()).withObject(food).build();
		String documentId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of(FOOD_INDEX));

		return documentId;
	}

	public void findFoodsByBrand(final String brandName) {
		QueryBuilder queryBuilder = QueryBuilders
				.matchQuery("manufacturer", brandName);
		// .fuzziness(0.8)
		// .boost(1.0f)
		// .prefixLength(0)
		// .fuzzyTranspositions(true);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(queryBuilder)
				.build();

		SearchHits<Food> foodHits =
				elasticsearchOperations
				.search(searchQuery, Food.class,
				  IndexCoordinates.of(FOOD_INDEX));

		log.info("foodHits {} {}", foodHits.getSearchHits().size(), foodHits.getSearchHits());

		List<SearchHit<Food>> searchHits =
				foodHits.getSearchHits();
		int i = 0;
		for (SearchHit<Food> searchHit : searchHits) {
			log.info("searchHit {}", searchHit);
		}

	}

	public void findByFoodName(final String foodName) {
		Query searchQuery = new StringQuery(
				"{\"match\":{\"name\":{\"query\":\""+ foodName + "\"}}}\"");

		SearchHits<Food> foods = elasticsearchOperations.search(searchQuery, Food.class,
				IndexCoordinates.of(FOOD_INDEX));
	}

	public void findByFoodPrice(final String foodPrice) {
		Criteria criteria = new Criteria("price").greaterThan(10.0).lessThan(100.0);
		Query searchQuery = new CriteriaQuery(criteria);

		SearchHits<Food> foods = elasticsearchOperations.search(searchQuery, Food.class,
				IndexCoordinates.of(FOOD_INDEX));
	}

	public List<Food> processSearch(final String query) {
		log.info("Search with query {}", query);
		
		// 1. Create query on multiple fields enabling fuzzy search
		QueryBuilder queryBuilder = 
				QueryBuilders
				.multiMatchQuery(query, "name", "description")
				.fuzziness(Fuzziness.AUTO);

		Query searchQuery = new NativeSearchQueryBuilder()
				                .withFilter(queryBuilder)
				                .build();

		// 2. Execute search
		SearchHits<Food> foodHits =
				elasticsearchOperations
				.search(searchQuery, Food.class,
				IndexCoordinates.of(FOOD_INDEX));

		// 3. Map searchHits to food list
		List<Food> foodMatches = new ArrayList<Food>();
		foodHits.forEach(srchHit->{
			foodMatches.add(srchHit.getContent());
		});
		return foodMatches;
	}

	

	
	public List<String> fetchSuggestions(String query) {
		QueryBuilder queryBuilder = QueryBuilders
				.wildcardQuery("name", query+"*");

		Query searchQuery = new NativeSearchQueryBuilder()
				.withFilter(queryBuilder)
				.withPageable(PageRequest.of(0, 5))
				.build();

		SearchHits<Food> searchSuggestions =
				elasticsearchOperations.search(searchQuery, 
						Food.class,
				IndexCoordinates.of(FOOD_INDEX));
		
		List<String> suggestions = new ArrayList<String>();
		
		searchSuggestions.getSearchHits().forEach(searchHit->{
			suggestions.add(searchHit.getContent().getNameUz());
		});
		return suggestions;
	}

}
