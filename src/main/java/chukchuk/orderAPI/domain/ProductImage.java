package chukchuk.orderAPI.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "bg_product_image")
@SequenceGenerator(
        name = "PRODUCT_IMAGE_SEQ_GENERATOR",
        sequenceName = "PRODUCT_IMAGE_SEQ",
        allocationSize = 1
)
@ToString(exclude = "product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "PRODUCT_IMAGE_SEQ_GENERATOR")
    private Long id;

    @ManyToOne
    @JoinColumn
    private Product product;

    private String fileName;

    private int ord;

    public void insertOrd(int ord) {
        this.ord = ord;
    }
}
