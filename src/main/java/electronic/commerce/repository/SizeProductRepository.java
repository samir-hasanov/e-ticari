package electronic.commerce.repository;

import electronic.commerce.dto.entity.SizeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeProductRepository extends JpaRepository<SizeProduct,Long> {

   Optional<SizeProduct> findSizeProductByProductSize(String size);
   List<SizeProduct> findSizeProductByActive(Integer active);
   SizeProduct findSizeProductById(Long sizeId);
   Optional<SizeProduct>
   findSizeProductByIdAndProductSizeAndActive(Long sizeId,String sizeProduct,Integer active);
   SizeProduct findSizeProductByIdAndActive(Long sizeId,Integer active);
}
