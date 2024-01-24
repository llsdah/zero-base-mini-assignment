package com.example.zerobase.zerobaseminiassignment.repository;

import com.example.zerobase.zerobaseminiassignment.model.FollowModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<FollowModel,Long> {
    void deleteByFollowerMemberIdAndFollowingMemberId(MemberModel member, MemberModel followerMember);
}
