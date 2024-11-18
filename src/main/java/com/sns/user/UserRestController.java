package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;
	
	@GetMapping("is-duplicate-id")
	public Map<String, Object> isDuplicateId(
			@RequestParam("loginId") String loginId
			){
		
		
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		boolean isDuplicate = false;
		
		
		if(user != null) {
			isDuplicate = true;
		}
		
		Map<String , Object> result = new HashMap<>();
		result.put("code" , 200);
		result.put("is_duplicate_id", isDuplicate);
		return result;
	}
	
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// md5 알고리즘 - hashing
		// aaaa -> 74b8733745420d4d33f80c4663dc5e5
		// aaaa -> 74b8733745420d4d33f80c4663dc5e5
		String hashedPassword = EncryptUtils.md5(password);
		
		// db insert
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
	}
	
	
	
	
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session
			){
		
		
		// db select
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, password);
		Map<String, Object> result = new HashMap<>();
		
		if (user != null) {
			session.setAttribute("userId",user.getId());
			session.setAttribute("userLoginId",user.getLoginId());
			session.setAttribute("userName",user.getName());
			result.put("code", 200);
			result.put("result", "성공");
			
		} else {
			result.put("code", 300);
			result.put("error_message", "존재하지 않는 사용자입니다");
		}
		
		
		
		return result;
	}
	
	
	
}
