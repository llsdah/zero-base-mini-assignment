package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class LinkModel extends DateModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long linkId;

    private String url;
    private String title;
    private String content;
    private boolean useFlag;
    private Long memberId;


    public LinkModel(String url, String title, String contents, boolean useFlag, Long memberId) {
        this.url = url;
        this.title = title;
        this.content = contents;
        this.useFlag = useFlag;
        this.memberId = memberId;
    }

    public LinkModel() {

    }

    public Long getLinkId() {
        return linkId;
    }

    public boolean isUseFlag() {
        return useFlag;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
    }

    @Override
    public String toString() {
        return "LinkModel{" +
                "linkId=" + linkId +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", useFlag=" + useFlag +
                ", memberId=" + memberId +
                "} " + super.toString();
    }
}
