package electronic.commerce.dto.response.commerce.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RespStatusList {
    @JsonProperty(value ="status")
    private RespStatus respStatus;
}
