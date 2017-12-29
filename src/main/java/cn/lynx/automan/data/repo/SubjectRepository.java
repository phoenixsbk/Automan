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
  @Query("select s from Subject s join s.statuses st where st.status = :statusName order by s.updateDate desc")
  List<Subject> listSubjectWithStatus(@Param("statusName") String statusName, Pageable pageable);

  @Query("select s from Subject s join s.statuses st where st.status = :statusName order by s.updateDate desc")
  List<Subject> listSubjectWithStatus(@Param("statusName") String statusName);
}
