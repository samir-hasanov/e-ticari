package electronic.commerce.controller;


import electronic.commerce.dto.request.ReqSizeProduct;
import electronic.commerce.dto.response.RespSizeProduct;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.service.SizeProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1.3")
@RequiredArgsConstructor
@Slf4j
public class SizeProductController {

    private final SizeProductService sizeProductService;

    @PostMapping("/addSize")
    public RespStatusList addSizeProduct(@RequestBody ReqSizeProduct reqSizeProduct){
        return sizeProductService.addSizeProduct(reqSizeProduct);
    }

    @GetMapping("/getAll")
    public Response<List<RespSizeProduct>> getAllSizeProduct(){
        return sizeProductService.getAllSizeProduct();
    }

    @PostMapping("{sizeId}")
    public RespStatusList deleteSizeProduct(@PathVariable("sizeId") Long sizeProductId){
        return sizeProductService.deleteSizeProduct(sizeProductId);
    }

    @PutMapping("/up")
    public RespStatusList updateSizeProduct(@RequestBody ReqSizeProduct reqSizeProduct){
        log.info("id:"+reqSizeProduct.getId()+"name: "+reqSizeProduct.getSize());
        return sizeProductService.updateSizeProduct(reqSizeProduct);
    }

}
