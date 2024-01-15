package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // 맴버 변수에 대한 설정이 동시에 바뀔 가능성은 적다
@Table(indexes = @Index(name = "post_hashTag_id", columnList = "postHashTagId"))
public class PostHashTagModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "postHashTagId", nullable = false)
    private Long postHashTagId;

    @ManyToOne
    @JoinColumn(name = "postId" , referencedColumnName = "postId") // 외래 키 설정
    private PostModel postId;


    @ManyToOne
    @JoinColumn(name = "hashTagId", referencedColumnName = "hashTagId")
    private HashTagModel hashTagId;
    private String visibleTagName; //대소문자 무구별

    public PostHashTagModel() {}

    public PostHashTagModel(PostModel postId, HashTagModel hashTagId, String visibleTagName) {
        this.postId = postId;
        this.hashTagId = hashTagId;
        this.visibleTagName = visibleTagName;
    }

    public Long getPostHashTagId() {
        return postHashTagId;
    }

    public PostModel getPostId() {
        return postId;
    }

    public HashTagModel getHashTagId() {
        return hashTagId;
    }

    public String getVisibleTagName() {
        return visibleTagName;
    }

    @Override
    public String toString() {
        return "PostHashTagModel{" +
                "postHashTagId=" + postHashTagId +
                ", postId=" + postId +
                ", hashTagId=" + hashTagId +
                ", visibleTagName='" + visibleTagName + '\'' +
                '}';
    }
}
