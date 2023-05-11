package electronic.commerce.repository;

import electronic.commerce.dto.entity.BrandProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandProductRepository extends JpaRepository<BrandProduct,Long> {

    Optional<BrandProduct> findBrandProductByNameBrand(String name);
    List<BrandProduct> findBrandProductByActive(Integer active);
    BrandProduct findBrandProductById(Long id);
    Optional<BrandProduct>
    findBrandProductByIdAndNameBrandAndActive(Long brandId,String brandName,Integer active);
    BrandProduct findBrandProductByIdAndActive(Long brandId,Integer active);
}
