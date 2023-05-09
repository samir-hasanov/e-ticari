package electronic.commerce.repository;

import electronic.commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findProductBySerialNumber(String serialNumber);
    Product findProductById(Long productId);
    Product findProductByIdAndActive(Long productId,Integer active);
    List<Product> findProductByActive(Integer active);


}
