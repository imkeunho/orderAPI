package chukchuk.orderAPI.service;

import chukchuk.orderAPI.dto.OrderSheetDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void getOrderSheetList() {

        List<OrderSheetDTO> result = orderService.findAll();

        log.info(result);
    }

}
