package electronic.commerce.service;

import electronic.commerce.dto.request.ReqProductType;
import electronic.commerce.dto.response.RespProductType;
import electronic.commerce.dto.response.RespStatus;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.dto.entity.ProductType;
import electronic.commerce.exception.EnumCode;
import electronic.commerce.exception.ExceptionConstants;
import electronic.commerce.exception.MyException;
import electronic.commerce.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;


    public RespStatusList addProductType(ReqProductType reqProductType) {
        RespStatusList response=new RespStatusList();
        try {
            Optional<ProductType> product = productTypeRepository
                    .findProductTypeByNameType(reqProductType.getName());
            String nameType=reqProductType.getName();
            if (product.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Product type taken");
            } else if (nameType == null) {
                throw new MyException(ExceptionConstants.DATA_NULL, "Request null");
            }
            ProductType productType = new ProductType();
            productType.setNameType(nameType);
            productTypeRepository.save(productType);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;

    }

    public Response<List<RespProductType>> getAllProductType() {
        Response<List<RespProductType>> response = new Response<>();
        List<RespProductType> respList = new ArrayList<>();
        try {
            List<ProductType> list = productTypeRepository.findProductTypeByActive(EnumCode.ACTIVE.getValue());
            if (list.isEmpty()) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Product Type list not found");
            }
            for (ProductType listType : list) {
                RespProductType respProductType = new RespProductType();
                respProductType.setId(listType.getId());
                respProductType.setType(listType.getNameType());
                respList.add(respProductType);
            }
            response.setT(respList);
            response.setRespStatus(RespStatus.getMessage());
            log.info(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public RespStatusList deleteProductType(Long typeId) {

        RespStatusList response=new RespStatusList();

        try {
            if (typeId == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Request null");
            }
            ProductType productType = productTypeRepository.findProductTypeById(typeId);
            if (productType == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "ProductType not found");
            }
            productType.setActive(EnumCode.DEACTIVATE.getValue());
            productTypeRepository.save(productType);
           response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    @Transactional
    public RespStatusList updateProductType(ReqProductType reqProductType) {
        RespStatusList response=new RespStatusList();
        try {
            Long typeId = reqProductType.getId();
            String name = reqProductType.getName();
            if (typeId == null || name == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Invalid request");
            }
            Optional<ProductType> product = productTypeRepository.
                    findProductTypeByIdAndNameTypeAndActive(typeId, name, EnumCode.ACTIVE.getValue());
            if (product.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Update taken");
            }

            ProductType productType = productTypeRepository
                    .findProductTypeByIdAndActive(typeId, EnumCode.ACTIVE.getValue());
            if (productType == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Not Found");
            }
            productType.setNameType(name);
            productTypeRepository.save(productType);
          response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
