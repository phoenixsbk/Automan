package cn.lynx.automan.service.impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AuthServiceImplTest {
    @Test
    public void testEncryption() {
        String password = "12345";
        AuthServiceImpl impl = new AuthServiceImpl();
        String out = impl.encrpt(password);
        assertNotNull(out);
        assertEquals("wxrGBXk/WAs4bA+1PxuXdQ==", out);
    }
}
