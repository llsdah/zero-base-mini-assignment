package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

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
    @JoinColumn(name = "blocker_member_id")
    private MemberModel blockerMember;
    @ManyToOne
    @JoinColumn(name = "block_member_id")
    private MemberModel blockMember;
    @ManyToOne
    @JoinColumn(name = "block_post_id")
    private PostModel blockPost;


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
