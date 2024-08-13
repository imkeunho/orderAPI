package chukchuk.orderAPI.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderSheetDTO {

    private Long oid;

    private int dong, ho;

    private String account;

    private List<OrderItemListDTO> items;

    private String cashReceipt;

    private boolean payment;

    private String orderDate;
}
