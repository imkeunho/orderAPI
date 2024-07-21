package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByOrderByIdAsc();

    @Query("select p from Product p where p.deleted = false")
    List<Product> findAllByNotDelete();


}
