package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Order;
import chukchuk.orderAPI.dto.OrderItemInterface;
import chukchuk.orderAPI.dto.OrderItemListDTO;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Test
    public void compareOrder() {
//        List<OrderItem> result = orderItemRepository.customFindAll();
//
//        Order order1 = null;
//        Order order2 = null;
//        for (OrderItem orderItem : result) {
//            if (orderItem.getId().equals(9L)) {
//                order1 = orderItem.getOrder();
//            } else if (orderItem.getId().equals(5L)) {
//                order2 = orderItem.getOrder();
//            }
//        }

        Order order1 = new Order();
        Order order2 = new Order();

        log.info(order1);
        log.info(order2);

        Assertions.assertThat(order1).isNotEqualTo(order2);
    }

    public OrderItemListDTO interfaceToDTO(OrderItemInterface orderItemInterface) {

        return OrderItemListDTO.builder()
                .name(orderItemInterface.getName())
                .price(orderItemInterface.getPrice())
                .pno(orderItemInterface.getPno())
                .qty(orderItemInterface.getQty())
                .build();
    }

    @Test
    public void getOrderItemByDong() {

        List<OrderItemInterface> result = orderItemRepository.findByDongWithOrderQty(101);

        List<OrderItemListDTO> list = result.stream().map((this::interfaceToDTO)).toList();

        log.info(list);
    }
}
