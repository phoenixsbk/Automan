package cn.lynx.automan.data.repo;

import cn.lynx.automan.data.entity.AThread;
import cn.lynx.automan.data.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadRepository extends JpaRepository<AThread, Integer> {
  List<AThread> findBySubjectOrderByPublishDateDesc(Subject subject);
}
