package com.co.kr.controller;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.code.Code;
import com.co.kr.domain.GalleryDomain;
import com.co.kr.domain.GalleryFileDomain;
import com.co.kr.exception.RequestException;
import com.co.kr.service.GalleryService;
import com.co.kr.vo.FileListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GalleryFileController {

	@Autowired
	private GalleryService galleryService;
	
	@PostMapping(value = "gyupload")
	public ModelAndView gyUpload(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException, ParseException {
		
		ModelAndView mav = new ModelAndView();
		int gySeq = galleryService.gyfileProcess(fileListVO, request, httpReq);
		fileListVO.setContent(""); //초기화
		fileListVO.setTitle(""); //초기화
		
		// 화면에서 넘어올때는 gySeq String이라 string으로 변환해서 넣어즘
		mav = gySelectOneCall(fileListVO, String.valueOf(gySeq),request);
		mav.setViewName("gallery/galleryList.html");
		return mav;
   }
	
	public ModelAndView gySelectOneCall(@ModelAttribute("fileListVO") FileListVO fileListVO, String gySeq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		System.out.println(map);
		
		map.put("gySeq", Integer.parseInt(gySeq));
		GalleryDomain galleryDomain =galleryService.gallerySelectOne(map);
		System.out.println("galleryDomain"+galleryDomain);
		List<GalleryFileDomain> gyfileList =  galleryService.gallerySelectOneFile(map);
		System.out.println("galleryFileDomain"+gyfileList);
		
		for (GalleryFileDomain list : gyfileList) {
			String path = list.getGyFilePath().replaceAll("\\\\", "/");
			list.setGyFilePath(path);
		}
		
		mav.addObject("gydetail", galleryDomain);
		mav.addObject("gyfiles", gyfileList);

		//삭제시 사용할 용도
		session.setAttribute("gyfiles", gyfileList);

		return mav;
	}
	
	//detail
		@GetMapping("gydetail")
	    public ModelAndView gyDetail(@ModelAttribute("fileListVO") FileListVO fileListVO, @RequestParam("gySeq") String gySeq, HttpServletRequest request) throws IOException {
			ModelAndView mav = new ModelAndView();
			//하나파일 가져오기
			mav = gySelectOneCall(fileListVO, gySeq,request);
			mav.setViewName("gallery/galleryList.html");
			return mav;
		}

		@GetMapping("gyedit")
		public ModelAndView edit(FileListVO fileListVO, @RequestParam("gySeq") String gySeq, HttpServletRequest request) throws IOException {
			ModelAndView mav = new ModelAndView();

			HashMap<String, Object> map = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			
			map.put("gySeq", Integer.parseInt(gySeq));
			GalleryDomain galleryDomain =galleryService.gallerySelectOne(map);
			List<GalleryFileDomain> gyfileList = galleryService.gallerySelectOneFile(map);
			
			for (GalleryFileDomain list : gyfileList) {
				String path = list.getGyFilePath().replaceAll("\\\\", "/");
				list.setGyFilePath(path);
			}

			fileListVO.setSeq(galleryDomain.getGySeq());
			fileListVO.setContent(galleryDomain.getGyContent());
			fileListVO.setTitle(galleryDomain.getGyTitle());
			fileListVO.setIsEdit("gyedit");  // upload 재활용하기위해서
			
		
			mav.addObject("gydetail", galleryDomain);
			mav.addObject("gyfiles", gyfileList);
			mav.addObject("gyfileLen",gyfileList.size());
			
			mav.setViewName("gallery/galleryEditList.html");
			return mav;
		}
		
		@PostMapping("gyeditSave")
		public ModelAndView editSave(@ModelAttribute("fileListVO") FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq) throws IOException {
			ModelAndView mav = new ModelAndView();
			
			//저장
			galleryService.gyfileProcess(fileListVO, request, httpReq);
			
			mav = gySelectOneCall(fileListVO, fileListVO.getSeq(),request);
			fileListVO.setContent(""); //초기화
			fileListVO.setTitle(""); //초기화
			mav.setViewName("gallery/galleryList.html");
			return mav;
		}
		
		@GetMapping("gyremove")
		public ModelAndView mbRemove(@RequestParam("gySeq") String gySeq, HttpServletRequest request) throws IOException {
			ModelAndView mav = new ModelAndView();
			
			HttpSession session = request.getSession();
			HashMap<String, Object> map = new HashMap<String, Object>();
			List<GalleryFileDomain> gyfileList = null;
			if(session.getAttribute("gyfiles") != null) {						
				gyfileList = (List<GalleryFileDomain>) session.getAttribute("gyfiles");
			}

			map.put("gySeq", Integer.parseInt(gySeq));
			
			//내용삭제
			galleryService.gyContentRemove(map);

			for (GalleryFileDomain list : gyfileList) {
				list.getGyFilePath();
				Path filePath = Paths.get(list.getGyFilePath());
		 
		        try {
		        	
		            // 파일 물리삭제
		            Files.deleteIfExists(filePath); // notfound시 exception 발생안하고 false 처리
		            // db 삭제 
								galleryService.gyFileRemove(list);
					
		        } catch (DirectoryNotEmptyException e) {
								throw RequestException.fire(Code.E404, "디렉토리가 존재하지 않습니다", HttpStatus.NOT_FOUND);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}

			//세션해제
			session.removeAttribute("gyfiles"); // 삭제
			mav = gyListCall();
			mav.setViewName("gallery/galleryList.html");
			
			return mav;
		}


	//리스트 가져오기 따로 함수뺌
	public ModelAndView gyListCall() {
		ModelAndView mav = new ModelAndView();
		List<GalleryDomain> items = galleryService.galleryList();
		List<GalleryFileDomain> gyfileList = galleryService.gyfileList();
		mav.addObject("items", items);
		mav.addObject("gyfiles", gyfileList);
		return mav; 
	}
}
	
