package com.springstudy.shop.board;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springstudy.shop.board.domain.BoardAttachDTO;
import com.springstudy.shop.board.domain.BoardDTO;
import com.springstudy.shop.board.domain.Criteria;
import com.springstudy.shop.board.domain.PageDTO;
import com.springstudy.shop.board.service.IBoardService;

import oracle.net.aso.l;

@Controller
@RequestMapping("/board")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private IBoardService service;
	
	private String uploadPath = "D:\\spring4_workspace\\springstudy\\src\\main\\webapp\\resources\\fileUpload";

	
	@RequestMapping("/list")
	public void listAll(Criteria cri, Model model) throws Exception{
		logger.info("show allList..."+cri);
		// 반환타입이 없으므로 자동으로 매핑걸어준다.  (Model)
		
		model.addAttribute("list", service.listAll(cri));
		
		int total = service.getTotalCnt(cri);
		logger.info("total: " + total);
		// 넘겨줄때 pageMaker로 넘기겠다
		model.addAttribute("pageMaker" , new PageDTO(cri, total));
	}
	
	// 경로는 똑같아도 전혀다름. (GET,POST) 
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public void registerGET() {
		
		logger.info("register get...........");
	}

	
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	// void->String
	public String registerPOST(BoardDTO bDto, RedirectAttributes rttr) throws Exception {
		logger.info("register post...............");
		logger.info("/register ===>)"+bDto);
			
		// null이 아니라면
		logger.info("============================================");
		if(bDto.getAttachList() != null) {
			bDto.getAttachList().forEach(attach -> logger.info("" + attach));
		}
		logger.info("============================================");		
		// 서비스의 레지스터. 파라미터로DTO넘겨줌.
		service.register(bDto);
		
		rttr.addFlashAttribute("result", bDto.getBno());
		
		return "redirect:/board/list";
	}
//	@RequestMapping(value="/read", method = RequestMethod.GET)
//	// @RequestParam어노테이션을 이용해서 input네임명인 bno를 받는다.
//	public void read(@RequestParam("bno") int bno, Model model) throws Exception{
//		// 화면에서 jsp가 받을거는 "board"
//		model.addAttribute("board", service.read(bno));
//	}
//	@RequestMapping(value="/modify", method = RequestMethod.GET)
//	public void modifyGET(int bno, Model model) throws Exception{
//		model.addAttribute(service.read(bno));
//		
//		/* bDto를 넘기니까 java.lang ..set null이러면서 오류가 떴다.
//		 * BoardDTO bDto = new BoardDTO(); model.addAttribute("modify",service.modify(bDto));
//		 */
//	}
//	
//	@RequestMapping(value="/modify", method = RequestMethod.POST)
//	public String modifyPOST(BoardDTO bDto, RedirectAttributes rttr) throws Exception{
//		service.modify(bDto);
//		logger.info("modify post.......");
//		
//		if(service.modify(bDto)) {
//			rttr.addFlashAttribute("result","success");
//	}
//		// 갱신했으면 바로 redirect돌리는게 낫다
//		return "redirect:/board/read?bno=${board.bno}";
//		
//	}
	
	@RequestMapping(value= {"/read","/modify"}, method=RequestMethod.GET)
	public void modifyGET(@RequestParam("bno") int bno,
						  @ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		logger.info("/read or /modify....");
		model.addAttribute("board",service.read(bno));
		/* model.addAttribute("cri",cri); */
	}
	@RequestMapping(value= "/modify", method=RequestMethod.POST)
	public String modifyPOST(BoardDTO bDto,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) throws Exception {
		logger.info("/modify post...........");
		/* model.addAttribute("board",service.read(bno)); */
		
		if(service.modify(bDto)) {
			rttr.addFlashAttribute("result","success");
			
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	
	@RequestMapping(value="/remove",method = RequestMethod.POST)
	// RedirectAttributes 메세지 띄워줌
	public String removePOST(@RequestParam("bno") int bno,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("remove post....");
			// 이거 추가해줌 (remove하면 오류뜨길래)
		List<BoardAttachDTO> attachList = service.getAttachList(bno);
		
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
			deleteFiles(attachList);
	}
	/*
	 * rttr.addAttribute("pageNum",cri.getPageNum());
	 * rttr.addAttribute("amount",cri.getAmount());
	 * rttr.addAttribute("type",cri.getType());
	 * rttr.addAttribute("keyword",cri.getKeyword());
	 */
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@GetMapping(value = "/getAttachList",
				produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody	// json으로 응답하겠다..
	public ResponseEntity<List<BoardAttachDTO>> getAttachList(int bno){
		logger.info("getAttachList : " + bno);
		
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	// 자식 정보 찾는거
	private void deleteFiles(List<BoardAttachDTO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		logger.info("delete attach files............................");
		logger.info(""+attachList);
		
		attachList.forEach(attach -> {
			try {
				// nio
				Path file = Paths.get(uploadPath + "\\" 
									+ attach.getUploadPath() + "\\" 
									+ attach.getUuid() + "_"  
									+  attach.getFileName());
					
				Files.deleteIfExists(file);
				if (Files.probeContentType(file).startsWith("image")) {
					Path thumbnail = Paths.get(uploadPath + "\\" 
									+ attach.getUploadPath() + "\\s_" 
									+ attach.getUuid() + "_" 
									+ attach.getFileName());
				Files.delete(thumbnail);
				}
			} catch (Exception e) {
				logger.error("delete file error : " + e.getMessage());
			}
		});
		
	}
	
}
