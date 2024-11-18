package com.sns.comment.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {

	private int id;
	private int postId;
	private int userId;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
}
