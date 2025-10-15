package net.xzh.generator.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回结果类，用于封装API接口的返回数据 泛型T表示返回的数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {

	private T data;
	private Integer resp_code;
	private String resp_msg;

	public static <T> CommonResult<T> success(String msg) {
		return of(null, ResultCode.SUCCESS.getCode(), msg);
	}

	public static <T> CommonResult<T> success(T model, String msg) {
		return of(model, ResultCode.SUCCESS.getCode(), msg);
	}

	public static <T> CommonResult<T> success(T model) {
		return of(model, ResultCode.SUCCESS.getCode(), "");
	}

	public static <T> CommonResult<T> of(T datas, Integer code, String msg) {
		return new CommonResult<>(datas, code, msg);
	}

	public static <T> CommonResult<T> failed(String msg) {
		return of(null, ResultCode.ERROR.getCode(), msg);
	}

	public static <T> CommonResult<T> failed(T model, String msg) {
		return of(model, ResultCode.ERROR.getCode(), msg);
	}
}
