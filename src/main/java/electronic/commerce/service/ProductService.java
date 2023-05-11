package electronic.commerce.service;

import electronic.commerce.dto.entity.*;
import electronic.commerce.dto.request.*;
import electronic.commerce.dto.response.*;
import electronic.commerce.exception.EnumCode;
import electronic.commerce.exception.ExceptionConstants;
import electronic.commerce.exception.MyException;
import electronic.commerce.repository.ProductRepository;
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
public class ProductService {

    private final String PATH = "C:\\Users\\samir\\OneDrive\\Masaüstü\\fileserver";

    private final ProductRepository productRepository;


    public RespStatusList addProduct(ReqProduct reqProduct) {
        RespStatusList response = new RespStatusList();
        String name = reqProduct.getName();
        String serialNumber = reqProduct.getSerial();
        try {
            if (name == null && serialNumber == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Request invalid name or serial null");
            }
            Optional<Product> optional = productRepository.findProductBySerialNumber(serialNumber);
            if (optional.isPresent()) {
                throw new MyException(ExceptionConstants.DATA_TAKEN, "Product taken");
            }
            Product product = new Product();
            product.setPhoto(reqProduct.getPhoto());
            product.setName(name);
            product.setPrice(reqProduct.getPrice());
            product.setSerialNumber(reqProduct.getSerial());
            ////////
            ProductType productType = new ProductType();
            productType.setId(reqProduct.getType().getId());
            product.setProductType(productType);
            ///////
            BrandProduct brandProduct = new BrandProduct();
            brandProduct.setId(reqProduct.getBrand().getId());
            product.setBrandProduct(brandProduct);
            ///////
            SizeProduct sizeProduct = new SizeProduct();
            sizeProduct.setId(reqProduct.getSize().getId());
            product.setSizeProduct(sizeProduct);
            ////////
            ColorProduct colorProduct = new ColorProduct();
            colorProduct.setId(reqProduct.getColor().getId());
            product.setColorProduct(colorProduct);
            ////////
            productRepository.save(product);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }
        return response;
    }

    public Response<List<RespProduct>> getAllProduct() {
        Response<List<RespProduct>> response = new Response<>();
        List<RespProduct> listProduct = new ArrayList<>();
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Products List Not found");
            }

            for (Product product : products) {
                RespProduct respProduct = new RespProduct();
                respProduct.setId(product.getId());
                respProduct.setName(product.getName());
                respProduct.setPrice(product.getPrice());
                respProduct.setSerial(product.getSerialNumber());
                ///////////////////////////////////////////////////////
                RespProductType respProductType = new RespProductType();
                respProductType.setId(product.getProductType().getId());
                respProductType.setType(product.getProductType().getNameType());
                respProduct.setRespProductType(respProductType);
                ////////////////////////////////////////////////
                RespBrandProduct respBrandProduct = new RespBrandProduct();
                respBrandProduct.setId(product.getBrandProduct().getId());
                respBrandProduct.setBrand(product.getBrandProduct().getNameBrand());
                respProduct.setRespBrandProduct(respBrandProduct);
                ////////////////////////////////////////////////////
                RespSizeProduct respSizeProduct = new RespSizeProduct();
                respSizeProduct.setId(product.getSizeProduct().getId());
                respSizeProduct.setSize(product.getSizeProduct().getProductSize());
                respProduct.setRespSizeProduct(respSizeProduct);
                ///////////////////////////////////////////////////
                RespColorProduct respColorProduct = new RespColorProduct();
                respColorProduct.setId(product.getColorProduct().getId());
                respColorProduct.setColor(product.getColorProduct().getNameColor());
                respProduct.setRespColorProduct(respColorProduct);
                listProduct.add(respProduct);
            }
            response.setT(listProduct);
            response.setRespStatus(RespStatus.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Transactional
    public RespStatusList deleteProduct(Long productId) {
        RespStatusList response = new RespStatusList();
        try {
            if (productId == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Invalid request");
            }
            log.info("Logid : " + productId);
            Product product = productRepository.findProductById(productId);
            if (product == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Not Product found");
            }
            product.setActive(EnumCode.DEACTIVATE.getValue());
            productRepository.save(product);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal error"));
        }
        return response;
    }

    @Transactional
    public RespStatusList upDateProduct(ReqProduct reqProduct) {
        RespStatusList response = new RespStatusList();
        Long productId = reqProduct.getId();
        String name = reqProduct.getName();
        Double price = reqProduct.getPrice();
        String serialNumber = reqProduct.getSerial();
        ReqProductType reqProductType = reqProduct.getType();
        ReqBrandProduct reqBrandProduct = reqProduct.getBrand();
        ReqSizeProduct reqSizeProduct = reqProduct.getSize();
        ReqColorProduct reqColorProduct = reqProduct.getColor();
        try {
            if (productId == null && name == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "Invalid request");
            }
            Product product = productRepository
                    .findProductByIdAndActive(productId, EnumCode.ACTIVE.getValue());
            if (product == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Product not found");
            }
            product.setName(name);
            product.setPrice(price);
            product.setSerialNumber(serialNumber);
            ////
            ProductType productType = new ProductType();
            productType.setId(reqProductType.getId());
            product.setProductType(productType);
            ////
            BrandProduct brandProduct = new BrandProduct();
            brandProduct.setId(reqBrandProduct.getId());
            product.setBrandProduct(brandProduct);
            ///
            SizeProduct sizeProduct = new SizeProduct();
            sizeProduct.setId(reqSizeProduct.getId());
            product.setSizeProduct(sizeProduct);
            ///
            ColorProduct colorProduct = new ColorProduct();
            colorProduct.setId(reqColorProduct.getId());
            product.setColorProduct(colorProduct);
            ///
            productRepository.save(product);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
        }

        return response;
    }

