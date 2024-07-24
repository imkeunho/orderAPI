package chukchuk.orderAPI.service;

import chukchuk.orderAPI.domain.Product;
import chukchuk.orderAPI.dto.ProductDTO;
import chukchuk.orderAPI.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> findAll() {

        List<Product> products = productRepository.findAllByNotDelete();

        return products.stream().map((this::entityToDTO)).toList();
    }

    @Override
    public void saveAll(List<ProductDTO> productDTOList) {
        //기존 product 모두 비활성화 -> deleted true
        List<Product> products = productRepository.findAllByNotDelete();
        products.forEach(product -> product.changeDeleted(true));

        List<Product> list = productDTOList.stream().map((this::dtoToEntity)).toList();

        productRepository.saveAll(list);

    }

    public Product dtoToEntity(ProductDTO productDTO) {

        return Product.builder()
                .id(productDTO.getPno())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .build();
    }

    public ProductDTO entityToDTO(Product product) {

        return ProductDTO.builder()
                .pno(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
