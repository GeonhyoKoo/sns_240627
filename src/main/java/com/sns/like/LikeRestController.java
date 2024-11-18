package com.sns.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sns.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LikeRestController {

	
	private final LikeBO likeBO;
	
	
	// postId, userId 가 필요함. user 은 session정보에 있기에 post는 받아와야함
	// GET : /like?postId=3   -> @RequestParam("postID")
	// GET : /like/3  -> /like/{postId} wildcard 문법-> @PathVariable(name="postId")
	
	@GetMapping("/like/{postId}")
	public Map<String , Object> likeToggle(
			@PathVariable(name="postId") int postId,
			HttpSession session
			){
		
		Map<String , Object> result = new HashMap<>();
		
		// 로그인 여부 
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인 후 이용가능합니다.");
			return result;
		}
		
		// toggle 요청 -> BO
		// 여기서 판단을 하는것보단 BO쪽에서 확인하도록 하는게 더 좋은 흐름
//		int like = likeBO.getLikeByUserIdAndPostId(userId , postId);
//		if (like > 0) {
//			// delete
//			likeBO.deleteLikeByUserIdAndPostId(userId , postId);
//			result.put("code", 200);
//			result.put("result", "삭제");
//						
//		} else {
//			// insert
//			likeBO.addLikeByUserIdAndPostId(userId , postId);
//			result.put("code", 200);
//			result.put("result", "성공");
//		}
		
		likeBO.toggleLike(postId, userId);
		
		
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
		return result;
		
	}
	
}
