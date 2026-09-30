package com.qcloud.cos.ci;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.qcloud.cos.internal.cihandler.CICommonJsonResponseHandler;
import com.qcloud.cos.model.ciModel.metaInsight.GetAIMediaInfoRequest;
import com.qcloud.cos.model.ciModel.metaInsight.GetAIMediaInfoResponse;
import com.qcloud.cos.utils.CIJackson;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GetAIMediaInfoTest {

    // 文档「响应体」一节示例（视频）
    private static final String RESPONSE_BODY_EXAMPLE =
            "{\n" +
            "\t\"RequestId\": \"2FDE2411-DB8D-4A9A-875B-275798F14A5E\",\n" +
            "\t\"MediaInfo\": {\n" +
            "\t\t\"URI\": \"cos://examplebucket-1250000000/test.mp4\",\n" +
            "\t\t\"CosTagging\": {\n" +
            "\t\t\t\"business\": \"userDataTest\",\n" +
            "\t\t\t\"type\": \"media\",\n" +
            "\t\t\t\"channel\": \"test\"\n" +
            "\t\t},\n" +
            "\t\t\"CustomLabels\": {\n" +
            "\t\t\t\"scene\": \"outdoor\",\n" +
            "\t\t\t\"project\": \"demo\"\n" +
            "\t\t},\n" +
            "\t\t\"CosUserMeta\": {\n" +
            "\t\t\t\"x-cos-meta-source\": \"upload\",\n" +
            "\t\t\t\"x-cos-meta-author\": \"test-user\"\n" +
            "\t\t},\n" +
            "\t\t\"ModifiedTime\": \"2020-12-26T04:11:10Z\",\n" +
            "\t\t\"FileInfo\": {\n" +
            "\t\t\t\"FileBasicInfo\": {\n" +
            "\t\t\t\t\"FileName\": \"example.mp4\",\n" +
            "\t\t\t\t\"FileSize\": 30611502,\n" +
            "\t\t\t\t\"FileUrl\": \"cos://examplebucket-1250000000/test.mp4\",\n" +
            "\t\t\t\t\"Region\": \"ap-shanghai\",\n" +
            "\t\t\t\t\"ModifiedTime\": \"2020-12-26T04:11:10Z\",\n" +
            "\t\t\t\t\"FormatName\": \"mov,mp4,m4a,3gp,3g2,mj2\",\n" +
            "\t\t\t\t\"Duration\": 216.206667,\n" +
            "\t\t\t\t\"Bitrate\": 1132.68,\n" +
            "\t\t\t\t\"Width\": 960,\n" +
            "\t\t\t\t\"Height\": 540\n" +
            "\t\t\t},\n" +
            "\t\t\t\"AiData\": {\n" +
            "\t\t\t\t\"AiLabelInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"From\": 1.4,\n" +
            "\t\t\t\t\t\t\"To\": 2.5,\n" +
            "\t\t\t\t\t\t\"Timestamp\": 1.4,\n" +
            "\t\t\t\t\t\t\"LabelDetail\": [\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Confidence\": \"high\",\n" +
            "\t\t\t\t\t\t\t\t\"LabelInfos\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"Type\": \"物体\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Category\": \"交通工具\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Name\": \"车\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Confidence\": \"medium\",\n" +
            "\t\t\t\t\t\t\t\t\"LabelInfos\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"Type\": \"物体\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Category\": \"建筑\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Name\": \"写字楼\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"AsrInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"From\": 1.4,\n" +
            "\t\t\t\t\t\t\"To\": 2.5,\n" +
            "\t\t\t\t\t\t\"ClipId\": \"5FE19530C7A422197535FE74F5DB****\",\n" +
            "\t\t\t\t\t\t\"Content\": \"欢迎观看本期节目\",\n" +
            "\t\t\t\t\t\t\"Timestamp\": 1.4\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"OcrInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"From\": 1.4,\n" +
            "\t\t\t\t\t\t\"To\": 2.5,\n" +
            "\t\t\t\t\t\t\"ClipId\": \"5FE19530C7A422197535FE74F5DB****\",\n" +
            "\t\t\t\t\t\t\"Content\": \"城市交通\",\n" +
            "\t\t\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\t\t\"Left\": 517,\n" +
            "\t\t\t\t\t\t\t\"Top\": 409,\n" +
            "\t\t\t\t\t\t\t\"Width\": 128,\n" +
            "\t\t\t\t\t\t\t\"Height\": 168\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"Score\": 0.86,\n" +
            "\t\t\t\t\t\t\"Timestamp\": 1.4\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"AiRoughData\": {\n" +
            "\t\t\t\t\t\"AiCategory\": \"生活\",\n" +
            "\t\t\t\t\t\"Description\": \"这是一段关于城市道路交通场景的视频\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"FaceInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"Score\": 99.04,\n" +
            "\t\t\t\t\t\t\"LabelName\": \"张三\",\n" +
            "\t\t\t\t\t\t\"Category\": \"custom\",\n" +
            "\t\t\t\t\t\t\"OccurrencesInfos\": [\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"From\": 61.066353,\n" +
            "\t\t\t\t\t\t\t\t\"To\": 69.06635,\n" +
            "\t\t\t\t\t\t\t\t\"TrackData\": [\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Timestamp\": 62.03302,\n" +
            "\t\t\t\t\t\t\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\"Left\": 517,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\"Top\": 409,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\"Width\": 128,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\"Height\": 168\n" +
            "\t\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t]\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    // 案例一：获取视频的 AI 分析信息
    private static final String CASE_VIDEO =
            "{\n" +
            "\t\"RequestId\": \"2FDE2411-DB8D-4A9A-875B-275798F14A5E\",\n" +
            "\t\"MediaInfo\": {\n" +
            "\t\t\"URI\": \"cos://examplebucket-1250000000/test.mp4\",\n" +
            "\t\t\"CosTagging\": {\n" +
            "\t\t\t\"business\": \"userDataTest\",\n" +
            "\t\t\t\"type\": \"media\",\n" +
            "\t\t\t\"channel\": \"test\"\n" +
            "\t\t},\n" +
            "\t\t\"CustomLabels\": {\n" +
            "\t\t\t\"scene\": \"outdoor\",\n" +
            "\t\t\t\"project\": \"demo\"\n" +
            "\t\t},\n" +
            "\t\t\"CosUserMeta\": {\n" +
            "\t\t\t\"x-cos-meta-source\": \"upload\",\n" +
            "\t\t\t\"x-cos-meta-author\": \"test-user\"\n" +
            "\t\t},\n" +
            "\t\t\"ModifiedTime\": \"2020-12-26T04:11:10Z\",\n" +
            "\t\t\"FileInfo\": {\n" +
            "\t\t\t\"FileBasicInfo\": {\n" +
            "\t\t\t\t\"FileName\": \"example.mp4\",\n" +
            "\t\t\t\t\"FileSize\": 30611502,\n" +
            "\t\t\t\t\"FileUrl\": \"cos://examplebucket-1250000000/test.mp4\",\n" +
            "\t\t\t\t\"Region\": \"ap-shanghai\",\n" +
            "\t\t\t\t\"ModifiedTime\": \"2020-12-26T04:11:10Z\",\n" +
            "\t\t\t\t\"FormatName\": \"mov,mp4,m4a,3gp,3g2,mj2\",\n" +
            "\t\t\t\t\"Duration\": 216.206667,\n" +
            "\t\t\t\t\"Bitrate\": 1132.68,\n" +
            "\t\t\t\t\"Width\": 960,\n" +
            "\t\t\t\t\"Height\": 540\n" +
            "\t\t\t},\n" +
            "\t\t\t\"AiData\": {\n" +
            "\t\t\t\t\"AiLabelInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"From\": 1.4,\n" +
            "\t\t\t\t\t\t\"To\": 2.5,\n" +
            "\t\t\t\t\t\t\"Timestamp\": 1.4,\n" +
            "\t\t\t\t\t\t\"LabelDetail\": [\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Confidence\": \"high\",\n" +
            "\t\t\t\t\t\t\t\t\"LabelInfos\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"Type\": \"物体\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Category\": \"交通工具\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Name\": \"车\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Confidence\": \"medium\",\n" +
            "\t\t\t\t\t\t\t\t\"LabelInfos\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"Type\": \"物体\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Category\": \"建筑\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Name\": \"写字楼\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"AsrInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"From\": 1.4,\n" +
            "\t\t\t\t\t\t\"To\": 2.5,\n" +
            "\t\t\t\t\t\t\"ClipId\": \"5FE19530C7A422197535FE74F5DB****\",\n" +
            "\t\t\t\t\t\t\"Content\": \"欢迎观看本期节目\",\n" +
            "\t\t\t\t\t\t\"Timestamp\": 1.4\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"OcrInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"From\": 1.4,\n" +
            "\t\t\t\t\t\t\"To\": 2.5,\n" +
            "\t\t\t\t\t\t\"ClipId\": \"5FE19530C7A422197535FE74F5DB****\",\n" +
            "\t\t\t\t\t\t\"Content\": \"城市交通\",\n" +
            "\t\t\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\t\t\"Left\": 517,\n" +
            "\t\t\t\t\t\t\t\"Top\": 409,\n" +
            "\t\t\t\t\t\t\t\"Width\": 128,\n" +
            "\t\t\t\t\t\t\t\"Height\": 168\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"Score\": 0.86,\n" +
            "\t\t\t\t\t\t\"Timestamp\": 1.4\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"AiRoughData\": {\n" +
            "\t\t\t\t\t\"AiCategory\": \"生活\",\n" +
            "\t\t\t\t\t\"Description\": \"这是一段关于城市道路交通场景的视频\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"FaceInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"Score\": 99.04,\n" +
            "\t\t\t\t\t\t\"LabelName\": \"张三\",\n" +
            "\t\t\t\t\t\t\"Category\": \"custom\",\n" +
            "\t\t\t\t\t\t\"OccurrencesInfos\": [\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"From\": 61.066353,\n" +
            "\t\t\t\t\t\t\t\t\"To\": 69.06635,\n" +
            "\t\t\t\t\t\t\t\t\"TrackData\": [\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Timestamp\": 62.03302,\n" +
            "\t\t\t\t\t\t\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\"Left\": 517,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\"Top\": 409,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\"Width\": 128,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\"Height\": 168\n" +
            "\t\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t]\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    // 案例二：获取图片的 AI 分析信息
    private static final String CASE_IMAGE =
            "{\n" +
            "\t\"RequestId\": \"2FDE2411-DB8D-4A9A-875B-275798F14A5E\",\n" +
            "\t\"MediaInfo\": {\n" +
            "\t\t\"URI\": \"cos://examplebucket-1250000000/test.png\",\n" +
            "\t\t\"CosTagging\": {\n" +
            "\t\t\t\"business\": \"userDataTest\",\n" +
            "\t\t\t\"type\": \"media\",\n" +
            "\t\t\t\"channel\": \"test\"\n" +
            "\t\t},\n" +
            "\t\t\"CustomLabels\": {\n" +
            "\t\t\t\"scene\": \"outdoor\",\n" +
            "\t\t\t\"project\": \"demo\"\n" +
            "\t\t},\n" +
            "\t\t\"CosUserMeta\": {\n" +
            "\t\t\t\"x-cos-meta-source\": \"upload\",\n" +
            "\t\t\t\"x-cos-meta-author\": \"test-user\"\n" +
            "\t\t},\n" +
            "\t\t\"ModifiedTime\": \"2020-12-26T04:11:10Z\",\n" +
            "\t\t\"FileInfo\": {\n" +
            "\t\t\t\"FileBasicInfo\": {\n" +
            "\t\t\t\t\"FileName\": \"test.png\",\n" +
            "\t\t\t\t\"FileSize\": 30611502,\n" +
            "\t\t\t\t\"FileUrl\": \"cos://examplebucket-1250000000/test.png\",\n" +
            "\t\t\t\t\"Region\": \"ap-shanghai\",\n" +
            "\t\t\t\t\"ModifiedTime\": \"2020-12-26T04:11:10Z\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"AiData\": {\n" +
            "\t\t\t\t\"AiLabelInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"LabelDetail\": [\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Confidence\": \"high\",\n" +
            "\t\t\t\t\t\t\t\t\"LabelInfos\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"Type\": \"物体\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Category\": \"交通工具\",\n" +
            "\t\t\t\t\t\t\t\t\t\"Name\": \"车\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"OcrInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"Content\": \"营业执照\",\n" +
            "\t\t\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\t\t\"Left\": 100,\n" +
            "\t\t\t\t\t\t\t\"Top\": 200,\n" +
            "\t\t\t\t\t\t\t\"Width\": 300,\n" +
            "\t\t\t\t\t\t\t\"Height\": 50\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"Score\": 0.95\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t],\n" +
            "\t\t\t\t\"FaceInfo\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"Score\": 99.04,\n" +
            "\t\t\t\t\t\t\"LabelName\": \"张三\",\n" +
            "\t\t\t\t\t\t\"Category\": \"custom\",\n" +
            "\t\t\t\t\t\t\"TrackData\": [\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"Left\": 517,\n" +
            "\t\t\t\t\t\t\t\t\t\"Top\": 409,\n" +
            "\t\t\t\t\t\t\t\t\t\"Width\": 128,\n" +
            "\t\t\t\t\t\t\t\t\t\"Height\": 168\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t]\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    private static GetAIMediaInfoResponse unmarshall(String json) throws Exception {
        return new CICommonJsonResponseHandler<GetAIMediaInfoResponse>()
                .getResponse(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)),
                        GetAIMediaInfoResponse.class);
    }

    private static void assertVideoResponse(GetAIMediaInfoResponse response) {
        assertEquals("2FDE2411-DB8D-4A9A-875B-275798F14A5E", response.getRequestId());

        GetAIMediaInfoResponse.MediaInfo mediaInfo = response.getMediaInfo();
        assertNotNull(mediaInfo);
        assertEquals("cos://examplebucket-1250000000/test.mp4", mediaInfo.getURI());
        assertEquals(3, mediaInfo.getCosTagging().size());
        assertEquals("userDataTest", mediaInfo.getCosTagging().get("business"));
        assertEquals("media", mediaInfo.getCosTagging().get("type"));
        assertEquals("test", mediaInfo.getCosTagging().get("channel"));
        assertEquals(2, mediaInfo.getCustomLabels().size());
        assertEquals("outdoor", mediaInfo.getCustomLabels().get("scene"));
        assertEquals("demo", mediaInfo.getCustomLabels().get("project"));
        assertEquals(2, mediaInfo.getCosUserMeta().size());
        assertEquals("upload", mediaInfo.getCosUserMeta().get("x-cos-meta-source"));
        assertEquals("test-user", mediaInfo.getCosUserMeta().get("x-cos-meta-author"));
        assertEquals("2020-12-26T04:11:10Z", mediaInfo.getModifiedTime());

        GetAIMediaInfoResponse.FileBasicInfo basicInfo = mediaInfo.getFileInfo().getFileBasicInfo();
        assertNotNull(basicInfo);
        assertEquals("example.mp4", basicInfo.getFileName());
        assertEquals(Long.valueOf(30611502L), basicInfo.getFileSize());
        assertEquals("cos://examplebucket-1250000000/test.mp4", basicInfo.getFileUrl());
        assertEquals("ap-shanghai", basicInfo.getRegion());
        assertEquals("2020-12-26T04:11:10Z", basicInfo.getModifiedTime());
        assertEquals("mov,mp4,m4a,3gp,3g2,mj2", basicInfo.getFormatName());
        assertEquals(216.206667, basicInfo.getDuration(), 0.000001);
        assertEquals(1132.68, basicInfo.getBitrate(), 0.0001);
        assertEquals(Integer.valueOf(960), basicInfo.getWidth());
        assertEquals(Integer.valueOf(540), basicInfo.getHeight());

        GetAIMediaInfoResponse.AiData aiData = mediaInfo.getFileInfo().getAiData();
        assertNotNull(aiData);

        assertEquals(1, aiData.getAiLabelInfo().size());
        GetAIMediaInfoResponse.AiLabelInfo labelInfo = aiData.getAiLabelInfo().get(0);
        assertEquals(1.4, labelInfo.getFrom(), 0.0001);
        assertEquals(2.5, labelInfo.getTo(), 0.0001);
        assertEquals(1.4, labelInfo.getTimestamp(), 0.0001);
        assertEquals(2, labelInfo.getLabelDetail().size());
        assertEquals("high", labelInfo.getLabelDetail().get(0).getConfidence());
        assertEquals("物体", labelInfo.getLabelDetail().get(0).getLabelInfos().get("Type"));
        assertEquals("交通工具", labelInfo.getLabelDetail().get(0).getLabelInfos().get("Category"));
        assertEquals("车", labelInfo.getLabelDetail().get(0).getLabelInfos().get("Name"));
        assertEquals("medium", labelInfo.getLabelDetail().get(1).getConfidence());
        assertEquals("写字楼", labelInfo.getLabelDetail().get(1).getLabelInfos().get("Name"));

        assertEquals(1, aiData.getAsrInfo().size());
        GetAIMediaInfoResponse.AsrInfo asrInfo = aiData.getAsrInfo().get(0);
        assertEquals(1.4, asrInfo.getFrom(), 0.0001);
        assertEquals(2.5, asrInfo.getTo(), 0.0001);
        assertEquals("5FE19530C7A422197535FE74F5DB****", asrInfo.getClipId());
        assertEquals("欢迎观看本期节目", asrInfo.getContent());
        assertEquals(1.4, asrInfo.getTimestamp(), 0.0001);

        assertEquals(1, aiData.getOcrInfo().size());
        GetAIMediaInfoResponse.OcrInfo ocrInfo = aiData.getOcrInfo().get(0);
        assertEquals(1.4, ocrInfo.getFrom(), 0.0001);
        assertEquals(2.5, ocrInfo.getTo(), 0.0001);
        assertEquals("5FE19530C7A422197535FE74F5DB****", ocrInfo.getClipId());
        assertEquals("城市交通", ocrInfo.getContent());
        assertEquals(0.86, ocrInfo.getScore(), 0.0001);
        assertEquals(1.4, ocrInfo.getTimestamp(), 0.0001);
        assertEquals(Integer.valueOf(517), ocrInfo.getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(409), ocrInfo.getBoxPosition().getTop());
        assertEquals(Integer.valueOf(128), ocrInfo.getBoxPosition().getWidth());
        assertEquals(Integer.valueOf(168), ocrInfo.getBoxPosition().getHeight());

        assertNotNull(aiData.getAiRoughData());
        assertEquals("生活", aiData.getAiRoughData().getAiCategory());
        assertEquals("这是一段关于城市道路交通场景的视频", aiData.getAiRoughData().getDescription());

        assertEquals(1, aiData.getFaceInfo().size());
        GetAIMediaInfoResponse.FaceInfo faceInfo = aiData.getFaceInfo().get(0);
        assertEquals(99.04, faceInfo.getScore(), 0.0001);
        assertEquals("张三", faceInfo.getLabelName());
        assertEquals("custom", faceInfo.getCategory());
        assertEquals(1, faceInfo.getOccurrencesInfos().size());
        GetAIMediaInfoResponse.OccurrencesInfo occurrencesInfo = faceInfo.getOccurrencesInfos().get(0);
        assertEquals(61.066353, occurrencesInfo.getFrom(), 0.000001);
        assertEquals(69.06635, occurrencesInfo.getTo(), 0.000001);
        assertEquals(1, occurrencesInfo.getTrackData().size());
        assertEquals(62.03302, occurrencesInfo.getTrackData().get(0).getTimestamp(), 0.000001);
        assertEquals(Integer.valueOf(517), occurrencesInfo.getTrackData().get(0).getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(409), occurrencesInfo.getTrackData().get(0).getBoxPosition().getTop());
        assertEquals(Integer.valueOf(128), occurrencesInfo.getTrackData().get(0).getBoxPosition().getWidth());
        assertEquals(Integer.valueOf(168), occurrencesInfo.getTrackData().get(0).getBoxPosition().getHeight());
    }

    @Test
    public void responseBodyExampleTest() throws Exception {
        assertVideoResponse(unmarshall(RESPONSE_BODY_EXAMPLE));
    }

    @Test
    public void caseVideoTest() throws Exception {
        assertVideoResponse(unmarshall(CASE_VIDEO));
    }

    @Test
    public void caseImageTest() throws Exception {
        GetAIMediaInfoResponse response = unmarshall(CASE_IMAGE);
        assertEquals("2FDE2411-DB8D-4A9A-875B-275798F14A5E", response.getRequestId());

        GetAIMediaInfoResponse.MediaInfo mediaInfo = response.getMediaInfo();
        assertNotNull(mediaInfo);
        assertEquals("cos://examplebucket-1250000000/test.png", mediaInfo.getURI());
        assertEquals("userDataTest", mediaInfo.getCosTagging().get("business"));
        assertEquals("outdoor", mediaInfo.getCustomLabels().get("scene"));
        assertEquals("upload", mediaInfo.getCosUserMeta().get("x-cos-meta-source"));
        assertEquals("2020-12-26T04:11:10Z", mediaInfo.getModifiedTime());

        GetAIMediaInfoResponse.FileBasicInfo basicInfo = mediaInfo.getFileInfo().getFileBasicInfo();
        assertNotNull(basicInfo);
        assertEquals("test.png", basicInfo.getFileName());
        assertEquals(Long.valueOf(30611502L), basicInfo.getFileSize());
        assertEquals("cos://examplebucket-1250000000/test.png", basicInfo.getFileUrl());
        assertEquals("ap-shanghai", basicInfo.getRegion());
        assertEquals("2020-12-26T04:11:10Z", basicInfo.getModifiedTime());
        assertNull(basicInfo.getFormatName());
        assertNull(basicInfo.getDuration());
        assertNull(basicInfo.getBitrate());
        assertNull(basicInfo.getWidth());
        assertNull(basicInfo.getHeight());

        GetAIMediaInfoResponse.AiData aiData = mediaInfo.getFileInfo().getAiData();
        assertNotNull(aiData);
        assertNull(aiData.getAsrInfo());
        assertNull(aiData.getAiRoughData());

        assertEquals(1, aiData.getAiLabelInfo().size());
        GetAIMediaInfoResponse.AiLabelInfo labelInfo = aiData.getAiLabelInfo().get(0);
        assertNull(labelInfo.getFrom());
        assertNull(labelInfo.getTo());
        assertNull(labelInfo.getTimestamp());
        assertEquals(1, labelInfo.getLabelDetail().size());
        assertEquals("high", labelInfo.getLabelDetail().get(0).getConfidence());
        assertEquals("物体", labelInfo.getLabelDetail().get(0).getLabelInfos().get("Type"));
        assertEquals("交通工具", labelInfo.getLabelDetail().get(0).getLabelInfos().get("Category"));
        assertEquals("车", labelInfo.getLabelDetail().get(0).getLabelInfos().get("Name"));

        assertEquals(1, aiData.getOcrInfo().size());
        GetAIMediaInfoResponse.OcrInfo ocrInfo = aiData.getOcrInfo().get(0);
        assertNull(ocrInfo.getFrom());
        assertNull(ocrInfo.getTo());
        assertNull(ocrInfo.getClipId());
        assertNull(ocrInfo.getTimestamp());
        assertEquals("营业执照", ocrInfo.getContent());
        assertEquals(0.95, ocrInfo.getScore(), 0.0001);
        assertEquals(Integer.valueOf(100), ocrInfo.getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(200), ocrInfo.getBoxPosition().getTop());
        assertEquals(Integer.valueOf(300), ocrInfo.getBoxPosition().getWidth());
        assertEquals(Integer.valueOf(50), ocrInfo.getBoxPosition().getHeight());

        assertEquals(1, aiData.getFaceInfo().size());
        GetAIMediaInfoResponse.FaceInfo faceInfo = aiData.getFaceInfo().get(0);
        assertEquals(99.04, faceInfo.getScore(), 0.0001);
        assertEquals("张三", faceInfo.getLabelName());
        assertEquals("custom", faceInfo.getCategory());
        assertNull(faceInfo.getOccurrencesInfos());
        assertEquals(1, faceInfo.getTrackData().size());
        assertNull(faceInfo.getTrackData().get(0).getTimestamp());
        assertEquals(Integer.valueOf(517), faceInfo.getTrackData().get(0).getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(409), faceInfo.getTrackData().get(0).getBoxPosition().getTop());
        assertEquals(Integer.valueOf(128), faceInfo.getTrackData().get(0).getBoxPosition().getWidth());
        assertEquals(Integer.valueOf(168), faceInfo.getTrackData().get(0).getBoxPosition().getHeight());
    }

    @Test
    public void responseFieldsFullCoverageTest() throws Exception {
        // 严格模式反序列化：文档 JSON 中任何未被模型声明覆盖的字段都会抛 UnrecognizedPropertyException
        ObjectMapper strictMapper = new ObjectMapper();
        strictMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        strictMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        strictMapper.readValue(RESPONSE_BODY_EXAMPLE, GetAIMediaInfoResponse.class);
        strictMapper.readValue(CASE_VIDEO, GetAIMediaInfoResponse.class);
        strictMapper.readValue(CASE_IMAGE, GetAIMediaInfoResponse.class);
    }

    @Test
    public void requestSerializeTest() throws Exception {
        // 文档案例一请求体示例，序列化结果须与其逐字段全等（不多不少）
        String docRequestBody =
                "{\n" +
                "\t\"DatasetName\": \"your-dataset-name-001\",\n" +
                "\t\"URI\": \"cos://examplebucket-1250000000/test.mp4\"\n" +
                "}";
        GetAIMediaInfoRequest request = new GetAIMediaInfoRequest();
        request.setDatasetName("your-dataset-name-001");
        request.setURI("cos://examplebucket-1250000000/test.mp4");

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(docRequestBody), mapper.readTree(CIJackson.toJsonString(request)));
    }
}
