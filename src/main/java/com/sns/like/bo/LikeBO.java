package com.sns.like.bo;

import org.springframework.stereotype.Service;

import com.sns.like.mapper.LikeMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeBO {

	
	private final LikeMapper likeMapper;
	
//	public int getLikeByUserIdAndPostId(int userId, int postId) {
//		return likeMapper.selectLikeCountByUserIdAndPostId(userId, postId);
//	}
//	
//	
//	public void addLikeByUserIdAndPostId(int userId , int postId) {
//		likeMapper.insertLikeByUserIdAndPostId(userId, postId);
//	}
//	
//
//	public void deleteLikeByUserIdAndPostId(int userId , int postId) {
//		 likeMapper.deleteLikeByUserIdAndPostId(userId, postId);
//	}
	
	
	// 강사님 풀이
	// 토글 좋아요 /해제
	// input : postId, userId
	// output : x
	public void toggleLike(int postId, int userId) {
		
		// 조회 - postId, userId -> 있으면 삭제, 없으면 추가
		if(likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0) {
			// 있으면 삭제
			likeMapper.deleteLikeByUserIdAndPostId(postId, userId);
		} else {
			// 없으면 추가
			likeMapper.insertLike(postId, userId);
		}
	}
	
	
	// 글에 해당하는 좋아요 개수
	// input : postId output : 좋아요 개수
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId , null);
	}
	
	
	// 좋아요 채울지 말지 여부
	// input : 글번호 , 로그인 된 사람    output : boolean 채운다 true 비운다 false
	public boolean filledLikeByPostIdUserId(int postId , Integer userId) {
		//1. 비로그인 -> 빈하트
		if(userId == null) {
			return false;
		}
		
		//2. 로그인 -> 누른적 없음 빈하트
		//3. 로그인 -> 누른적있음 하트
		// 로그인 된사람 db select
		int likeCount = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		
		return likeCount > 0;
	}
	
	
	// 삭제된 글의 하트 지우기
	public void deleteLikesByPostId(int postId) {
		likeMapper.deleteLikesByPostId(postId);
	}
	
	
	
}
