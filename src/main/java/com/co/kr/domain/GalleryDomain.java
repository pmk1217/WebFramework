package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "builder")
public class GalleryDomain {
	
	private String gySeq;
	private String mbId;
	private String gyTitle;
	private String gyContent;
	private String gyCreateAt;
	private String gyUpdateAt;
}
