package org.example.netty.study.common.keepalive;

import lombok.Data;
import lombok.extern.java.Log;
import org.example.netty.study.common.Operation;

@Data
@Log
public class KeepaliveOperation extends Operation {

    private long time ;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    @Override
    public KeepaliveOperationResult execute() {
        KeepaliveOperationResult orderResponse = new KeepaliveOperationResult(time);
        return orderResponse;
    }
}
