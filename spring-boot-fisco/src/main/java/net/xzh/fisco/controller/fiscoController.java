package net.xzh.fisco.controller;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.BlockNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.fisco.common.model.CommonResult;
import net.xzh.fisco.config.FiscoBcos;

@Api(tags = "Fisco测试")
@RestController
public class fiscoController {

	@Autowired
	private FiscoBcos fiscoBcos;

	@ApiOperation("查询区块高度")
	@RequestMapping(value = "/block", method = RequestMethod.GET)
	public CommonResult<?> Number() {
		BcosSDK bcosSDK = fiscoBcos.getBcosSDK();
		Client client = bcosSDK.getClient(Integer.valueOf(1));
		BlockNumber blockNumber = client.getBlockNumber();
		return CommonResult.success(blockNumber.getBlockNumber());
	}
}
