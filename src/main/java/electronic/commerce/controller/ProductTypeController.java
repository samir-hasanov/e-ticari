package electronic.commerce.controller;

import electronic.commerce.dto.request.ReqProductType;
import electronic.commerce.dto.response.RespProductType;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.1")
@RequiredArgsConstructor
@Slf4j
public class ProductTypeController {

private final ProductTypeService productTypeService;

    @PostMapping("/addType")
    public RespStatusList addProductType(@RequestBody ReqProductType reqProductType){
        return productTypeService.addProductType(reqProductType);
    }

    @GetMapping("/getAll")
    public Response<List<RespProductType>> getAllProductType(){
        return productTypeService.getAllProductType();
    }
    @PostMapping("{productTypeId}")
    public RespStatusList deleteProductType(@PathVariable("productTypeId") Long productTypeId){
        return productTypeService.deleteProductType(productTypeId);
    }
    @PutMapping("/up")
    public RespStatusList updateProductType(@RequestBody ReqProductType reqProductType){
        log.info("id:"+reqProductType.getId()+"name: "+reqProductType.getName());
        return productTypeService.updateProductType(reqProductType);
    }
}
