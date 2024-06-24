package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;

public class UpdateFileMetaIndexResponse extends CiServiceResult {

    /**
     *请求ID
     */
    private String requestId;

    /**
     *创建元数据索引的任务ID
     */
    private String eventId;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getEventId() { return eventId; }

    public void setEventId(String eventId) { this.eventId = eventId; }

    
}
