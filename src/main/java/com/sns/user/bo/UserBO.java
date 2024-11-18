package com.sns.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.common.EncryptUtils;
import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository;
	
	
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	
	public UserEntity addUser(String loginId, String password, String name, String email) {
		return userRepository.save(UserEntity.builder().loginId(loginId).password(password).name(name).email(email).build());
	}
	
	
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		
		String hashedPassword =  EncryptUtils.md5(password);
		return userRepository.findByLoginIdAndPassword(loginId , hashedPassword);
	}
	
	
	public UserEntity getUserEntityById(int userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	
	
	
}
