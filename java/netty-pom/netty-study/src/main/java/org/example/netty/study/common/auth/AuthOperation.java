package org.example.netty.study.common.auth;

import lombok.Data;
import lombok.extern.java.Log;
import org.example.netty.study.common.Operation;

@Data
@Log
public class AuthOperation extends Operation {

    private final String userName;
    private final String password;

    @Override
    public AuthOperationResult execute() {
        if("admin".equalsIgnoreCase(this.userName)){
            AuthOperationResult orderResponse = new AuthOperationResult(true);
            return orderResponse;
        }

        return new AuthOperationResult(false);
    }
}
