package uz.pdp.fastfoodapp.entity.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.entity.food.Food;
import uz.pdp.fastfoodapp.entity.order.Order;
import uz.pdp.fastfoodapp.entity.order.OrderRepository;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final OrderRepository orderRepository;


    public HttpEntity<?> addFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setRate(feedbackDto.getRate());
        feedback.setText(feedbackDto.getText());
        Optional<Order> optionalOrder = orderRepository.findById(feedbackDto.getOrderId());
        if (!optionalOrder.isPresent()) {
            return new ResponseEntity<>(new ApiResponse("wrong", false, false), HttpStatus.NOT_FOUND);
        }
        feedback.setOrder(optionalOrder.get());
        feedbackRepository.save(feedback);
        return new ResponseEntity<>(new ApiResponse("success", true, true), HttpStatus.OK);
    }


    public HttpEntity<?> deleteFeedbackById(UUID id) {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        for (Feedback feedback : feedbackList) {
            if (feedback.getId().equals(id)) {
                feedbackRepository.deleteById(id);
                return new ResponseEntity<>(new ApiResponse("success", true, true), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse("wrong", false, false), HttpStatus.NOT_FOUND);
    }


    public HttpEntity<?> editFeedback(FeedbackDto feedbackDto, UUID id) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(id);
        Feedback feedback = optionalFeedback.get();
        feedback.setRate(feedbackDto.getRate());
        feedback.setText(feedbackDto.getText());
        Optional<Order> orderOptional = orderRepository.findById(feedbackDto.getOrderId());
        if (!orderOptional.isPresent()) {
            return new ResponseEntity<>(new ApiResponse("wrong", false, false), HttpStatus.NOT_FOUND);
        }
        Feedback save = feedbackRepository.save(feedback);
        return new ResponseEntity<>(new ApiResponse("success", true, save), HttpStatus.OK);

    }


    public HttpEntity<?> getFeedbackByOrderId(UUID orderId, int size, int page, String search) {

        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );
        Page<Feedback> all = feedbackRepository.getFeedbackByOrder(
                pageable, search);

        return ResponseEntity.ok(new ApiResponse("success", true, all));
    }
}
