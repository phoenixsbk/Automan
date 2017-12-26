package cn.lynx.automan.data.repo;

import cn.lynx.automan.data.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Integer> {
    @Transactional
    void deleteByUsername(String username);

    SessionToken findByUsernameAndSessionEndDateGreaterThanEqual(String username, Timestamp sessionEndDate);

    SessionToken findByAuthToken(String authToken);
}
