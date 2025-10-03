package net.xzh.fisco.controller;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.BlockNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.fisco.config.FiscoBcos;


/**
 * 配置管理
 */
@Tag(name = "令牌管理", description = "配置管理相关的API")
@RestController
public class fiscoController {

	@Autowired
	private FiscoBcos fiscoBcos;

	@Operation(summary = "查询区块高度", description = "查询区块高度")
	@RequestMapping(value = "/block", method = RequestMethod.GET)
	public Object Number() {
		BcosSDK bcosSDK = fiscoBcos.getBcosSDK();
		Client client = bcosSDK.getClient(Integer.valueOf(1));
		BlockNumber blockNumber = client.getBlockNumber();
		return blockNumber.getBlockNumber();
	}
}
