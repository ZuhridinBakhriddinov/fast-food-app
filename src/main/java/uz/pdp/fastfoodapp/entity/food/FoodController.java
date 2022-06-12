package uz.pdp.fastfoodapp.entity.food;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.domain}" + "/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<?> getAllFoods(){
        return foodService.getAllFoods();
    }

    @PostMapping
    public ResponseEntity<?> addFood(){
        return null;
    }
}
