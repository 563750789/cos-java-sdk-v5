package com.qcloud.cos;

import com.qcloud.cos.model.ciModel.bucket.MediaBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MediaBucketTest extends AbstractCOSClientTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void describeMediaBucketsTest() {
        if (!judgeUserInfoValid()) {
            return;
        }
        MediaBucketRequest request = new MediaBucketRequest();
        request.setBucketName(bucket);
        MediaBucketResponse response = cosclient.describeMediaBuckets(request);
        if (response != null && response.getMediaBucketList().size() != 0) {
            assertNotEquals("0", response.getTotalCount());
            assertTrue(Integer.parseInt(response.getTotalCount()) > 0);
            assertTrue(Integer.parseInt(response.getPageSize()) > 0);
            assertTrue(Integer.parseInt(response.getPageNumber()) > 0);
            assertEquals(bucket, response.getMediaBucketList().get(0).getBucketId());
        }
    }

}
