package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // 맴버 변수에 대한 설정이 동시에 바뀔 가능성은 적다
@Table(indexes = @Index(name = "post_id", columnList = "postId"))
public class PostModel extends ModificationDateModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "postId", nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "memberModel_memberid") // 외래 키 설정
    private MemberModel memberId;

    @Setter
    private String title;
    @Setter
    private String contents;
    @Setter
    private String status; // 0:임시, 1:확설화, 2:비활설화, 3:차단

    public PostModel(){}

    public PostModel(MemberModel memberId, String title, String contents, String status) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.status = status;
    }

    public void setMemberId(MemberModel memberId) {this.memberId = memberId;}
    public Long getPostId() {return postId;}
    public MemberModel getMemberId() {return memberId;}
    public String getTitle() {return title;}
    public String getContents() {return contents;}
    public String getStatus() {return status;}

    @Override
    public String toString() {
        return "PostModel{" +
                "postId=" + postId +
                ", memberId=" + memberId +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}
