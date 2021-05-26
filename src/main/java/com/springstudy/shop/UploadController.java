package com.springstudy.shop;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.springstudy.shop.board.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Controller 
@Log4j
public class UploadController {
	private String uploadPath = "D:\\spring4_workspace\\springstudy\\src\\main\\webapp\\resources\\fileUpload";

	@GetMapping("/uploadForm")
		// form태그 이용하는 방법(서블릿에서 했던방식)
	public void uploadForm() {
		log.info("upload Form");
	}
	@PostMapping("/uploadFormAction")
		// 멀티파트파일이라는 배열을 받아온다 (여러개)
		// uploadForm.jsp의 uploadFormAction의 name
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		for(MultipartFile multipartFile : uploadFile) {
			log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			log.info("Upload File Name : "+ multipartFile.getOriginalFilename());
			log.info("Upload File Size : "+ multipartFile.getSize());
			log.info("Upload File ContentType : "+ multipartFile.getContentType());
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax...");
	}
	
	// 폴더 취득
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
			// java.util.Date (오늘날짜 취득)
		Date date = new Date();
		String str = sdf.format(date);
		
			// "-"이것을 기준으로 seperator 해줌 (서블릿 당시에는 수동으로 함)
		return str.replace("-", File.separator);
	}
	
	// 이미지 판단
	private boolean checkImageType(File file) {
		try {
				// 파라미터로 경로잡아줌
			String contentType = Files.probeContentType(file.toPath());
				// 접두어(image라는) 붙여서 반환해줌
			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
			// image면 true, 아니면 false로 반환
		return false;
	}
	
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile){
		log.info("update ajax post.................");
		List<AttachFileDTO> attachList = new ArrayList<>();
		
		String uploadFolderPath = getFolder();
		
		File uploadFolder = new File(uploadPath, getFolder());
		log.info("uploadFolder path: " + uploadFolder);
		
			// 만약 uploadFolder가 존재하지 않는다면, 
		if(uploadFolder.exists() == false) {
			// 생성해라
			// 년,월,일 생성하기(mkdirs사용)
			uploadFolder.mkdirs();
		}
		for (MultipartFile multipartFile : uploadFile) {
			log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			log.info("Upload File Name : "+ multipartFile.getOriginalFilename());
			log.info("Upload File Size : "+ multipartFile.getSize());
			log.info("Upload File ContentType : "+ multipartFile.getContentType());
			
			AttachFileDTO attachFileDto = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
				// ex에서는 \\ 기준으로 잘라줌
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("only file name : " + uploadFileName);
			
				// setFileName이라는 데에다가 setting을 하겠다.
			attachFileDto.setFileName(uploadFileName);
			
				// 파일명 중복방지 (랜덤으로 숫자 생성)
			UUID uuid = UUID.randomUUID();
			
				// 재할당 (파일네임이 똑같이 들어와도 uuid가 다르므로 중복방지를 해줌)
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
				// IO객체가 들어오므로 예외처리
			try {
				File saveFile = new File(uploadFolder, uploadFileName);
					// 원격지에 해당하는 파일 주입
				multipartFile.transferTo(saveFile);
				
					// uuid,uploadFolderPath도 등록시켜줌
				attachFileDto.setUuid(uuid.toString());
				attachFileDto.setUploadPath(uploadFolderPath);
				
					// 이미지인지 아닌지 판단후, true라면
				if(checkImageType(saveFile)) {
					attachFileDto.setImage(true);
						// 썸네일을 만들어라 ("s_" => small의 s)
					File thumbnail = new File(uploadFolder, "s_" + uploadFileName);
						// saveFile넘기고 size를 주는것이 가능함
						// 썸네일 주는것이 가능함. (100x100)
					Thumbnails.of(saveFile).size(100, 100).toFile(thumbnail);
				}
				attachList.add(attachFileDto);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(attachList, HttpStatus.OK);
		
	}
	

}
