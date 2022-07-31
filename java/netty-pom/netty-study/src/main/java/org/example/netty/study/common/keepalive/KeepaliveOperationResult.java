package org.example.netty.study.common.keepalive;

import lombok.Data;
import org.example.netty.study.common.OperationResult;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
