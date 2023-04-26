package com.co.kr.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.GalleryContentDomain;
import com.co.kr.domain.GalleryDomain;
import com.co.kr.domain.GalleryFileDomain;

@Mapper
public interface GalleryMapper {

	public List<GalleryDomain> galleryList();
	
	public List<GalleryFileDomain> gyfileList();
	
	//content insert
	public void contentUpload(GalleryContentDomain galleryContentDomain);
	//file insert
	public void fileUpload(GalleryFileDomain galleryFileDomain);

	//content update
	public void gyContentUpdate(GalleryContentDomain galleryContentDomain);
	//file update
	public void gyFileUpdate(GalleryFileDomain galleryFileDomain);

	//content delete 
	public void gyContentRemove(HashMap<String, Object> map);
	//file delete 
	public void gyFileRemove(GalleryFileDomain galleryFileDomain);
	
	//select one
	public GalleryDomain gallerySelectOne(HashMap<String, Object> map);

	//select one file
	public List<GalleryFileDomain> gallerySelectOneFile(HashMap<String, Object> map);
}
