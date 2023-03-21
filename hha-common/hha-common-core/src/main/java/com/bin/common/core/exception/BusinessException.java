package com.bin.common.core.exception;

import com.bin.common.core.api.AppHttpCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tageshi
 * @date 2023/3/15 17:06
 */
@Setter
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public BusinessException(AppHttpCodeEnum appHttpCodeEnum) {
        this.code = appHttpCodeEnum.getCode();
        this.msg = appHttpCodeEnum.getMsg();
    }
    public static BusinessException newInstance(AppHttpCodeEnum appHttpCodeEnum) {
        return new BusinessException(appHttpCodeEnum.getCode(), appHttpCodeEnum.getMsg());
    }
    public BusinessException() {
    }
}
