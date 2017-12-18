package cn.lynx.automan.data.repo;

import cn.lynx.automan.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
}
