package electronic.commerce.repository;

import electronic.commerce.dto.entity.ColorProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorProductRepository extends JpaRepository<ColorProduct, Long> {
    Optional<ColorProduct> findColorProductByNameColor(String colorName);

    List<ColorProduct> findColorProductByActive(Integer active);

    ColorProduct findColorProductById(Long colorId);

    Optional<ColorProduct>
    findColorProductByIdAndNameColorAndActive(Long colorId, String colorName, Integer active);

    ColorProduct findColorProductByIdAndActive(Long colorId, Integer active);
}
