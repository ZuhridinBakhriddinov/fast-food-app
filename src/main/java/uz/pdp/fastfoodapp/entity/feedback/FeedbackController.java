package uz.pdp.fastfoodapp.entity.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.template.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("${app.domain}" + "/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping()
    public HttpEntity<?> addFeedback(@RequestBody FeedbackDto feedbackDto) {
        return feedbackService.addFeedback(feedbackDto);

    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCommentById(@PathVariable UUID id) {
        return feedbackService.deleteFeedbackById(id);

    }

    @PutMapping("/{id}")
    public HttpEntity<?> editFeedback(@RequestBody FeedbackDto commentDto, @PathVariable UUID id) {
        return feedbackService.editFeedback(commentDto, id);

    }


    @GetMapping("/orderId")
    public HttpEntity<?> getFeedbackByOrderId( @PathVariable UUID orderId,
                                               @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                               @RequestParam(name = "page", defaultValue = "1") int page,
                                               @RequestParam(name = "search", defaultValue = "") String search
                                               ) {
        return feedbackService.getFeedbackByOrderId( orderId,size,page,search);


    }




}
