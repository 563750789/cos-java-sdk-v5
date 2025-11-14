package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.ai.CreateAIObjectDetectJobRequest;
import com.qcloud.cos.model.ciModel.ai.CreateAIObjectDetectJobResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * 图像主体检测demo https://cloud.tencent.com/document/product/460/106462
 * 图像主体检测功能为同步请求方式，您可以通过本接口检测图片中的主体，识别出主体的名称、位置、置信度等信息。该接口属于 GET 请求。
 */
public class CreateAIObjectDetectJobDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        detectObjectWithCOSObject(client);
        detectObjectWithExternalUrl(client);
    }

    /**
     * 使用COS中的图片进行主体检测
     * 图片支持格式：PNG、JPG、JPEG。
     * 图片大小：所下载图片经 Base64 编码后不超过4MB。
     * 图片像素：建议大于50*50像素，否则影响识别效果。
     * ObjectKey 对象位置或DetectUrl 图片url 需二选一
     */
    public static void detectObjectWithCOSObject(COSClient client) {
        //1.创建任务请求对象
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置bucket中的图片位置
        request.setObjectKey("test-image.jpg");
        
        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("AI Object Detect with COS Object Response: " + Jackson.toJsonString(response));
    }

    /**
     * 使用外部URL进行主体检测
     */
    public static void detectObjectWithExternalUrl(COSClient client) {
        //1.创建任务请求对象
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置图片url（SDK会自动进行URL编码，不需要手动编码）
        String externalUrl = "https://demo-1234567890.cos.ap-chongqing.myqcloud.com/test-image.jpg";
        request.setDetectUrl(externalUrl);
        
        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("AI Object Detect with External URL Response: " + Jackson.toJsonString(response));
    }
}
