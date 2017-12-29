package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.Subject;
import cn.lynx.automan.data.entity.SubjectStatus;
import cn.lynx.automan.data.entity.SubjectStatuses;
import cn.lynx.automan.data.repo.SubjectRepository;
import cn.lynx.automan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
  public List<Subject> listSubjects(SubjectStatuses status) {
    return subjectRepository.listSubjectWithStatus(status.toString());
  }

  @Override
  public List<Subject> listSubjects(SubjectStatuses status, int pageIndex, int pageSize) {
    return subjectRepository.listSubjectWithStatus(status.toString(), new PageRequest(pageIndex, pageSize));
  }
}
