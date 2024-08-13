package chukchuk.orderAPI.service;

import chukchuk.orderAPI.dto.PageRequestDTO;
import chukchuk.orderAPI.dto.PageResponseDTO;
import chukchuk.orderAPI.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO);

    ProductDTO get(Long pno);

    void remove(Long pno);

    void modify(ProductDTO productDTO);

}
