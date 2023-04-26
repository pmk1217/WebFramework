package com.co.kr.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.co.kr.domain.GalleryDomain;
import com.co.kr.domain.GalleryFileDomain;
import com.co.kr.vo.FileListVO;

public interface GalleryService {

	// 인서트 및 업데이트
	public int gyfileProcess(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq);
	
	public List<GalleryDomain> galleryList();
	
	public List<GalleryFileDomain> gyfileList();
	
	// 하나 삭제
	public void gyContentRemove(HashMap<String, Object> map);

	// 하나 삭제
	public void gyFileRemove(GalleryFileDomain galleryFileDomain);
	
	// 하나 리스트 조회
	public GalleryDomain gallerySelectOne(HashMap<String, Object> map);
	// 하나 파일 리스트 조회
	public List<GalleryFileDomain> gallerySelectOneFile(HashMap<String, Object> map);
}
