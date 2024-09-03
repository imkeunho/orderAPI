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
import java.time.format.DateTimeFormatter;
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
                .orderDate(LocalDateTime.now())
                .build();

        List<OrderItem> list = orderSheetDTO.getItems().stream().map((dto) ->
                OrderItem.builder()
                        .order(order)
                        .product(Product.builder().id(dto.getPno()).build())
                        .qty(dto.getQty())
                        .build()).toList();

        list.forEach(order::addOrderItem);

        orderRepository.save(order);
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

        List<OrderItem> orderItemList = orderItemRepository.customFindAll();

        return createOrderSheet(orderItemList);
    }

    @Override
    public void remove(Long ono) {
        orderRepository.deleteById(ono);
    }

    @Override
    public void updatePayment(Long ono) {
        Optional<Order> result = orderRepository.findById(ono);
        Order order = result.orElseThrow();

        order.changePayment(!order.isPayment());
    }

    @Override
    public void complete(Long ono) {
        Optional<Order> result = orderRepository.findById(ono);
        Order order = result.orElseThrow();

        order.changeComplete(true);
    }

    @Override
    public void freezing() {
        List<Order> orders = orderRepository.findNotFreezing();
        orders.forEach(order -> order.changeFreezing(true));
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
                    .complete(order.isComplete())
                    .orderDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(order.getOrderDate()))
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
