package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Order;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void selectTest() {
        List<Order> orders = orderRepository.findNotFreezing();

        log.info(orders);
    }
}
