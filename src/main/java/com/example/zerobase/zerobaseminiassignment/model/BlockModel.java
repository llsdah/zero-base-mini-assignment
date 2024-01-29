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
public class BlockModel extends RegistrationDateModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "blockId", nullable = false)
    private Long blockId;

    @ManyToOne
    @JoinColumn(name = "blocker_memberId")
    private MemberModel blockerMember;
    @ManyToOne
    @JoinColumn(name = "block_memberId")
    private MemberModel blockMember;
    @ManyToOne
    @JoinColumn(name = "block_postId")
    private PostModel blockPost;

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
