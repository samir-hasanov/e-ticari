package electronic.commerce.service;

import electronic.commerce.dto.request.ReqColorProduct;
import electronic.commerce.dto.response.RespColorProduct;
import electronic.commerce.dto.response.RespStatus;
import electronic.commerce.dto.response.RespStatusList;
import electronic.commerce.dto.response.Response;
import electronic.commerce.entity.ColorProduct;
import electronic.commerce.exception.EnumCode;
import electronic.commerce.exception.ExceptionConstants;
import electronic.commerce.exception.MyException;
import electronic.commerce.repository.ColorProductRepository;
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
public class ColorProductService {

    private final ColorProductRepository colorProductRepository;

    public RespStatusList addColorProduct(ReqColorProduct reqColorProduct) {
        RespStatusList response=new RespStatusList();
        try {
            Optional<ColorProduct> product = colorProductRepository
                    .findColorProductByNameColor(reqColorProduct.getName());
            String color = reqColorProduct.getName();
            if (product.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Product color taken");
            } else if (color == null) {
                throw new MyException(ExceptionConstants.DATA_NULL, "Request null");
            }
            ColorProduct colorProduct = new ColorProduct();
            colorProduct.setNameColor(color);
            colorProductRepository.save(colorProduct);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;

    }

    public Response<List<RespColorProduct>> getAllColorName() {
        Response<List<RespColorProduct>> response = new Response<>();
        List<RespColorProduct> respList = new ArrayList<>();
        try {
            List<ColorProduct> list = colorProductRepository
                    .findColorProductByActive(EnumCode.ACTIVE.getValue());
            if (list.isEmpty()) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Color list not found");
            }
            for (ColorProduct color : list) {
                RespColorProduct respColorProduct = new RespColorProduct();
                respColorProduct.setId(color.getId());
                respColorProduct.setColor(color.getNameColor());
                respList.add(respColorProduct);
            }
            response.setT(respList);
            response.setRespStatus(RespStatus.getMessage());
            log.info(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public RespStatusList deleteColor(Long colorId) {
        RespStatusList response=new RespStatusList();
        try {
            if (colorId == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Request null");
            }
            ColorProduct colorProduct = colorProductRepository.findColorProductById(colorId);
            if (colorProduct == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Color not found");
            }
            colorProduct.setActive(EnumCode.DEACTIVATE.getValue());
            colorProductRepository.save(colorProduct);
             response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Transactional
    public RespStatusList upDateColorName(ReqColorProduct reqColorProduct) {
        RespStatusList response=new RespStatusList();
        try {
            Long colorId = reqColorProduct.getId();
            String colorName =reqColorProduct.getName();
            if (colorId == null || colorName == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Invalid request");
            }
            Optional<ColorProduct> product = colorProductRepository
                    .findColorProductByIdAndNameColorAndActive(colorId, colorName, EnumCode.ACTIVE.getValue());
            if (product.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Update taken");
            }

            ColorProduct color = colorProductRepository
                    .findColorProductByIdAndActive(colorId, EnumCode.ACTIVE.getValue());
            if (color == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Not Found");
            }
            color.setNameColor(colorName);
            colorProductRepository.save(color);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
