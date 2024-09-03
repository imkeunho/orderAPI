package chukchuk.orderAPI.controller;

import chukchuk.orderAPI.dto.OrderItemListDTO;
import chukchuk.orderAPI.dto.OrderSheetDTO;
import chukchuk.orderAPI.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public List<OrderSheetDTO> getOrders() {

        return orderService.findOfCompletePayment();
    }

    @GetMapping("/item/{dong}")
    public List<OrderItemListDTO> getItems(@PathVariable("dong") int dong) {

        return orderService.findByDongWithOrderQty(dong);
    }

    @PutMapping("/payment/{ono}")
    public Map<String, String> updatePayment(@PathVariable("ono") Long ono) {

        orderService.updatePayment(ono);

        return Map.of("RESULT", "SUCCESS");
    }

    @PutMapping("/complete/{ono}")
    public Map<String, String> complete(@PathVariable("ono") Long ono) {

        orderService.complete(ono);

        return Map.of("RESULT", "SUCCESS");
    }

    @PutMapping("/complete/all")
    public Map<String, String> freezing() {

        orderService.freezing();

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{ono}")
    public Map<String, String> deleteOrder(@PathVariable("ono") Long ono) {

        orderService.remove(ono);

        return Map.of("RESULT", "SUCCESS");
    }

}
