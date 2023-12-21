package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 멤버 모델
 */
@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // 맴버 변수에 대한 설정이 동시에 바뀔 가능성은 적다
@Table(indexes = @Index(name = "idx_id", columnList = "memberId_title"))
public class MemberModel extends DateModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memberId_title", nullable = false)
    private Long memberId;

    private String name;
    private String phoneNumber;
    private String email;
    private String authority;

    public MemberModel(String name, String phoneNumber, String email, String authority, Long memberId) {
        this.memberId = memberId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.authority = authority;
    }

    public MemberModel() {

    }

    public void updateAuthority(String authority) {
        this.authority = authority;
    }


    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthority() {
        return authority;
    }


    @Override
    public String toString() {
        return "MemberModel{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", authority='" + authority + '\'' +
                "} " + super.toString();
    }
}
