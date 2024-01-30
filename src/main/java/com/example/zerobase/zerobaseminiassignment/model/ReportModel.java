package com.example.zerobase.zerobaseminiassignment.model;


import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import java.time.LocalDateTime;

/**
 * 신고
 */
@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(indexes = @Index(name = "report_id", columnList = "reportId"))
public class ReportModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reportId", nullable = false)
    private Long reportsId;

    @ManyToOne
    @JoinColumn(name = "reporter_memberId", nullable = false) // 외래 키 설정
    private MemberModel reporterMemberId;
    @ManyToOne
    @JoinColumn(name = "postId",nullable = true) // 외래 키 설정
    private PostModel postId;
    @ManyToOne
    @JoinColumn(name = "target_memberId",nullable = true) // 외래 키 설정
    private MemberModel targetMemberId;

    private String reason;

    @Getter
    @Setter
    private String answer;

    @Getter
    @Setter
    @Column(columnDefinition = "int default 0") // 0 미처리 , 0이외값 처리
    private int status;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public ReportModel() {}
    public ReportModel(MemberModel reporterMemberId, PostModel postId, MemberModel targetMemberId, String reason) {
        this.reporterMemberId = reporterMemberId;
        this.postId = postId;
        this.targetMemberId = targetMemberId;
        this.reason = reason;
    }

    public Long getReportsId() {
        return reportsId;
    }

    public MemberModel getReporterMemberId() {
        return reporterMemberId;
    }

    public PostModel getPostId() {
        return postId;
    }

    public MemberModel getTargetMemberId() {
        return targetMemberId;
    }

    public String getReason() {
        return reason;
    }

    public void setReporterMemberId(MemberModel reporterMemberId) {
        this.reporterMemberId = reporterMemberId;
    }

    @Override
    public String toString() {
        return "ReportModel{" +
                "reportsId=" + reportsId +
                ", ReporterMemberId=" + reporterMemberId +
                ", postId=" + postId +
                ", targetMemberId=" + targetMemberId +
                ", reason='" + reason + '\'' +
                "} " + super.toString();
    }
}
