package chukchuk.orderAPI.controller;

import chukchuk.orderAPI.dto.OrderSheetDTO;
import chukchuk.orderAPI.dto.PageRequestDTO;
import chukchuk.orderAPI.dto.PageResponseDTO;
import chukchuk.orderAPI.dto.ProductDTO;
import chukchuk.orderAPI.service.OrderService;
import chukchuk.orderAPI.service.ProductService;
import chukchuk.orderAPI.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final OrderService orderService;

    private final CustomFileUtil fileUtil;

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, Long> register(ProductDTO productDTO) {

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        long pno = productService.register(productDTO);

        return Map.of("RESULT", pno);
    }

    @PostMapping("/user/submit")
    public Map<String, String> submitOrder(@RequestBody OrderSheetDTO orderSheetDTO) {

        log.info(orderSheetDTO);

        orderService.save(orderSheetDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName) {

        return fileUtil.getFile(fileName);
    }

    @GetMapping("/user/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {

        return productService.getList(pageRequestDTO);
    }

    @GetMapping("/user/{pno}")
    public ProductDTO get(@PathVariable("pno") Long pno) {

        return productService.get(pno);
    }

    @DeleteMapping("/admin/{pno}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, String> remove(@PathVariable("pno") Long pno) {

        List<String> oldFileNames = productService.get(pno).getUploadFileNames();

        productService.remove(pno);

        fileUtil.deleteFiles(oldFileNames);

        return Map.of("RESULT", "SUCCESS");
    }

    @PutMapping("/admin/{pno}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, String> modify(@PathVariable("pno") Long pno, ProductDTO productDTO) {

        productDTO.setPno(pno);

        //old product Database saved product
        ProductDTO oldProductDTO = productService.get(pno);

        //file upload
        List<MultipartFile> files = productDTO.getFiles();
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);

        //keep file String
        List<String> uploadedFileNames = productDTO.getUploadFileNames();

        if (currentUploadFileNames != null && !currentUploadFileNames.isEmpty()) {

            uploadedFileNames.addAll(currentUploadFileNames);
        }

        productService.modify(productDTO);

        //파일 삭제
        List<String> oldFileNames = oldProductDTO.getUploadFileNames();

        if (oldFileNames != null && !oldFileNames.isEmpty()) {

            List<String> removeFiles = oldFileNames.stream()
                    .filter(fileName -> !uploadedFileNames.contains(fileName))
                    .toList();

            fileUtil.deleteFiles(removeFiles);
        }

        return Map.of("RESULT", "SUCCESS");
    }

}
