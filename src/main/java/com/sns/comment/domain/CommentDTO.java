package com.sns.comment.domain;

import com.sns.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

// 댓글 한개에 대한 정의
@Getter
@Setter
public class CommentDTO {

	// 댓글 한개
	private Comment comment;
	
	// 댓글 쓴 사람의 정보
	private UserEntity user;
	
	
}
