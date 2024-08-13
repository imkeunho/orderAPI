package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Product;
import chukchuk.orderAPI.domain.ProductImage;
import chukchuk.orderAPI.dto.ProductDTO;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {

        for (int i = 0; i < 5; i++) {

            Product product = Product.builder()
                    .name("Test..." + i)
                    .description("Test Desc")
                    .price(1000)
                    .productImages(new ArrayList<>())
                    .build();

            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .fileName("image_" + i + i)
                    .build();

            product.addProductImages(productImage);

            if (i % 2 == 0) {
                ProductImage productImage1 = ProductImage.builder()
                        .product(product)
                        .fileName("image_" + i)
                        .build();

                product.addProductImages(productImage1);

            }
            productRepository.save(product);
        }

    }

    @Test
    @Transactional
    public void testList() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        Page<Product> result = productRepository.selectList(pageable);

        result.get().forEach(product -> {
            log.info(product.toString());
            List<ProductImage> productImages = product.getProductImages();
            log.info(productImages.toString());
        });
    }
}
