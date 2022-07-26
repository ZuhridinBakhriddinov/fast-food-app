package uz.pdp.fastfoodapp.entity.cook;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.template.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("api/cook")
public class CookController {
    @Autowired
    CookService cookService;

    @PostMapping("receivedOrder/{id}")
    public HttpEntity<?> receivedOrder(@PathVariable UUID id) {
        return cookService.receivedOrder(id);
    }

    @PostMapping("readyOrder/{id}")
    public HttpEntity<?> readyOrder(@PathVariable UUID id) {
        return cookService.readyOrder(id);

    }

    @CrossOrigin
    @GetMapping("/getAcceptedOrders")
    public HttpEntity<?> getAcceptedOrders(@RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "search", defaultValue = "") String search

    ) {

        return cookService.getAcceptedOrders(
                page,
                size,
                search
        );

    }

    @CrossOrigin
    @GetMapping("/getPreparedOrders")
    public HttpEntity<?> getPreparedOrders(@RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "search", defaultValue = "") String search

    ) {

        return cookService.getPreparedOrders(
                page,
                size,
                search
        );

    }


}
