package electronic.commerce.service;

import electronic.commerce.dto.request.ReqSizeProduct;
import electronic.commerce.dto.response.RespSizeProduct;
import electronic.commerce.dto.response.RespStatus;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.entity.SizeProduct;
import electronic.commerce.exception.EnumCode;
import electronic.commerce.exception.ExceptionConstants;
import electronic.commerce.exception.MyException;
import electronic.commerce.repository.SizeProductRepository;
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
public class SizeProductService {

    private final SizeProductRepository sizeProductRepository;

    public RespStatusList addSizeProduct(ReqSizeProduct reqSizeProduct) {
        RespStatusList response=new RespStatusList();
        try {
            Optional<SizeProduct> product
                    = sizeProductRepository.
                    findSizeProductByProductSize(reqSizeProduct.getSize());
            String size=reqSizeProduct.getSize();
            if (product.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Product Size taken");
            } else if (size==null) {
                throw new MyException(ExceptionConstants.DATA_NULL, "Request null");
            }
            SizeProduct sizeProduct = new SizeProduct();
            sizeProduct.setProductSize(size);
            sizeProductRepository.save(sizeProduct);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response<List<RespSizeProduct>> getAllSizeProduct() {
        Response<List<RespSizeProduct>> response = new Response<>();
        List<RespSizeProduct> respList = new ArrayList<>();
        try {
            List<SizeProduct> list = sizeProductRepository.findSizeProductByActive(EnumCode.ACTIVE.getValue());
            if (list.isEmpty()) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Product Size list not found");
            }
            for (SizeProduct listSize : list) {
                RespSizeProduct respSizeProduct = new RespSizeProduct();
                respSizeProduct.setId(listSize.getId());
                respSizeProduct.setSize(listSize.getProductSize());
                respList.add(respSizeProduct);
            }
            response.setT(respList);
            response.setRespStatus(RespStatus.getMessage());
            log.info(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public RespStatusList deleteSizeProduct(Long sizeProductId) {
        RespStatusList response=new RespStatusList();

        try {
            if (sizeProductId == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Request null");
            }
            SizeProduct sizeProduct = sizeProductRepository.findSizeProductById(sizeProductId);
            if (sizeProduct == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Product size not found");
            }
            sizeProduct.setActive(EnumCode.DEACTIVATE.getValue());
            sizeProductRepository.save(sizeProduct);
         response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Transactional
    public RespStatusList updateSizeProduct(ReqSizeProduct reqSizeProduct) {
        RespStatusList response=new RespStatusList();
        try {
            Long sizeProductId = reqSizeProduct.getId();
            String sizeProduct = reqSizeProduct.getSize();
            log.info("id : " + sizeProductId + " -- " + "Name : " + sizeProduct);
            if (sizeProductId == null || sizeProduct == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Invalid request");
            }
            Optional<SizeProduct> product = sizeProductRepository
                    .findSizeProductByIdAndProductSizeAndActive(sizeProductId, sizeProduct, EnumCode.ACTIVE.getValue());
            if (product.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Update taken");
            }

            SizeProduct size = sizeProductRepository
                    .findSizeProductByIdAndActive(sizeProductId, EnumCode.ACTIVE.getValue());
            if (size == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Not Found");
            }
            size.setProductSize(sizeProduct);
            sizeProductRepository.save(size);
           response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}

