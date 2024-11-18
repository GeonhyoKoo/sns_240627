package com.sns.post.bo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;
import com.sns.user.bo.UserBO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostBO {
	
	private final PostRepository postRepository;
	private final FileManagerService fileManager;
	private final UserBO userBO;
	private final LikeBO likeBO;
	private final CommentBO commentBO;
	

	// input:X
	// output:List<PostEntity>
	public List<PostEntity> getPostList() {
		return postRepository.findByOrderByIdDesc();
	}
	
	// input: 파라미터들 output:PostEntity
	public PostEntity addPost(int userId, String userLoginId, String content, MultipartFile file) {

		// 업로드 후 imagePath를 받아옴
		String imagePath = fileManager.uploadFile(file, userLoginId);

		return postRepository.save(
				PostEntity.builder()
				.userId(userId)
				.content(content)
				.imagePath(imagePath)
				.build());
	}
	
	
	@Transactional
	public void deletePostByPostIdUserId(int postId, int userId) {
		
		// 글 조회
		PostEntity post = postRepository.findById(postId).orElse(null);
		if(post == null) {
			return;
		}
		
		// 글 삭제 -> entitiy로 넘기면 id로 삭제해줌. 
		postRepository.delete(post);
		
		// 이미지 조회
		if (post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}
		
		// 댓글 삭제
		commentBO.deleteCommentsByPostId(postId);
		
		// 좋아요 삭제
		likeBO.deleteLikesByPostId(postId);
		
		
		
	}
	
	
	
}