package electronic.commerce.service;

import electronic.commerce.dto.request.ReqBrandProduct;
import electronic.commerce.dto.response.RespBrandProduct;
import electronic.commerce.dto.response.RespStatus;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.dto.entity.BrandProduct;
import electronic.commerce.exception.EnumCode;
import electronic.commerce.exception.ExceptionConstants;
import electronic.commerce.exception.MyException;
import electronic.commerce.repository.BrandProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandProductService {

    private final BrandProductRepository brandProductRepository;

    public RespStatusList addBrandProduct(ReqBrandProduct reqBrandProduct) {
        RespStatusList response=new RespStatusList();
        String nameBrand=reqBrandProduct.getName();
        try {
            Optional<BrandProduct> brand = brandProductRepository
                    .findBrandProductByNameBrand(nameBrand);
            if (brand.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Product brand taken");
            } else if (nameBrand == null) {
                throw new MyException(ExceptionConstants.DATA_NULL, "Request null");
            }
            BrandProduct brandProduct = new BrandProduct();
            brandProduct.setNameBrand(nameBrand);
            brandProductRepository.save(brandProduct);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response<List<RespBrandProduct>> getAllProductBrand() {
        Response<List<RespBrandProduct>> response = new Response<>();
        List<RespBrandProduct> respList = new ArrayList<>();
        try {
            List<BrandProduct> list = brandProductRepository
                    .findBrandProductByActive(EnumCode.ACTIVE.getValue());
            if (list.isEmpty()) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Product brand list not found");
            }
            for (BrandProduct listBrand : list) {
                RespBrandProduct respBrandProduct = new RespBrandProduct();
                respBrandProduct.setId(listBrand.getId());
                respBrandProduct.setBrand(listBrand.getNameBrand());
                respList.add(respBrandProduct);
            }
            response.setT(respList);
            response.setRespStatus(RespStatus.getMessage());
            log.info(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public RespStatusList deleteBrand(Long brandId) {
        RespStatusList response=new RespStatusList();
        try {
            if (brandId == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Request null");
            }
            BrandProduct brandProduct = brandProductRepository.findBrandProductById(brandId);
            if (brandProduct == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Brand not found");
            }
            brandProduct.setActive(EnumCode.DEACTIVATE.getValue());
            brandProductRepository.save(brandProduct);
            response.setRespStatus(RespStatus.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public RespStatusList upDateBrandName(ReqBrandProduct reqBrandProduct) {
        RespStatusList response=new RespStatusList();
        try {
            Long brandId = reqBrandProduct.getId();
            String brandName =reqBrandProduct.getName();
            if (brandId == null || brandName == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Invalid request");
            }
            Optional<BrandProduct> product = brandProductRepository
                    .findBrandProductByIdAndNameBrandAndActive(brandId, brandName, EnumCode.ACTIVE.getValue());
            if (product.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Update taken");
            }
            BrandProduct brand = brandProductRepository
                    .findBrandProductByIdAndActive(brandId, EnumCode.ACTIVE.getValue());
            if (brand == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Not Found");
            }
            brand.setNameBrand(brandName);
            brandProductRepository.save(brand);
              response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
