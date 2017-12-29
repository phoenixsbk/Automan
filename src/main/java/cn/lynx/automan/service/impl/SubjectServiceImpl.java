package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.Subject;
import cn.lynx.automan.data.entity.SubjectStatus;
import cn.lynx.automan.data.entity.SubjectStatuses;
import cn.lynx.automan.data.repo.SubjectRepository;
import cn.lynx.automan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
  @Autowired
  private SubjectRepository subjectRepository;

  @Override
  public List<Subject> listSubjects(SubjectStatuses statuses) {
    List<Subject> subjectList = subjectRepository.listSubjectWithStatus();
    List<Subject> statusFilteredList = subjectList.stream().filter(s -> {
      boolean result = true;
      for (SubjectStatuses status : statuses) {
        result = result && s.getStatuses().stream().anyMatch(ss -> ss.getStatus().startsWith(status.toString()));
      }
      return result;
    }).collect(Collectors.toList());

    statusFilteredList.stream().sorted((x,y) -> {
      Optional<Set<SubjectStatus>> xOp = Optional.ofNullable(x.getStatuses()).filter(xx -> xx.stream().anyMatch(xs -> xs.getStatus().startsWith(SubjectStatuses.TOP.toString())));
      boolean xMatch = x.getStatuses().stream().anyMatch(ss -> ss.getStatus().startsWith(SubjectStatuses.TOP.toString()));
      boolean yMatch = y.getStatuses().stream().anyMatch(yy -> yy.getStatus().startsWith(SubjectStatuses.TOP.toString()));
      if () {
        return 1;
      } else {
        return x.getUpdateDate().compareTo(y.getUpdateDate());
      }
    });
  }
}
