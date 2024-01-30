package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import java.time.LocalDateTime;

/**
 * 차단
 */
@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(indexes = @Index(name = "block_id", columnList = "blockId"))
public class BlockModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "blockId", nullable = false)
    private Long blockId;

    @ManyToOne
    @JoinColumn(name = "blocker_memberId")
    private MemberModel blockerMember; // 차단을 수행한 사람
    @ManyToOne
    @JoinColumn(name = "block_memberId")
    private MemberModel blockMember; // 차단된 사람
    @ManyToOne
    @JoinColumn(name = "block_postId")
    private PostModel blockPost; // 차단된 게시물

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public BlockModel() {}
    public BlockModel(MemberModel blockerMemberId, MemberModel blockMemberId) {
        this.blockerMember = blockerMemberId;
        this.blockMember = blockMemberId;
    }

    public BlockModel(MemberModel blockerMember, PostModel blockPost) {
        this.blockerMember = blockerMember;
        this.blockPost = blockPost;
    }

    public Long getBlockId() {
        return blockId;
    }

    public MemberModel getBlockerMember() {
        return blockerMember;
    }

    public MemberModel getBlockMember() {
        return blockMember;
    }

    public PostModel getBlockPost() {return blockPost;}

    @Override
    public String toString() {
        return "BlockModel{" +
                "blockId=" + blockId +
                ", blockerMember=" + blockerMember +
                ", blockMember=" + blockMember +
                ", blockPost=" + blockPost +
                "} " + super.toString();
    }
}
