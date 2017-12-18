package cn.lynx.automan.service.impl;

import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.data.repo.UserRepository;
import cn.lynx.automan.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static MessageDigest MD;

    static {
        try {
            MD = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't find the MD5 encryption, abort startup.", e);
        }
    }

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(String username, String password) {
        String alteredPwd = encrpt(password);
        User user = userRepository.findByUsernameAndPassword(username, password);
        return null;
    }

    String encrpt(String rawPassword) {
        try {
            byte[] updated = MD.digest(rawPassword.getBytes("UTF-16LE"));
            return Base64.getEncoder().encodeToString(updated);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Not gonna happen if UTF-16LE not existed.");
            return null;
        }
    }
}
