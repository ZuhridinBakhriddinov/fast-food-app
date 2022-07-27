package uz.pdp.fastfoodapp.entity.order;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.entity.food.Food;
import uz.pdp.fastfoodapp.entity.food.FoodRepository;
import uz.pdp.fastfoodapp.entity.order.enums.OrderStatus;
import uz.pdp.fastfoodapp.entity.orderItem.OrderItem;
import uz.pdp.fastfoodapp.entity.orderItem.OrderItemDto;
import uz.pdp.fastfoodapp.entity.orderItem.OrderItemRepository;
import uz.pdp.fastfoodapp.entity.payments.Payments;
import uz.pdp.fastfoodapp.entity.payments.PaymentsRepository;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.address.Address;
import uz.pdp.fastfoodapp.entity.user.address.AddressRepository;
import uz.pdp.fastfoodapp.template.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PaymentsRepository paymentsRepository;


    public void fulfillOrder(Session session) {


        List<Order> orders = orderRepository.findAllByUserIdAndStatus(UUID.fromString(session.getClientReferenceId()), OrderStatus.PENDING);

        for (Order order : orders) {
            order.setStatus(OrderStatus.ACCEPTED);
            orderRepository.save(order);
        }


    }

    int orderNum = 1;

    public HttpEntity<?> getStripeSession(User user, OrderListDto orderItems, List<SessionCreateParams.LineItem> lineItems) {
        //   (int) (Math.random() * (10000 - 1 + 1)) + 1
        Optional<Payments> paymentsOptional = paymentsRepository.findById(orderItems.getOrderInfo().payTypeId);
        Payments payments = paymentsOptional.get();
        double totalSum = 0;
        Order order = new Order();
        order.setOrderNumber(orderNum++);
        order.setStatus(OrderStatus.PENDING);
        order.setDeliveryPrice(10.0);
        order.setDeliverer(user);
        order.setPayments(payments);
        Optional<Address> addressOptional = addressRepository.findById(orderItems.orderInfo.addressId);
        order.setAddress(addressOptional.get());
        order.setUser(user);
        order.setTotalSum(totalSum + order.getDeliveryPrice());
        Order saveOrder = orderRepository.save(order);


   /*todo: total sum has problem
   * */

        for (OrderItemDto orderProducts : orderItems.getOrderItem()) {
            OrderItem orderItem = new OrderItem();
            Optional<Food> optionalFood = foodRepository.findById(orderProducts.getFoodId());
            orderItem.setFood(optionalFood.get());
            orderItem.setQuantity(orderProducts.getQuantity());
            orderItem.setOrder(saveOrder);
            orderItemRepository.save(orderItem);
            totalSum += optionalFood.get().getPrice();

            double productPrice = (optionalFood.get().getPrice() * 100 + 0.3) / (1 - 2.9 / 100);
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                    .builder()
                    .setName(optionalFood.get().getNameUz())
                    .build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                    .builder()
                    .setProductData(productData)
                    .setCurrency("usd")
                    .setUnitAmount((long) (productPrice))
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem
                    .builder()
                    .setPriceData(priceData)
                    .setQuantity((long) orderProducts.getQuantity())
                    .build();


            lineItems.add(lineItem);


        }

        if (payments.getName().equals("Cash")) {
            List<Order> orders = orderRepository.findAllByUserIdAndStatus(orderItems.orderInfo.userId, OrderStatus.PENDING);

            for (Order myOrder : orders) {
                myOrder.setStatus(OrderStatus.ACCEPTED);
                orderRepository.save(order);
            }
        //    return ResponseEntity.ok().body("http://localhost:3000/success");
            return ResponseEntity.ok().body("https://fast-food-client.herokuapp.com/success");

        }



        SessionCreateParams params = SessionCreateParams
                .builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl("http://localhost:8080/failed")
                .setSuccessUrl("http://localhost:3000/success")
                .setClientReferenceId(user.getId().toString())
                .addAllLineItem(lineItems)
                .build();

        try {
            Session session = Session.create(params);
            String checkoutUrl = session.getUrl();


            return ResponseEntity.ok(checkoutUrl);

        } catch (StripeException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();

    }

    public HttpEntity<?> getRecentlyMyOrder(int page, int size, String search,UUID user_id) {

        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );

        Page<CustomOrder> all = orderRepository.findAllRecentlyMyOrder(
                pageable,
                search,
                user_id
        );

        return ResponseEntity.ok(new ApiResponse("success", true, all));

    }

    public HttpEntity<?> getOrderHistory(int page, int size, String search, UUID user_id) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );

        Page<CustomOrder> all = orderRepository.findAllOrderHistory(
                pageable,
                search,
                user_id
        );

        return ResponseEntity.ok(new ApiResponse("success", true, all));
    }
}
