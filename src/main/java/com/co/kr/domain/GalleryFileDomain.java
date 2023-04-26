package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "builder")
public class GalleryFileDomain {
	
	private Integer gySeq;
	private String mbId;
	
	private String gyOriginalFileName;
	private String gyNewFileName; //동일 이름 업로드 될 경우
	private String gyFilePath;
	private Integer gyFileSize;
}
