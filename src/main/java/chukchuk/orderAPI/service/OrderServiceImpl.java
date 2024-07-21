package chukchuk.orderAPI.service;

import chukchuk.orderAPI.domain.Order;
import chukchuk.orderAPI.domain.OrderItem;
import chukchuk.orderAPI.domain.Product;
import chukchuk.orderAPI.dto.OrderItemListDTO;
import chukchuk.orderAPI.dto.OrderSheetDTO;
import chukchuk.orderAPI.repository.OrderItemRepository;
import chukchuk.orderAPI.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    @Override
    public void save(OrderSheetDTO orderSheetDTO) {

        Order order = Order.builder()
                .dong(orderSheetDTO.getDong())
                .ho(orderSheetDTO.getHo())
                .account(orderSheetDTO.getAccount())
                .build();

        orderRepository.save(order);

        List<OrderItem> list = orderSheetDTO.getItems().stream().map((dto) ->
                OrderItem.builder()
                        .order(order)
                        .product(Product.builder().id(dto.getPno()).build())
                        .qty(dto.getQty())
                        .orderDate(LocalDateTime.now())
                        .build()).toList();

        orderItemRepository.saveAll(list);
    }

    @Override
    public List<OrderSheetDTO> findAll() {

        List<OrderItem> orderItemList = orderItemRepository.customFindAll();

        List<OrderSheetDTO> orderSheetDTOList = new ArrayList<>();

        Loop1 :
        for (OrderItem orderItem : orderItemList) {

            Order order = orderItem.getOrder();

            for (OrderSheetDTO dto : orderSheetDTOList) {
                if (dto.getOid().equals(order.getId())) {
                    continue Loop1;
                }
            }

            OrderSheetDTO orderSheetDTO = OrderSheetDTO.builder()
                    .oid(order.getId())
                    .dong(order.getDong())
                    .ho(order.getHo())
                    .account(order.getAccount())
                    .build();

            List<OrderItemListDTO> items = new ArrayList<>();
            for (OrderItem searchOrderItem : orderItemList) {

                OrderItemListDTO orderItemListDTO = OrderItemListDTO.builder()
                        .pno(searchOrderItem.getProduct().getId())
                        .qty(searchOrderItem.getQty())
                        .price(searchOrderItem.getProduct().getPrice())
                        .name(searchOrderItem.getProduct().getName())
                        .build();

                Order searchOrder = searchOrderItem.getOrder();
                if (order == searchOrder) {
                    items.add(orderItemListDTO);
                }
            }

            orderSheetDTO.setItems(items);
            orderSheetDTOList.add(orderSheetDTO);
        }

        return orderSheetDTOList;
    }

    @Override
    public List<OrderSheetDTO> findOfCompletePayment() {
        return List.of();
    }
}
