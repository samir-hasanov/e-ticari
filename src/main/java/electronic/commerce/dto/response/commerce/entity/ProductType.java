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
public class ProductType {
    @Id
    @GeneratedValue
    private Long Id;
    @Column(unique = true)
    private String nameType;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
