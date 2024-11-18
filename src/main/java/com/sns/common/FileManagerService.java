package com.sns.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {
	// 실제 업로드 된 이미지가 저장될 서버 경로
	public static final String FILE_UPLOAD_PATH = "D:\\신보람\\5_spring_project\\sns\\sns_workspace\\images/";
	
	// input: MultipartFile, userLoginId
	// output: String(이미지 경로)
	public String uploadFile(MultipartFile file, String loginId) {
		// 폴더(디렉토리) 생성
		// 예:aaaa_17237482334/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis(); // aaaa_17237482334
		// D:\\신보람\\5_spring_project\\memo\\memo_workpace\\images/aaaa_17237482334/
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		// 폴더 생성
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null; // 폴더 생성시 실패하면 경로를 null로 리턴(에러 아님)
		}
		
		// 파일 업로드
		try {
			byte[] bytes = file.getBytes();
			// ★★★★★★ 나중에 파일명을 영문자로 변경할 것!(한글명은 업로드 불가) ★★★★★
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 시 실패하면 경로를 null로 리턴(에러 아님)
		}
		
		// 파일업로드가 성공하면 이미지 url path 리턴
		// 주소는 이렇게 될 것이다.(예언)
		//    /images/aaaa_17237482334/sun.png
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	
	public void deleteFile(String imagePath) {
		
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[파일 삭제] path:" + path);
				return;
			}
			
			path = path.getParent();
			if(Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[디렉토리 삭제] path: " + path);
				}
			}
			
		}
		
		
		
	}
	
	
}