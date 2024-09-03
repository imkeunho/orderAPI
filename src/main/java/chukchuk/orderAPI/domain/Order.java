package chukchuk.orderAPI.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "bg_order")
@SequenceGenerator(
        name = "ORDER_SEQ_GENERATOR",
        sequenceName = "ORDER_SEQ",
        allocationSize = 1
)
@ToString(exclude = "orderItems")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ_GENERATOR")
    private Long id;

    private int dong;

    private int ho;

    private boolean payment;

    private boolean complete;

    private boolean freezing;

    private LocalDateTime orderDate;

    private String account;

    private String cashReceipt;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(orderItem);
    }

    public void changePayment(boolean payment) {
        this.payment = payment;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeFreezing(boolean freezing) {
        this.freezing = freezing;
    }

    public void changeCashReceipt(String cashReceipt) {
        this.cashReceipt = cashReceipt;
    }

    public void changeOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

}
