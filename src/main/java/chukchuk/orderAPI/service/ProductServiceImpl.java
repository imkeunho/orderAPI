package chukchuk.orderAPI.service;

import chukchuk.orderAPI.domain.Product;
import chukchuk.orderAPI.dto.ProductDTO;
import chukchuk.orderAPI.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> findAll() {

        List<Product> products = productRepository.findAllByOrderByIdAsc();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            productDTOList.add(entityToDTO(product));
        }

        return productDTOList;
    }

    @Override
    public void saveAll(List<ProductDTO> productDTOList) {
        List<Product> list = new ArrayList<>();
        for (ProductDTO productDTO : productDTOList) {
            list.add(dtoToEntity(productDTO));
        }
        productRepository.saveAll(list);

    }

    public Product dtoToEntity(ProductDTO productDTO) {

        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .build();
    }

    public ProductDTO entityToDTO(Product product) {

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
