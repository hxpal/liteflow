package com.yomahub.liteflow.test.zookeeper;

import cn.hutool.core.io.resource.ResourceUtil;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.entity.data.DefaultSlot;
import com.yomahub.liteflow.entity.data.LiteflowResponse;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.test.BaseTest;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.curator.test.TestingServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * 非spring环境下的zk配置源功能测试
 * ZK节点存储数据的格式为yml文件
 * @author zendwang
 * @since 2.5.0
 */
public class ZkNodeWithYmlTest extends BaseTest {
    
    private static final String ZK_NODE_PATH = "/lite-flow/flow";

    private static TestingServer zkServer;

    private static FlowExecutor flowExecutor;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        zkServer = new TestingServer(21810);
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            String data = ResourceUtil.readUtf8Str("zookeeper/flow.yml");
            ZkClient zkClient = new ZkClient("127.0.0.1:21810");
            zkClient.setZkSerializer(new ZkSerializer() {
                @Override
                public byte[] serialize(final Object o) throws ZkMarshallingError {
                    return o.toString().getBytes(Charset.forName("UTF-8"));
                }

                @Override
                public Object deserialize(final byte[] bytes) throws ZkMarshallingError {
                    return new String(bytes, Charset.forName("UTF-8"));
                }
            });
            zkClient.createPersistent(ZK_NODE_PATH, true);
            zkClient.writeData(ZK_NODE_PATH, data);
            zkClient.close();
            latch.countDown();
        }).start();
        latch.await();

        LiteflowConfig config = new LiteflowConfig();
        config.setRuleSource("yml:127.0.0.1:21810");
        flowExecutor = FlowExecutorHolder.loadInstance(config);
    }
    
    @Test
    public void testZkNodeWithYml() {
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain1", "arg");
        Assert.assertTrue(response.isSuccess());
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        zkServer.stop();
    }
}
