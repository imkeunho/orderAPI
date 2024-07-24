package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.OrderItem;
import chukchuk.orderAPI.dto.OrderItemInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    //모든 주문 내역
    @Query("select " +
            "oi " +
            "from OrderItem oi " +
            "where oi.product.deleted = false " +
            "order by oi.order.dong, oi.order.ho")
    List<OrderItem> customFindAll();

    //삭제안된(입금확인 완료) 주문 내역
    @Query("select " +
            "oi " +
            "from OrderItem oi " +
            "where oi.order.complete = false" +
            "   and oi.product.deleted = false " +
            "order by oi.order.dong, oi.order.ho")
    List<OrderItem> findPayment();

    //동별 주문 내역
    @Query(value =
            "select " +
            "    pro.pno," +
            "    pro.name," +
            "    pro.price," +
            "    pro.qty " +
            "from " +
            "    (select p.id as pno," +
            "       p.NAME as name," +
            "       p.PRICE as price," +
            "       (select sum(OI.QTY) " +
            "        from BG_ORDER_ITEM OI " +
            "                 inner join BG_ORDER O on O.ID = OI.ORDER_ID " +
            "        where " +
            "          OI.PRODUCT_ID = P.ID" +
            "          and O.COMPLETE = 0) as qty" +
            "    from BG_PRODUCT P) pro " +
            "where pro.qty > 0 " +
            "order by pro.pno", nativeQuery = true)
    List<OrderItemInterface> findAllWithOrderQty();

    //상품별 주문 현황(주문 수)
    @Query(value =
            "select " +
            "    pro.pno," +
            "    pro.name," +
            "    pro.price," +
            "    pro.qty " +
            "from " +
            "    (select p.id as pno," +
            "       p.NAME as name," +
            "       p.PRICE as price," +
            "       (select sum(OI.QTY) " +
            "        from BG_ORDER_ITEM OI " +
            "                 inner join BG_ORDER O on O.ID = OI.ORDER_ID " +
            "        where " +
            "          OI.PRODUCT_ID = P.ID" +
            "          and O.COMPLETE = 0" +
            "          and O.DONG = :dong) as qty" +
            "    from BG_PRODUCT P) pro " +
            "where pro.qty > 0 " +
            "order by pro.pno", nativeQuery = true)
    List<OrderItemInterface> findByDongWithOrderQty(@Param("dong") int dong);
}
