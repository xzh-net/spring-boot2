package net.xzh.elasticsearch.domain;


/**
 * @author zlt
 */
public class IndexDto {
    /**
     * 索引名
     */
    private String indexName;
    /**
     * 分片数 number_of_shards
     */
    private Integer numberOfShards;
    /**
     * 副本数 number_of_replicas
     */
    private Integer numberOfReplicas;
    /**
     * 类型
     */
    private String type;
    /**
     * mappings内容
     */
    private String mappingsSource;
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public Integer getNumberOfShards() {
		return numberOfShards;
	}
	public void setNumberOfShards(Integer numberOfShards) {
		this.numberOfShards = numberOfShards;
	}
	public Integer getNumberOfReplicas() {
		return numberOfReplicas;
	}
	public void setNumberOfReplicas(Integer numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMappingsSource() {
		return mappingsSource;
	}
	public void setMappingsSource(String mappingsSource) {
		this.mappingsSource = mappingsSource;
	}
    
}