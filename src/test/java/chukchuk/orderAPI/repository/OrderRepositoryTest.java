package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Order;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class OrderRepositoryTest {

    private OrderRepository orderRepository;

    @Test
    void getOrder() {

        Optional<Order> result = orderRepository.findById(1L);
        Order order = result.orElseThrow();


    }
}
