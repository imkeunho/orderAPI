package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.freezing = false ")
    List<Order> findNotFreezing();
}
