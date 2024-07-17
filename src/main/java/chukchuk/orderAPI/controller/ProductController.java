package chukchuk.orderAPI.controller;

import chukchuk.orderAPI.dto.ProductDTO;
import chukchuk.orderAPI.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public List<ProductDTO> list() {

        return productService.findAll();
    }

    @PostMapping("/")
    public Map<String, String> register(@RequestBody List<ProductDTO> productDTOList) {

        productService.saveAll(productDTOList);

        return Map.of("RESULT", "SUCCESS");
    }
}
