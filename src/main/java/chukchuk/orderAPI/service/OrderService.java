package chukchuk.orderAPI.service;

import chukchuk.orderAPI.dto.OrderItemListDTO;
import chukchuk.orderAPI.dto.OrderSheetDTO;

import java.util.List;

public interface OrderService {

    //주문 등록
    void save(OrderSheetDTO orderSheetDTO);

    //동별 상품 주문 수량
    List<OrderItemListDTO> findByDongWithOrderQty(int dong);

    //입금확인 완료된(삭제 flag 안된) 주문 내역
    List<OrderSheetDTO> findOfCompletePayment();

    //주문 내역 삭제 (complete = true)
    void remove(Long ono);

    //주문 내역 업데이트 (입급확인 payment = true)
    void updatePayment(Long ono);

    //주문 내역 완료 (complete = true)
    void complete(Long ono);

    //주문 내역 프리징 (freezing = true) 주문내역 초기화, 안보이게 하기
    void freezing();

}
