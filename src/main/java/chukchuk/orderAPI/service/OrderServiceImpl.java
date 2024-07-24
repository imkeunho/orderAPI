package chukchuk.orderAPI.service;

import chukchuk.orderAPI.domain.Order;
import chukchuk.orderAPI.domain.OrderItem;
import chukchuk.orderAPI.domain.Product;
import chukchuk.orderAPI.dto.OrderItemInterface;
import chukchuk.orderAPI.dto.OrderItemListDTO;
import chukchuk.orderAPI.dto.OrderSheetDTO;
import chukchuk.orderAPI.repository.OrderItemRepository;
import chukchuk.orderAPI.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    @Override
    public void save(OrderSheetDTO orderSheetDTO) {

        Order order = Order.builder()
                .dong(orderSheetDTO.getDong())
                .ho(orderSheetDTO.getHo())
                .account(orderSheetDTO.getAccount())
                .cashReceipt(orderSheetDTO.getCashReceipt())
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
    public List<OrderItemListDTO> findByDongWithOrderQty(int dong) {

        List<OrderItemInterface> itemsWithOrderQty = null;

        if (dong == 0) {
            itemsWithOrderQty = orderItemRepository.findAllWithOrderQty();
        } else {
            itemsWithOrderQty = orderItemRepository.findByDongWithOrderQty(dong);
        }

        return itemsWithOrderQty.stream().map((this::interfaceToDTO)).toList();
    }

    @Override
    public List<OrderSheetDTO> findOfCompletePayment() {

        List<OrderItem> orderItemList = orderItemRepository.findPayment();

        return createOrderSheet(orderItemList);
    }

    @Override
    public void remove(Long ono) {
        Optional<Order> result = orderRepository.findById(ono);
        Order order = result.orElseThrow();

        order.changeComplete(true);
    }

    @Override
    public void update(Long ono) {
        Optional<Order> result = orderRepository.findById(ono);
        Order order = result.orElseThrow();

        order.changePayment(true);
    }

    public List<OrderSheetDTO> createOrderSheet(List<OrderItem> orderItemList) {

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
                    .cashReceipt(order.getCashReceipt())
                    .payment(order.isPayment())
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

    public OrderItemListDTO interfaceToDTO(OrderItemInterface orderItemInterface) {

        return OrderItemListDTO.builder()
                .name(orderItemInterface.getName())
                .price(orderItemInterface.getPrice())
                .pno(orderItemInterface.getPno())
                .qty(orderItemInterface.getQty())
                .build();
    }
}
