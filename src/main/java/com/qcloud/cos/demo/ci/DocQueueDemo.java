package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.DocQueueRequest;
import com.qcloud.cos.model.ciModel.queue.DocQueueResponse;

import java.io.UnsupportedEncodingException;

/**
 * 文档预览队列相关demo https://cloud.tencent.com/document/product/460/46946
 */
public class DocQueueDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeDocProcessQueues(client);
    }

    /**
     * DescribeDocProcessQueues 接口用于查询文档预览队列。
     * @param client
     */
    public static void describeDocProcessQueues(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        DocQueueRequest request = new DocQueueRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        //3.调用接口,获取任务响应对象
        DocListQueueResponse response = client.describeDocProcessQueues(request);
        System.out.println(response);
    }

    /**
     * UpdateDocProcessQueue 接口用于更新文档预览队列
     * @param client
     */
    public static void updateDocProcessQueue(COSClient client)  {
        //1.创建任务请求对象
        DocQueueRequest request = new DocQueueRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        request.setQueueId("pc02270c617ae4b6d9b0a52cb1cfce6b3");
        request.getNotifyConfig().setUrl("cloud.tencent.com");
        request.setState("Active");
        request.setName("mark-1");
        //3.调用接口,获取任务响应对象
        boolean result = client.updateDocProcessQueue(request);
        System.out.println(result);
    }

}