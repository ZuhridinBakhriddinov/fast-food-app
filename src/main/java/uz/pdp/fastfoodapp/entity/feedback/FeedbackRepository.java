package uz.pdp.fastfoodapp.entity.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.fastfoodapp.entity.food.Food;

import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {


    @Query(nativeQuery = true,value = "")
    Page<Feedback> getFeedbackByOrder(Pageable pageable, String search);
}
