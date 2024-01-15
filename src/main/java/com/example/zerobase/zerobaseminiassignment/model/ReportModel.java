package com.example.zerobase.zerobaseminiassignment.model;


import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 신고
 */
@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(indexes = @Index(name = "report_id", columnList = "reportId"))
public class ReportModel extends ModificationDateModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reportId", nullable = false)
    private Long reportsId;

    @ManyToOne
    @JoinColumn(name = "reporter_memberId", nullable = false) // 외래 키 설정
    private MemberModel ReporterMemberId;
    @ManyToOne
    @JoinColumn(name = "postId",nullable = true) // 외래 키 설정
    private PostModel postId;
    @ManyToOne
    @JoinColumn(name = "target_memberId",nullable = true) // 외래 키 설정
    private MemberModel targetMemberId;

    private String reason;

    public ReportModel() {}
    public ReportModel(MemberModel reporterMemberId, PostModel postId, MemberModel targetMemberId, String reason) {
        ReporterMemberId = reporterMemberId;
        this.postId = postId;
        this.targetMemberId = targetMemberId;
        this.reason = reason;
    }

    public Long getReportsId() {
        return reportsId;
    }

    public MemberModel getReporterMemberId() {
        return ReporterMemberId;
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
        ReporterMemberId = reporterMemberId;
    }

    @Override
    public String toString() {
        return "ReportModel{" +
                "reportsId=" + reportsId +
                ", ReporterMemberId=" + ReporterMemberId +
                ", postId=" + postId +
                ", targetMemberId=" + targetMemberId +
                ", reason='" + reason + '\'' +
                "} " + super.toString();
    }
}
