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

    @ApiModelProperty(value = "镜像ID")
    private String imageId;

    @ApiModelProperty(value = "实例规格ID")
    private String hardwareId;
    
    @ApiModelProperty(value = "网络ID(lan)")
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