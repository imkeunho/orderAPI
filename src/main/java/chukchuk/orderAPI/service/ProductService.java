package chukchuk.orderAPI.service;

import chukchuk.orderAPI.dto.ProductDTO;
import chukchuk.orderAPI.dto.ResponseDTO;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface ProductService {

    List<ProductDTO> findAll();

    void saveAll(List<ProductDTO> productList);
}
