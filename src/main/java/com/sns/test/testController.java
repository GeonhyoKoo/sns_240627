package com.sns.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sns.user.mapper.UserMapper;

@Controller
public class testController {

	@Autowired
	UserMapper userMapper;
	
	@ResponseBody
	@GetMapping("/test1")
	public String test1() {
		return "sns 테스트입니다.";
	}
	
	
	@ResponseBody
	@GetMapping("/test2")
	public Map<String, Object> test2(){
		Map<String, Object> map = new HashMap<>();
		map.put("tes", 1);
		map.put("ade", 2);
		map.put("cccc", 123423);
		
		return map;
	}
	
	
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}
	
	@ResponseBody
	@GetMapping("/test4")
	public List<Map<String, Object>> test4(){
		return userMapper.selectUserList();
	}
	
	
	
}
