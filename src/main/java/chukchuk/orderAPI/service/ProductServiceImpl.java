package chukchuk.orderAPI.service;

import chukchuk.orderAPI.domain.Product;
import chukchuk.orderAPI.domain.ProductImage;
import chukchuk.orderAPI.dto.PageRequestDTO;
import chukchuk.orderAPI.dto.PageResponseDTO;
import chukchuk.orderAPI.dto.ProductDTO;
import chukchuk.orderAPI.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("id").descending());

        Page<Product> result = productRepository.selectList(pageable);

        // Object[] -> 0 Product 1 ProductImage
        List<ProductDTO> dtoList = result.get().map(product -> {

            List<ProductImage> productImage = product.getProductImages();

            ProductDTO productDTO = ProductDTO.builder()
                    .pno(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(product.getCreatedAt()))
                    .build();

            List<String> fileNames = productImage.stream().map(ProductImage::getFileName).toList();
            productDTO.setUploadFileNames(fileNames);

            return productDTO;

        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();


        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();

    }

    @Override
    public Long register(ProductDTO productDTO) {

        Product product = dtoToEntity(productDTO);
        product.changeCreatedAt(LocalDateTime.now());

        return productRepository.save(product).getId();
    }

    @Override
    public ProductDTO get(Long pno) {

        Optional<Product> result = productRepository.findById(pno);
        Product product = result.orElseThrow();

        return entityToDTO(product);
    }

    @Override
    public void remove(Long pno) {

        productRepository.deleteById(pno);
    }

    @Override
    public void modify(ProductDTO productDTO) {

        //조회
        Optional<Product> result = productRepository.findById(productDTO.getPno());

        Product product = result.orElseThrow();
        //변경 내용 반영
        product.changePrice(productDTO.getPrice());
        product.changeName(productDTO.getName());
        product.changeDescription(productDTO.getDescription());
        product.changeDeleted(productDTO.isDeleted());

        //이미지 처리
        List<String> uploadFileNames = productDTO.getUploadFileNames();

        product.clearProductImages();

        if (uploadFileNames != null && !uploadFileNames.isEmpty()) {

            uploadFileNames.forEach(str -> {
                ProductImage productImage = ProductImage.builder()
                        .product(product)
                        .fileName(str)
                        .build();

                product.addProductImages(productImage);
            });
        }

        //저장
        productRepository.save(product);
    }

    public Product dtoToEntity(ProductDTO productDTO) {

        Product product = Product.builder()
                .id(productDTO.getPno())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .productImages(new ArrayList<>())
                .build();

        List<String> uploadFileNames = productDTO.getUploadFileNames();

        if (uploadFileNames == null || uploadFileNames.isEmpty()) {
            return product;
        }

        for (String uploadFileName : uploadFileNames) {
             ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .fileName(uploadFileName)
                    .build();

            product.addProductImages(productImage);
        }

        return product;
    }

    public ProductDTO entityToDTO(Product product) {

        ProductDTO productDTO = ProductDTO.builder()
                .pno(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .deleted(product.isDeleted())
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(product.getCreatedAt()))
                .build();

        List<ProductImage> productImages = product.getProductImages();

        if (productImages == null || productImages.isEmpty()) {
            return productDTO;
        }

        List<String> fileNames = productImages.stream().map(ProductImage::getFileName).toList();

        productDTO.setUploadFileNames(fileNames);

        return productDTO;

    }

}
