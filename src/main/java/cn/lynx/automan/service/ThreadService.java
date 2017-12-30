package cn.lynx.automan.service;

import cn.lynx.automan.data.entity.AThread;

import java.util.List;

public interface ThreadService {
  List<AThread> listThreadFromSubject(int subjectId);
}
