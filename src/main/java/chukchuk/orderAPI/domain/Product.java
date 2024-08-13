package chukchuk.orderAPI.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "bg_product")
@SequenceGenerator(
        name = "PRODUCT_SEQ_GENERATOR",
        sequenceName = "PRODUCT_SEQ",
        allocationSize = 1
)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "productImages")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "PRODUCT_SEQ_GENERATOR")
    private Long id;

    private String name;

    private String description;

    private int price;

    private LocalDateTime createdAt;

    private boolean deleted;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

    public void addProductImages(ProductImage productImage) {

        if (productImages == null) {
            productImages = new ArrayList<>();
            productImage.insertOrd(0);
        } else {
            productImage.insertOrd(productImages.size());
        }

        productImages.add(productImage);
    }

    public void clearProductImages() {
        productImages.clear();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void changeCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
