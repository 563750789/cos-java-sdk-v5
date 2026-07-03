package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;

import java.util.List;

/**
 * 多模态混合检索响应（V2.7.0 新增）。
 * <p>
 * 对应 MetaInsight 接口：POST /datasetquery/hybridsearch。
 * 根据请求中 {@code Templates} 参数返回不同结果集：
 * </p>
 * <ul>
 *   <li>{@code Templates=ImageSearch} 时返回 {@link #imageResult}（图像级结果）</li>
 *   <li>{@code Templates=DocSearch} 时返回 {@link #docResult}（文档级结果）</li>
 * </ul>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 * @see <a href="https://write.woa.com/document/200892020456558592">官方文档</a>
 */
public class DatasetHybridSearchResponse extends CiServiceResult {

    /**
     * 请求 ID。
     */
    private String requestId;

    /**
     * 图像检索结果列表（{@code Templates=ImageSearch} 时返回）。
     * <p>复用现有的 {@link ImageResult} 类（含 URI / Score 字段）。</p>
     */
    private List<ImageResult> imageResult;

    /**
     * 文档检索结果列表（{@code Templates=DocSearch} 时返回）。
     */
    private List<DocResult> docResult;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public List<ImageResult> getImageResult() { return imageResult; }

    public void setImageResult(List<ImageResult> imageResult) { this.imageResult = imageResult; }

    public List<DocResult> getDocResult() { return docResult; }

    public void setDocResult(List<DocResult> docResult) { this.docResult = docResult; }

}
