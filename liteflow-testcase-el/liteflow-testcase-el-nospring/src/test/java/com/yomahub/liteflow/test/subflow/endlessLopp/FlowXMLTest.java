package com.yomahub.liteflow.test.subflow.endlessLopp;

import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.exception.CyclicDependencyException;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.property.LiteflowConfigGetter;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 测试 xml 文件情况下 chain 死循环逻辑
 *
 * @author luo yi
 * @since 2.11.1
 */
public class FlowXMLTest extends BaseTest {

	// 测试 chain 死循环
	@Test
	public void testChainEndlessLoop() {
		Assertions.assertThrows(CyclicDependencyException.class, () -> {
			LiteflowConfig config = LiteflowConfigGetter.get();
			config.setRuleSource("subflow/endlessLoop/flow.el.xml");
			FlowExecutorHolder.loadInstance(config);
		});
	}

}
