package net.xzh.jclouds.model;


import java.io.Serializable;

public class VirtualMachine implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /**
     * 区域ID，默认RegionOne
     */
    private String regionId;
    
    /**
     * 实例名称
     */
    private String serverName;

    /**
     * 镜像ID
     */
    private String imageId;

    /**
     * 实例规格ID
     */
    private String hardwareId;
    
    /**
     * 网络ID(lan)
     */
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