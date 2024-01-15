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
@Table(indexes = @Index(name = "member_id", columnList = "memberId"))
public class MemberModel extends ModificationDateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memberId", nullable = false)
    private Long memberId;

    private String name;
    private String phoneNumber;
    private String email;
    private String authority;
    private int status; // 0 : 임시 사용자 , 1 : 활성화 , 2 : 비활성화, 3 : 차단

    public MemberModel() {}
    public MemberModel(String name, String phoneNumber, String email, String authority, Long memberId, int status) {
        this.memberId = memberId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.authority = authority;
        this.status = status;
    }

    public void updateAuthority(String authority) {
        this.authority = authority;
    }
    public void updateStatus(int status) { this.status = status; }

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
    public int getStatus() {return status; }

    @Override
    public String toString() {
        return "MemberModel{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", authority='" + authority + '\'' +
                ", status='" + status + '\'' +
                "} " + super.toString();
    }
}
