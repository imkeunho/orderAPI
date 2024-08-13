package chukchuk.orderAPI.service;

import chukchuk.orderAPI.dto.PageRequestDTO;
import chukchuk.orderAPI.dto.PageResponseDTO;
import chukchuk.orderAPI.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testGetList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResponseDTO<ProductDTO> list =  productService.getList(pageRequestDTO);

        log.info(list);
    }
}
