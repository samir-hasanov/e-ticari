package electronic.commerce.controller;

import electronic.commerce.dto.request.ReqColorProduct;
import electronic.commerce.dto.response.RespColorProduct;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.service.ColorProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.4")
@RequiredArgsConstructor
@Slf4j
public class ColorProductController {

    private final ColorProductService colorProductService;

    @PostMapping("/addColor")
    public RespStatusList addColorProduct(@RequestBody ReqColorProduct reqColorProduct){
        return colorProductService.addColorProduct(reqColorProduct);
    }

    @GetMapping("/getAll")
    public Response<List<RespColorProduct>> getAllColorName(){
        return colorProductService.getAllColorName();
    }

    @PostMapping("{colorId}")
    public RespStatusList deleteColor(@PathVariable("colorId") Long colorId){
        return colorProductService.deleteColor(colorId);
    }
    @PutMapping("/up")
    public RespStatusList upDateColorName(@RequestBody ReqColorProduct reqColorProduct){
        return colorProductService.upDateColorName(reqColorProduct);
    }

}
