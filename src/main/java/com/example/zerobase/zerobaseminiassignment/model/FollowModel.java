package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // 맴버 변수에 대한 설정이 동시에 바뀔 가능성은 적다
@Table(indexes = @Index(name = "Follow_id", columnList = "followId"))
public class FollowModel extends RegistrationDateModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "followId", nullable = false)
    private Long followerId;

    @ManyToOne
    @JoinColumn(name = "follower_memberId")
    private MemberModel followerMemberId;


    @ManyToOne
    @JoinColumn(name = "following_memberId")
    private MemberModel followingMemberId;
}
