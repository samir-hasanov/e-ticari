package electronic.commerce.controller;
import electronic.commerce.dto.request.ReqProduct;
import electronic.commerce.dto.response.RespProduct;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/addProduct")
    public RespStatusList addProduct(@RequestBody ReqProduct reqProduct){

          return productService.addProduct(reqProduct);

    }

    @GetMapping("/getAll")
    public Response<List<RespProduct>> getAllProduct(){

        return productService.getAllProduct();
    }


    @GetMapping("/getProducts")
    public Response<List<RespProduct>> getProducts(){

        return productService.getProducts();
    }

    @GetMapping("/{selectById}")
    public Response<RespProduct> getProductById(@PathVariable("selectById") Long productId){

        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public RespStatusList deleteProduct(@PathVariable("productId") Long productId){

        return productService.deleteProduct(productId);
    }

    @PutMapping("/up")
    public RespStatusList upDateProduct(@RequestBody ReqProduct reqProduct){
        return productService.upDateProduct(reqProduct);
    }


}
