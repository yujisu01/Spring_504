package com.springstudy.shop;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
		// '?'물음표 달고오니까 쿼리스트링(Get방식)
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName : " + fileName);
			// uploadPath와 파일명을 붙여서 경로 찾아옴
		File file = new File(uploadPath + "\\"+fileName);
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			 	// contentType에 실어서 보내줌
			header.add("Content-Type", Files.probeContentType(file.toPath()));
				// 아까 만들어줬던 file주입, 헤더에 필요한 정보 실어서 보냄. (상태코드 200)
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping(value = "/download",
				produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
		// Header에서 취득한다(userAgent와, fileName을 꺼내옴)
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		Resource resource = new FileSystemResource(uploadPath + "\\" + fileName);
		
			// 실제파일이 존재하지 않다면
		if(resource.exists()== false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String resourceName = resource.getFilename();
			// 파일이건,이미지건 유효아이디를 달고오므로 삭제해준다.
			// "_" 언더바+ 유효아이디 잘라내줌
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		HttpHeaders headers = new HttpHeaders();
		
		try {
				// MSIE, Trident : Ex임
			boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);
			String downloadName= null;
				// 이건 익스플로러에서 다운로드 받을때 방식
			if(checkIE) {
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF8").replaceAll("\\+", " ");
			}
				// 그 이외(크롬,사파리등..)
			else {
				// 인코딩
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
				// downloadName : 실제파일명
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		
	}
	@PostMapping("/deleteFile")
	@ResponseBody
		// String으로 받음
		// 이미지인지, 파일인지 구분하는 type받아옴
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile : "+ fileName);
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-type", "text/html;charset=UTF-8");
		
			// 파일객체 선언
		File file;
		
		try {
			file = new File(uploadPath + "\\" + URLDecoder.decode(fileName, "UTF-8"));
				// file객체에 원래 delete가 있음ㅋ
			file.delete();
				
				// 이미지같은 경우는 썸네일도 날림
				// type이 이미지라면
			if(type.equals("image")) {
					// s_가 붙은것들은 다 삭제한다. (
				String largeFileName = file.getAbsolutePath().replace("\\s_","\\");
				log.info("largeFileName :"+largeFileName);
				
				file = new File(largeFileName);
				file.delete();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
			// 성공하면 문자열로 deleted를 alert창으로 띄우고, 상태코드
		return new ResponseEntity<>("삭제완료",headers, HttpStatus.OK);
	}
}



