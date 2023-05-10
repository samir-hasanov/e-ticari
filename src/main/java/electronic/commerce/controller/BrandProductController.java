package electronic.commerce.controller;

import electronic.commerce.dto.request.ReqBrandProduct;
import electronic.commerce.dto.response.RespBrandProduct;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.service.BrandProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1.2")
@Slf4j
public class BrandProductController {

    private BrandProductService brandProductService;

    public BrandProductController(BrandProductService brandProductService) {
        this.brandProductService = brandProductService;
    }

    @PostMapping("/addBrand")
    public RespStatusList addBrandProduct(@RequestBody ReqBrandProduct reqBrandProduct){
        return brandProductService.addBrandProduct(reqBrandProduct);
    }

    @GetMapping("/getAll")
    public Response<List<RespBrandProduct>> getAllProductBrand(){
        return brandProductService.getAllProductBrand();
    }

    @PostMapping("{brandId}")
    public RespStatusList deleteBrand(@PathVariable("brandId") Long brandId){
        return brandProductService.deleteBrand(brandId);
    }

    @PutMapping("/up")
    public RespStatusList upDateBrandName(@RequestBody ReqBrandProduct reqBrandProduct){
        return brandProductService.upDateBrandName(reqBrandProduct);
    }


}
