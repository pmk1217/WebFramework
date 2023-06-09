package com.co.kr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.BoardListDomain;

@Mapper
public interface UploadMapper {

	//list
	public List<BoardListDomain> boardList();
	
	//content insert
		public void contentUpload(BoardContentDomain boardContentDomain);
		//file insert
		public void fileUpload(BoardFileDomain boardFileDomain);

		//content update
		public void bdContentUpdate(BoardContentDomain boardContentDomain);
		//file updata
		public void bdFileUpdate(BoardFileDomain boardFileDomain);

	  //content delete 
		public void bdContentRemove(HashMap<String, Object> map);
		//file delete 
		public void bdFileRemove(BoardFileDomain boardFileDomain);
}
