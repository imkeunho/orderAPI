package chukchuk.orderAPI.service;

import chukchuk.orderAPI.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    void saveAll(List<ProductDTO> productList);
}
