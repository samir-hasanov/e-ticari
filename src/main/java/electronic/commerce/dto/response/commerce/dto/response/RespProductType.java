package electronic.commerce.dto.response.commerce.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespProductType {
    private Long id;
    private String type;
}
