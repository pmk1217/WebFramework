package com.co.kr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.service.UploadService;

import groovy.util.logging.Slf4j;

@Slf4j
@Controller
public class FileListController {
		
	@Autowired
	private UploadService uploadService;
	
	@PostMapping(value = "upload")
	public ModelAndView bdUpload(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException {
		
		ModelAndView mav = new ModelAndView();
		int bdSeq = uploadService.fileProcess(fileListVO, request, httpReq);
		fileListVO.setContent(""); //초기화
		fileListVO.setTitle(""); //초기화
		
		//화면에서 넘어올떄는 bdseq String이라 string으로 변환해서 넣어줌
		// mav = bdSelectOneCall(fileListVO, String.valueOf(bdSeq), request);
		mav.setViewName("board/boardList.html");
		return mav;
	}
}
