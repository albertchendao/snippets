package org.example.netty.study.common.auth;

import lombok.Data;
import org.example.netty.study.common.OperationResult;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
