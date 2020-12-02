package com.example.order.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * <p><b>Description:</b> 响应结果对象
 * <p><b>Company:</b>
 * @version V0.1
 */

@Data
public class Result<T> implements Serializable {
	private static  long serialVersionUID = 2995622624291771277L;

	//@ApiModelProperty(value = "调用是否成功", example = "true")
	private boolean success = true;

  //  @ApiModelProperty(value = "请求号，一个请求的唯一标识")
	private String requestNo;

  //  @ApiModelProperty(value = "响应码")
    private String code;

  //  @ApiModelProperty(value = "响应消息")
    private String msg;

  //  @ApiModelProperty(value = "响应结果数据")
    private T data;

    public Result() {
        super();
    }

    protected Result(Boolean success, String code, String msg, T data) {
//        this.requestNo = obtainRequestNo();
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 构建成功结果对象，不带响应数据T
     *
     * @param <T> 响应泛型
     * @return Result<T>
     */
    public static <T> Result<T> buildSuccess() {
        return new Result<T>(true, "", "", null);
    }

    /**
     *  构建成功结果对象,带响应码与消息，不带响应数据T
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> buildSuccess(String code, String msg) {
        return new Result<T>(true, code, msg, null);
    }

    /**
     * 构建成功结果对象，带响应数据T
     *
     * @param data 响应数据
     * @param <T> 响应泛型
     * @return Result<T>
     */
    public static <T> Result<T> buildSuccess(T data) {
        return new Result<T>(true, "", "", data);
    }

    /**
     * 构建失败结果对象
     *
     * @param code 响应码
     * @param msg 响应信息
     * @return Result<T>
     */
    public static Result<?> buildFail(String code, String msg) {
        return new Result<Void>(false, code, msg, null);
    }

    /**
     * 构建失败结果对象
     *
     * @param code 响应码
     * @param msg 响应信息
     * @param data 响应数据
     * @param <T> 响应泛型
     * @return Result<T>
     */
    public static <T> Result<T> build(String code, String msg, T data) {
        return new Result<T>(true, code, msg, data);
    }


}