package chukchuk.orderAPI.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "bg_order")
@SequenceGenerator(
        name = "ORDER_SEQ_GENERATOR",
        sequenceName = "ORDER_SEQ",
        allocationSize = 1
)
@ToString
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

    private String account;

    private String cashReceipt;

    public void changePayment(boolean payment) {
        this.payment = payment;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeCashReceipt(String cashReceipt) {
        this.cashReceipt = cashReceipt;
    }

}
