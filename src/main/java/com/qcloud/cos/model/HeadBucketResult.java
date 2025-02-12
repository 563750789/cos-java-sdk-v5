/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.

 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Result object for Head Bucket request.
 */
public class HeadBucketResult implements Serializable {

    private String bucketRegion;
    private boolean isMergeBucket;
    private boolean isMazBucket;

    /**
     * Returns the region where the bucket is located.
     */
    public String getBucketRegion() {
        return bucketRegion;
    }

    public void setBucketRegion(String bucketRegion) {
        this.bucketRegion = bucketRegion;
    }

    public HeadBucketResult withBucketRegion(String bucketRegion) {
        setBucketRegion(bucketRegion);
        return this;
    }

    public boolean isMergeBucket() {
        return this.isMergeBucket;
    }

    public boolean isMetaAccBucket() {
        return this.isMergeBucket;
    }

    public void setMergeBucket(boolean isMergeBucket) {
        this.isMergeBucket = isMergeBucket;
    }

    public boolean isMazBucket() {
        return isMazBucket;
    }

    public void setMazBucket(boolean mazBucket) {
        isMazBucket = mazBucket;
    }
}

