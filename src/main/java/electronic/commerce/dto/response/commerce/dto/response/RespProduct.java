package electronic.commerce.dto.response.commerce.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespProduct {
    private Long id;
    private String photo;
    private String name;
    private Integer amount;
    private Double price;
    private String serial;
    private RespProductType respProductType;
    private RespBrandProduct respBrandProduct;
    private RespSizeProduct respSizeProduct;
    private RespColorProduct respColorProduct;
}
