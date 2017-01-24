package com.thinkgem.jeesite.common.enums;

public enum DistributionTypeEnum {
	distributionDel("00","删除分销商"),
	distributionBind("01","分销商酒店添加关联"),
	distributionBindDel("02","分销商酒店删除关联"),
	priceUpper("03","统一设置价格上浮"),
	specialChannelPrice("04","设置特殊渠道价"),
    ;
    
    
    private String type;
    private String name;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private DistributionTypeEnum(String type, String name) {
		this.type = type;
		this.name = name;
	}
    
    
}
