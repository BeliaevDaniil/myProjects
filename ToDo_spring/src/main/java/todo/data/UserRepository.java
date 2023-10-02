package todo.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import todo.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getUserById(Long id);

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
