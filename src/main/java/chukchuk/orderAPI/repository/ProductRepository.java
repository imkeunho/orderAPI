package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.deleted = false")
    List<Product> findAllByNotDelete();

    @Query("select " +
           "    p " +
           "from Product p " +
           "where p.deleted = false ")
    Page<Product> selectList(Pageable pageable);
}
