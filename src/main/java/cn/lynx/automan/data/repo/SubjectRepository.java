package cn.lynx.automan.data.repo;

import cn.lynx.automan.data.entity.Subject;
import cn.lynx.automan.data.entity.SubjectStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
  @Query("select s from Subject s where :status member s.statuses order by u.updateDate desc")
  List<Subject> listSubjectWithStatus(@Param("status") SubjectStatus status, Pageable pageable);
}
