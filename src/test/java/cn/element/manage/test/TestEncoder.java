package cn.element.manage.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestEncoder {

    /**
     * $2a$10$ZZgWD3gCX41L/zFFH1iDmOV/0cvgafW8qlRm7UtETeEgfXCM6AsBu
     * $2a$10$iJuQYAm/FJIAkU64dNt3TezcA3MsTA.SMYr/TnzaDa3/sSQ/89Q.6
     * $2a$10$hpoPDHA4VP04NLBrj7JAV.tPXUg6oKtHxLMq.7yYJOym4680hbxbK
     */
    @Test
    public void testEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
