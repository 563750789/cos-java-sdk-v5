package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


public class DescribeDatasetResponse extends CiServiceResult {

    /**
     *请求ID
     */
    private String requestId;

    /**
     *数据集信息
     */
    private Dataset dataset;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public Dataset getDataset() { return dataset; }

    public void setDataset(Dataset dataset) { this.dataset = dataset; }

    
}
