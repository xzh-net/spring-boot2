package net.xzh.jclouds.model;


import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class VirtualMachine implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "区域ID，默认RegionOne")
    private String regionId;
    
    @ApiModelProperty(value = "实例名称")
    private String serverName;

    @ApiModelProperty(value = "镜像ID，6c573993-7271-499b-b443-5e298c6c09a7")
    private String imageId;

    @ApiModelProperty(value = "实例规格，lif.flavor.id")
    private String hardwareId;
    
    @ApiModelProperty(value = "实例网络,多个以英文逗号分割，ad10a9c6-0cf9-4fcb-8c64-de20f0abcf55")
    private String networks;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	public String getNetworks() {
		return networks;
	}

	public void setNetworks(String networks) {
		this.networks = networks;
	}
    
}