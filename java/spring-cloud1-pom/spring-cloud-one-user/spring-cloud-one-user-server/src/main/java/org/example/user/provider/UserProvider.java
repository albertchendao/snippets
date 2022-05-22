package org.example.user.provider;

import org.example.user.client.UserClient;
import org.example.user.dto.UserDto;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProvider implements UserClient, EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Override
//    @Timed(value = "get.info.requests", histogram = true, percentiles = {0.95, 0.99}, extraTags = {"version", "v1"})
    public UserDto info(@RequestParam("name") String name) {
        if ("null".equalsIgnoreCase(name)) return null;
        UserDto result = new UserDto();
        result.setName(name);
        return result;
    }
}
