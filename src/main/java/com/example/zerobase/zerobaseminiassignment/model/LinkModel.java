package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import java.time.LocalDateTime;

/**
 * 링크 모델
 */
@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // 링크에 대한 설정이 동시에 바뀔 가능성은 크다
public class LinkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long linkId;

    private String url;
    private String title;
    private String content;
    private boolean useFlag;
    private Long memberId;

    @Version
    private Long version; // 다중 서버에서의 동시성문제를 위한 버전 필드 추가

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

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

    public void updateUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
    }

    public Long getVersion() {
        return version;
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
                ", version=" + version +
                "} " + super.toString();
    }
}
