package com.co.kr.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "builder")
public class GalleryContentDomain {
	private Integer gySeq;
	private String mbId;
	
	private String gyTitle;
	private String gyContent;
}
