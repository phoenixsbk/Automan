package cn.lynx.automan.service;

import cn.lynx.automan.data.entity.SessionToken;

public interface TokenService {
    SessionToken getOrCreateToken(String username);
}
