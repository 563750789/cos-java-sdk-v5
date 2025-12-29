package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Request")
public class CreateAIObjectDetectJobRequest extends CosServiceRequest {

    private String bucketName;

    @XStreamOmitField
    private String objectKey;

    @XStreamOmitField
    private String ciProcess = "AIObjectDetect";

    @XStreamOmitField
    private String detectUrl;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getCiProcess() {
        return ciProcess;
    }

    public void setCiProcess(String ciProcess) {
        this.ciProcess = ciProcess;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    @Override public String toString() {
    return "CreateAIObjectDetectJobRequest{" +
            "bucketName='" + bucketName + '\'' +
            ", objectKey='" + objectKey + '\'' +
            ", ciProcess='" + ciProcess + '\'' +
            ", detectUrl='" + detectUrl + '\'' +
            '}';
}}
