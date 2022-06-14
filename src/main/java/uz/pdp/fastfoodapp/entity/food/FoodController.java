package uz.pdp.fastfoodapp.entity.food;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${app.domain}" + "/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

//    @GetMapping
//    public ResponseEntity<?> getAllFoods(){
//        return foodService.getAllFoods();
//    }

    @PostMapping
    public ResponseEntity<?> addFood(
            @RequestPart(name = "food") FoodDto foodDto,
            @RequestPart(name = "image") MultipartFile image
    ){
        return foodService.addFood(foodDto,image);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getFoods(){
        return foodService.getFoods();
    }
}
