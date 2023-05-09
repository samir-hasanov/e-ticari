package electronic.commerce.dto.response.commerce.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqBrandProduct {
    private Long brandId;
    private String nameBrand;
}
