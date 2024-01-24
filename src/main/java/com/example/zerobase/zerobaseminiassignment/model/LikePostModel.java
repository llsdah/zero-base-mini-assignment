package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // 맴버 변수에 대한 설정이 동시에 바뀔 가능성은 적다
@Table(indexes = @Index(name = "like_id", columnList = "likeId"))
public class LikePostModel extends RegistrationDateModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long LikePostId;

    @ManyToOne
    @JoinColumn(name = "likePost_likedPost") // 외래 키 설정
    private PostModel likedPost;

    @ManyToOne
    @JoinColumn(name = "likePost_likingMember") // 외래 키 설정
    private MemberModel likingMember;

    public LikePostModel() {
    }

    public LikePostModel(PostModel likedPost, MemberModel likingMember) {
        this.likedPost = likedPost;
        this.likingMember = likingMember;
    }

}
