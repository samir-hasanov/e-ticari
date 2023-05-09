package electronic.commerce.repository;

import electronic.commerce.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType,Long> {

    Optional<ProductType> findProductTypeByNameType(String nameType);

    List<ProductType> findProductTypeByActive(Integer active);

    ProductType findProductTypeById(Long productTypeId);

    Optional<ProductType> findProductTypeByIdAndNameTypeAndActive(Long id,String name ,Integer active);

    ProductType findProductTypeByIdAndActive(Long id,Integer active);
}
