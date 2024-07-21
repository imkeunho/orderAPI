package chukchuk.orderAPI.controller;

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

    @PostMapping("/")
    public Map<String, String> insertOrder(@RequestBody OrderSheetDTO orderSheetDTO) {

        log.info(orderSheetDTO);

        orderService.save(orderSheetDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/list")
    public List<OrderSheetDTO> getOrders() {

        return orderService.findAll();
    }

}
