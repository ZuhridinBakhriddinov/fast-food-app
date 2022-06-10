package uz.pdp.fastfoodapp.entity.food;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public ResponseEntity<?> getAllFoods() {
        List<Food> all = foodRepository.findAll();
        return ResponseEntity.ok(all);
    }
}
