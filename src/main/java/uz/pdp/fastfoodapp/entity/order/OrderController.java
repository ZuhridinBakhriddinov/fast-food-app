package uz.pdp.fastfoodapp.entity.order;


import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.UserRepository;
import uz.pdp.fastfoodapp.template.AppConstants;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserRepository userRepository;


    @Value("${WEBHOOK_KEY}")
    private String webhookKey;
    @Value("${STRIPE_API_KEY}")
    private String stripeApiKey;

    //    @SneakyThrows
    @SneakyThrows
    @CrossOrigin
    @PostMapping("/webhook")
    public void handle(@RequestBody String payload, @RequestHeader(name = "Stripe-Signature") String signHeader, HttpServletResponse response) {
        String endpointSecret = "whsec_caf7231c252f6538d81bf44b2f1ee721c5b42440b2211aa88ed4dbc1aa3393b8";
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";
//        String endpointSecret = webhookKey;
//        Stripe.apiKey = stripeApiKey;
//      to activate:  stripe listen --forward-to localhost:8081/order/webhook


        Event event = Webhook.constructEvent(payload, signHeader, endpointSecret);


        System.out.println("Order fulfilled");
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();
            System.out.println("DONE");
            orderService.fulfillOrder(session);


        }

    }


    @CrossOrigin
    @PostMapping("/purchase-products")
    public HttpEntity<?> createStripeSession(@RequestBody OrderListDto orderItems) {
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";
        UUID userId = orderItems.orderInfo.userId;
        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.get();
      /*  UUID userId = orderItems.getUserId().getUserId();
        Optional<User> userOptional = userRepository.findById(userId);*/
        //   User user = userOptional.get();
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        return orderService.getStripeSession(user, orderItems, lineItems);
    }

    @CrossOrigin
    @GetMapping("/recently")
    public HttpEntity<?> getRecentlyMyOrder(@RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                            @RequestParam(name = "page", defaultValue = "1") int page,
                                            @RequestParam(name = "search", defaultValue = "") String search,
                                            @AuthenticationPrincipal User user

    ) {

        return orderService.getRecentlyMyOrder(
                page,
                size,
                search,
                user.getId()
        );

    }

    @CrossOrigin
    @GetMapping("/orderHistory")
    public HttpEntity<?> getOrderHistory(@RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                         @RequestParam(name = "page", defaultValue = "1") int page,
                                         @RequestParam(name = "search", defaultValue = "") String search,
                                         @AuthenticationPrincipal User user

    ) {

        return orderService.getOrderHistory(
                page,
                size,
                search,
                user.getId()
        );

    }


}
