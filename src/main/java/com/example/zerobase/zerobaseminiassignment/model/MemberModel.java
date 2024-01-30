package com.example.zerobase.zerobaseminiassignment.model;

import com.example.zerobase.zerobaseminiassignment.common.MyAuthorityUtil;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * 멤버 모델
 */
@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // 맴버 변수에 대한 설정이 동시에 바뀔 가능성은 적다
@Table(indexes = @Index(name = "member_id", columnList = "memberId"))
@Getter
public class MemberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memberId", nullable = false)
    private Long memberId;

    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    private String authority;

    @Column(columnDefinition = "int default 0")
    private String status; // 0 : 임시 사용자 , 1 : 활성화 , 2 : 비활성화, 3 : 차단

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public MemberModel() {

    }
    public MemberModel(String username, String password, String phoneNumber, String email, String authority, Long memberId, String status) {
        this.memberId = memberId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;

        if(authority.equals("MANAGER")){
            this.authority = MyAuthorityUtil.MANAGER;
        }else if (authority.equals("USER")){
            this.authority = MyAuthorityUtil.USER;
        }else {
            this.authority = MyAuthorityUtil.TEMPORARY_USER;
        }

        this.status = status;
    }

    public void updateAuthority(String authority) {
        this.authority = authority;
    }
    public void updateStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "MemberModel{" +
                "memberId=" + memberId +
                ", userName='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", authority='" + authority + '\'' +
                ", status='" + status + '\'' +
                "} " + super.toString();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.authority));

    }

    //
}
