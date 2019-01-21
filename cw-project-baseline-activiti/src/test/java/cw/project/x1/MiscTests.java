package cw.project.x1;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MiscTests {
    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(-1);
        String encoded = bpe.encode("123456789");
        System.out.println(encoded);
        System.out.println(encoded.length());
    }
}
