package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.Subject;
import cn.lynx.automan.handler.Transformer;
import cn.lynx.automan.handler.subject.SubjectStatuses;
import cn.lynx.automan.data.repo.SubjectRepository;
import cn.lynx.automan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private List<Transformer<Subject>> transformers;

  @Override
  public List<Subject> listSubjects(SubjectStatuses status) {
    return subjectRepository.listSubjectWithStatus(status.toString());
  }

  @Override
  public List<Subject> listSubjects(int pageIndex, int pageSize) {
    List<Subject> subjects = subjectRepository.listSubjects(new PageRequest(pageIndex, pageSize));
    subjects.forEach(s -> transformers.forEach(t -> t.handle(s)));
    return subjects;
  }
}
