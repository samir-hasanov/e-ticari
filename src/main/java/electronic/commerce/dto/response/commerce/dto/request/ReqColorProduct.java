package electronic.commerce.dto.response.commerce.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqColorProduct {
    private Long colorProductId;
    private String nameColor;
}
