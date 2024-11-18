package com.sns.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

	
//	public int selectLikeCountByUserIdAndPostId(
//			@Param("postId") int postId,
//			@Param("userId") int userId);
	
	
	// input : postId
	// output : 좋아요 개수
//	public int selectLikeCountByPostId(int postId);
	
	
	// 위에 두 메소드를 합친 하나의 쿼리
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId);
	
	
	public void insertLike(
			@Param("postId") int postId,
			@Param("userId") int userId);
	

	public void deleteLikeByUserIdAndPostId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public void deleteLikesByPostId(int postId);
	
	
}
