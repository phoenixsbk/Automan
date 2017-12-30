package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.AThread;
import cn.lynx.automan.data.entity.Subject;
import cn.lynx.automan.data.repo.SubjectRepository;
import cn.lynx.automan.data.repo.ThreadRepository;
import cn.lynx.automan.handler.Transformer;
import cn.lynx.automan.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadServiceImpl implements ThreadService {
  @Autowired
  private ThreadRepository threadRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private List<Transformer<AThread>> funcHandlers;

  @Override
  public List<AThread> listThreadFromSubject(int subjectId) {
    Subject subject = subjectRepository.findOne(subjectId);
    if (subject == null) {
      return null;
    }
    List<AThread> result = threadRepository.findBySubjectOrderByPublishDateDesc(subject);
    result.forEach(s -> funcHandlers.forEach(f -> f.handle(s)));
    return result;
  }
}
