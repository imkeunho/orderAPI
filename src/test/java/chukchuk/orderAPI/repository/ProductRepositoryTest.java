package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insert() {
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .name("호박고구마" + i)
                    .price(1000)
                    .description("달달한 노란 고구마" + i)
                    .build();

            productRepository.save(product);
        }
    }

    @Test
    public void findAll() {
        List<Product> products = productRepository.findAllByOrderByIdAsc();
        for (Product product : products) {
            log.info(product);
        }
    }
}
