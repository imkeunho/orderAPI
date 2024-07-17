package chukchuk.orderAPI.service;

import chukchuk.orderAPI.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProductDTOList() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductDTO productDTO = ProductDTO.builder()
                    .name("맛있는 과자" + i)
                    .price(2000)
                    .description("감자과자 포카칩")
                    .build();

            productDTOList.add(productDTO);
        }
        productService.saveAll(productDTOList);
    }

    @Test
    public void selectAllProduct() {
        List<ProductDTO> productDTOList = productService.findAll();
        for (ProductDTO productDTO : productDTOList) {
            log.info(productDTO);
        }
    }
}
