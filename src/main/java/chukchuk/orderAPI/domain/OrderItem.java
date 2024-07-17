package chukchuk.orderAPI.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "bg_order_item")
@SequenceGenerator(
        name = "ORDER_ITEM_SEQ_GENERATOR",
        sequenceName = "ORDER_ITEM_SEQ",
        allocationSize = 1
)
@ToString(exclude = {"product", "order"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "ORDER_ITEM_SEQ_GENERATOR")
    private Long id;

    @ManyToOne
    @JoinColumn
    private Product product;

    @ManyToOne
    @JoinColumn
    private Order order;

    private int qty;

    private int orderPrice;

    private LocalDateTime orderDate;

}
