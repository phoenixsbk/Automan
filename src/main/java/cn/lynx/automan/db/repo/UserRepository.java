package cn.lynx.automan.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.lynx.automan.db.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
