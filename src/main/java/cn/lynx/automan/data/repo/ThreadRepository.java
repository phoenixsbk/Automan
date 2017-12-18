package cn.lynx.automan.data.repo;

import cn.lynx.automan.data.entity.AThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<AThread, Integer> {
}
