package electronic.commerce.dto.response.commerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@DynamicInsert
public class Product {

    @Id
    @GeneratedValue
    private Long Id;
    @Column(name = "photo")
    private String photo;
    @Column(name = "name_product")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "amount")
    private Integer amount;
    @Column(unique = true)
    private String serialNumber;
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandProduct brandProduct;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private SizeProduct sizeProduct;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private ColorProduct colorProduct;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;


}
