package cn.lynx.automan.service;

import cn.lynx.automan.data.entity.Subject;
import cn.lynx.automan.data.entity.SubjectStatus;
import cn.lynx.automan.data.entity.SubjectStatuses;

import java.util.List;

public interface SubjectService {
  List<Subject> listSubjects(SubjectStatuses status);

  List<Subject> listSubjects(int pageIndex, int pageSize);
}
