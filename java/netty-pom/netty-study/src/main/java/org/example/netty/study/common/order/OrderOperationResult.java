package org.example.netty.study.common.order;

import lombok.Data;
import org.example.netty.study.common.OperationResult;

@Data
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;

}