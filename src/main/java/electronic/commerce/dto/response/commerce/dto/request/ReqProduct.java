package electronic.commerce.dto.response.commerce.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ReqProduct {
    private Long id;
    private String photo;
    private String name;
    private Integer amount;
    private Double price;
    private String serial;
    private ReqProductType type;
    private ReqBrandProduct brand;
    private ReqSizeProduct size;
    private ReqColorProduct color;


}
