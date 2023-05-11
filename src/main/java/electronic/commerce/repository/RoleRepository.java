package electronic.commerce.repository;

import electronic.commerce.dto.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findRoleByName(String name);
}
