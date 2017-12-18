package cn.lynx.automan.data.repo;

import cn.lynx.automan.data.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Integer> {
    void deleteByUsername(String username);

    SessionToken findByUsernameAndSessionEndDateGreaterThanEqual(String username, Timestamp sessionEndDate);
}
