package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