    public Response<RespProduct> getProductById(Long productId) {
        Response<RespProduct> response = new Response<>();
        try {
            if (productId == null) {
                throw new MyException(ExceptionConstants.INVALID_REQUEST, "invalid request");
            }
            log.info("product id : = " + productId);
            Product product = productRepository.findProductByIdAndActive(productId, EnumCode.ACTIVE.getValue());
            if (product == null) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Not found Product");
            }
            RespProduct respProduct = new RespProduct();
            respProduct.setName(product.getName());
            respProduct.setSerial(product.getSerialNumber());
            respProduct.setAmount(product.getAmount());
            respProduct.setPrice(product.getPrice());
            ///////////////////////////////////////////////////////
            RespProductType respProductType = new RespProductType();
            respProductType.setId(product.getProductType().getId());
            respProductType.setType(product.getProductType().getNameType());
            respProduct.setRespProductType(respProductType);
            ////////////////////////////////////////////////
            RespBrandProduct respBrandProduct = new RespBrandProduct();
            respBrandProduct.setId(product.getBrandProduct().getId());
            respBrandProduct.setBrand(product.getBrandProduct().getNameBrand());
            respProduct.setRespBrandProduct(respBrandProduct);
            ////////////////////////////////////////////////////
            RespSizeProduct respSizeProduct = new RespSizeProduct();
            respSizeProduct.setId(product.getSizeProduct().getId());
            respSizeProduct.setSize(product.getSizeProduct().getProductSize());
            respProduct.setRespSizeProduct(respSizeProduct);
            ///////////////////////////////////////////////////
            RespColorProduct respColorProduct = new RespColorProduct();
            respColorProduct.setId(product.getColorProduct().getId());
            respColorProduct.setColor(product.getColorProduct().getNameColor());
            respProduct.setRespColorProduct(respColorProduct);
            response.setT(respProduct);
            response.setRespStatus(RespStatus.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response<List<RespProduct>> getProducts() {

        Response<List<RespProduct>> response = new Response<>();
        List<RespProduct> listProduct = new ArrayList<>();
        try {
            List<Product> products = productRepository.findProductByActive(EnumCode.ACTIVE.getValue());
            if (products.isEmpty()) {
                throw new MyException(ExceptionConstants.NOT_FOUND, "Products List Not found");
            }

            for (Product product : products) {
                RespProduct respProduct = new RespProduct();
                respProduct.setId(product.getId());
               // String image = Base64.getEncoder().encodeToString(product.getPhoto());
                //log.info("image : " + image);
                //respProduct.setPhoto(image);
                respProduct.setName(product.getName());
                respProduct.setPrice(product.getPrice());
                respProduct.setSerial(product.getSerialNumber());
                ///////////////////////////////////////////////////////
                RespProductType respProductType = new RespProductType();
                respProductType.setId(product.getProductType().getId());
                respProductType.setType(product.getProductType().getNameType());
                respProduct.setRespProductType(respProductType);
                ////////////////////////////////////////////////
                RespBrandProduct respBrandProduct = new RespBrandProduct();
                respBrandProduct.setId(product.getBrandProduct().getId());
                respBrandProduct.setBrand(product.getBrandProduct().getNameBrand());
                respProduct.setRespBrandProduct(respBrandProduct);
                ////////////////////////////////////////////////////
                RespSizeProduct respSizeProduct = new RespSizeProduct();
                respSizeProduct.setId(product.getSizeProduct().getId());
                respSizeProduct.setSize(product.getSizeProduct().getProductSize());
                respProduct.setRespSizeProduct(respSizeProduct);
                ///////////////////////////////////////////////////
                RespColorProduct respColorProduct = new RespColorProduct();
                respColorProduct.setId(product.getColorProduct().getId());
                respColorProduct.setColor(product.getColorProduct().getNameColor());
                respProduct.setRespColorProduct(respColorProduct);
                listProduct.add(respProduct);
            }
            response.setT(listProduct);
            response.setRespStatus(RespStatus.getMessage());

        } catch (Exception e) {

            e.printStackTrace();
        }
        return response;
    }
}
