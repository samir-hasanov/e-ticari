package electronic.commerce.dto.response.commerce.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqSizeProduct {
    private Long sizeProductId;
    private String sizeProduct;
}
