package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.SessionToken;
import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.data.repo.UserRepository;
import cn.lynx.automan.service.AuthService;
import cn.lynx.automan.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private MessageDigest md;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't find the MD5 encryption, abort startup.", e);
        }
    }

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, encrypt(password));
        if (user == null) {
            return null;
        } else {
            SessionToken token = tokenService.getOrCreateToken(username);
            return token.getAuthToken();
        }
    }

    String encrypt(String rawPassword) {
        try {
            byte[] updated = md.digest(rawPassword.getBytes("UTF-16LE"));
            return Base64.getEncoder().encodeToString(updated);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Not gonna happen if UTF-16LE not existed.");
            return null;
        }
    }
}
