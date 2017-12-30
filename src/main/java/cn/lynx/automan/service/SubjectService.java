package cn.lynx.automan.service;

import cn.lynx.automan.data.entity.Subject;
import cn.lynx.automan.handler.subject.SubjectStatuses;

import java.util.List;

public interface SubjectService {
  List<Subject> listSubjects(SubjectStatuses status);

  List<Subject> listSubjects(int pageIndex, int pageSize);
}
