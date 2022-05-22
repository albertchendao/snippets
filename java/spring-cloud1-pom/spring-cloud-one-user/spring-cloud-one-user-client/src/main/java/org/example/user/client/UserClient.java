package org.example.user.client;

import org.example.user.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
public interface UserClient {

    @GetMapping("/info")
    UserDto info(@RequestParam("name") String name);
}
