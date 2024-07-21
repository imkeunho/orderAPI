package chukchuk.orderAPI.service;

import chukchuk.orderAPI.dto.OrderSheetDTO;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface OrderService {

    //주문 등록
    void save(OrderSheetDTO orderSheetDTO);

    //모든 주문 내역
    List<OrderSheetDTO> findAll();

    //입금확인 완료된(삭제 flag 안된) 주문 내역
    List<OrderSheetDTO> findOfCompletePayment();

}
