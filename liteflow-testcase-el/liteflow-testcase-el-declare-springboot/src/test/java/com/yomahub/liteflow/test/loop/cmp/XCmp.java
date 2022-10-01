package com.yomahub.liteflow.test.loop.cmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowForCmpDefine;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.core.NodeForComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;

@LiteflowComponent("x")
@LiteflowForCmpDefine
public class XCmp{

    @LiteflowMethod(LiteFlowMethodEnum.PROCESS_FOR)
    public int processFor(NodeComponent bindCmp) throws Exception {
        return 3;
    }
}
