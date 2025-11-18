package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.BatchJobListResponse;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 批量任务测试类
 * 包含以下测试场景：
 * 1. 触发任务（独立节点）- createInventoryTriggerJob
 * 2. 触发任务（工作流）- createInventoryTriggerJobWithQueueType
 * 3. 查询单个任务 - describeInventoryTriggerJob
 * 4. 批量拉取任务 - describeInventoryTriggerJobs
 * 5. 取消任务 - cancelInventoryTriggerJob
 */
public class BatchJobTest extends AbstractCOSClientCITest {
    private String batchJobId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    /**
     * 在每个测试前创建一个批量任务，用于后续测试
     */
    @Before
    public void setUp() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("demo-batch-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("out/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            if (response != null && response.getJobDetail() != null) {
                batchJobId = response.getJobDetail().getJobId();
                System.out.println("Created batch job with ID: " + batchJobId);
            }
        } catch (Exception e) {
            System.err.println("Failed to create batch job in setUp: " + e.getMessage());
        }
    }

    /**
     * 测试：触发任务（独立节点）
     * 创建一个基本的批量触发任务
     */
    @Test
    public void testCreateInventoryTriggerJob() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("demo-basic-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.setUserData("test user data");
            operation.setJobLevel("0");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Create Inventory Trigger Job Response: " + response);

        } catch (Exception e) {
            System.err.println("testCreateInventoryTriggerJob failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：触发任务（工作流）- 使用倍速转码
     * 创建一个带有QueueType的批量触发任务
     */
    @Test
    public void testCreateInventoryTriggerJobWithQueueType() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("demo-speed-transcoding");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setTag("Transcode");
            // 设置QueueType为SpeedTranscoding，开启倍速转码
            operation.setQueueType("SpeedTranscoding");
            operation.setUserData("speed transcoding test");
            operation.setJobLevel("1");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/speed/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Create Job with QueueType Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job State: " + response.getJobDetail().getState());
                System.out.println("QueueType: SpeedTranscoding");
            }
        } catch (Exception e) {
            System.err.println("testCreateInventoryTriggerJobWithQueueType failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：查询单个任务
     * 根据任务ID查询批量任务的详细信息
     */
    @Test
    public void testDescribeInventoryTriggerJob() {
        try {
            if (batchJobId == null || batchJobId.isEmpty()) {
                System.out.println("No batch job ID available, skipping test");
                return;
            }
            
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setJobId(batchJobId);
            
            BatchJobResponse response = cosclient.describeInventoryTriggerJob(request);
            System.out.println("Describe Inventory Trigger Job Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job Name: " + response.getJobDetail().getName());
                System.out.println("Job State: " + response.getJobDetail().getState());
                System.out.println("Job Type: " + response.getJobDetail().getType());
                System.out.println("Creation Time: " + response.getJobDetail().getCreationTime());
            }
        } catch (Exception e) {
            System.err.println("testDescribeInventoryTriggerJob failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：批量拉取任务
     * 查询批量任务列表
     */
    @Test
    public void testDescribeInventoryTriggerJobs() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setOrderByTime("Desc");
            request.setSize(10);
            request.setStates("All");
            
            BatchJobListResponse response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println("Describe Inventory Trigger Jobs Response: " + response);
            
            if (response != null) {
                System.out.println("Total jobs: " + response.getJobsDetailList().size());
                System.out.println("NextToken: " + response.getNextToken());
                
                // 打印每个任务的基本信息
                response.getJobsDetailList().forEach(job -> {
                    System.out.println("  - Job ID: " + job.getJobId() + 
                                     ", Name: " + job.getName() + 
                                     ", State: " + job.getState());
                });
            }
        } catch (Exception e) {
            System.err.println("testDescribeInventoryTriggerJobs failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：批量拉取任务（带分页）
     * 测试使用nextToken进行分页查询
     */
    @Test
    public void testDescribeInventoryTriggerJobsWithPagination() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setOrderByTime("Desc");
            request.setSize(5);
            request.setStates("All");

            // 第一页
            BatchJobListResponse response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println(response);
            System.out.println("First page - Total jobs: " + response.getJobsDetailList().size());
            System.out.println("NextToken: " + response.getNextToken());
            
            // 如果有下一页，继续查询
            if (response.getNextToken() != null && !response.getNextToken().isEmpty()) {
                request.setNextToken(response.getNextToken());
                BatchJobListResponse nextPageResponse = cosclient.describeInventoryTriggerJobs(request);
                System.out.println("Second page - Total jobs: " + nextPageResponse.getJobsDetailList().size());
                System.out.println("NextToken: " + nextPageResponse.getNextToken());
            }
        } catch (Exception e) {
            System.err.println("testDescribeInventoryTriggerJobsWithPagination failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：取消任务
     * 取消一个正在运行的批量任务
     */
    @Test
    public void testCancelInventoryTriggerJob() {
        try {
            if (batchJobId == null || batchJobId.isEmpty()) {
                System.out.println("No batch job ID available, skipping test");
                return;
            }
            
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setJobId(batchJobId);
            
            Boolean response = cosclient.cancelInventoryTriggerJob(request);
            System.out.println("Cancel Inventory Trigger Job Result: " + response);

            if (response) {
                System.out.println("Successfully cancelled job with ID: " + batchJobId);
            }
        } catch (CosServiceException e) {
            // 如果任务已经完成或不存在，会抛出异常，这是正常的
            System.out.println("Cancel job failed (expected if job already completed): " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
        } catch (Exception e) {
            System.err.println("testCancelInventoryTriggerJob failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：按状态过滤任务
     * 测试查询特定状态的任务
     */
    @Test
    public void testDescribeInventoryTriggerJobsByState() {
        try {
            // 测试查询成功的任务
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setOrderByTime("Desc");
            request.setSize(10);
            request.setStates("Success");
            
            BatchJobListResponse response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println("Success jobs count: " + response.getJobsDetailList().size());
            
            // 测试查询失败的任务
            request.setStates("Failed");
            response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println("Failed jobs count: " + response.getJobsDetailList().size());
            
            // 测试查询运行中的任务
            request.setStates("Running");
            response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println(response);
            System.out.println("Running jobs count: " + response.getJobsDetailList().size());
        } catch (Exception e) {
            System.err.println("testDescribeInventoryTriggerJobsByState failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
