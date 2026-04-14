package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 分析结果容器，包含分析类型和对应的结果
 */
@XStreamAlias("AnalysisResult")
public class AnalysisResult {

    /**
     * 分析类型：ImageLabels
     */
    @XStreamAlias("Type")
    private String type;

    /**
     * 图片标签类型的分析结果
     */
    @XStreamAlias("ImageLabelsResult")
    private ImageLabelsResult imageLabelsResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ImageLabelsResult getImageLabelsResult() {
        return imageLabelsResult;
    }

    public void setImageLabelsResult(ImageLabelsResult imageLabelsResult) {
        this.imageLabelsResult = imageLabelsResult;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AnalysisResult{");
        sb.append("type='").append(type).append('\'');
        sb.append(", imageLabelsResult=").append(imageLabelsResult);
        sb.append('}');
        return sb.toString();
    }
}
