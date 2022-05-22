package org.example.user.provider;

import io.micrometer.core.annotation.Counted;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
public class DevProvider {

    @Counted("http_requests_total_dev_hello")
    @GetMapping("/hello")
    public String hello() {
        return "world";
    }

    @GetMapping("/hh")
    public String hh() {
        return "good";
    }
}
