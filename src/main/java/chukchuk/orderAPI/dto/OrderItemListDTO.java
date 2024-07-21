package chukchuk.orderAPI.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class OrderItemListDTO {

    private Long pno;

    private int qty;

    private int price;

    private String name;

    public OrderItemListDTO(Long pno, int qty, int price, String name) {
        this.pno = pno;
        this.qty = qty;
        this.price = price;
        this.name = name;
    }
}
