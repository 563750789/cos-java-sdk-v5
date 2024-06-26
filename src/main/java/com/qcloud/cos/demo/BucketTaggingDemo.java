package com.qcloud.cos.demo;

import java.util.LinkedList;
import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketTaggingConfiguration;
import com.qcloud.cos.model.SetBucketTaggingConfigurationRequest;
import com.qcloud.cos.model.TagSet;
import com.qcloud.cos.region.Region;

public class BucketTaggingDemo {
    public static void main(String[] args) {
        setGetDeleteBucketTagging();
    }

    private static void setGetDeleteBucketTagging() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-12500000000";
        List<TagSet> tagSetList = new LinkedList<TagSet>();
        TagSet tagSet = new TagSet();
        tagSet.setTag("age", "18");
        tagSet.setTag("name", "xiaoming");
        tagSetList.add(tagSet);
        BucketTaggingConfiguration bucketTaggingConfiguration = new BucketTaggingConfiguration();
        bucketTaggingConfiguration.setTagSets(tagSetList);
        SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest =
                new SetBucketTaggingConfigurationRequest(bucketName, bucketTaggingConfiguration);
        cosclient.setBucketTaggingConfiguration(setBucketTaggingConfigurationRequest);
        BucketTaggingConfiguration bucketTaggingConfiguration1 = cosclient.getBucketTaggingConfiguration(bucketName);
        cosclient.deleteBucketTaggingConfiguration(bucketName);
    }
}
