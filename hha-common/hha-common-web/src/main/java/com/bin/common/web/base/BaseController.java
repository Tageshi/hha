package com.bin.common.web.base;

import com.bin.common.core.context.BaseContextHandler;

/**
 * @author tageshi
 * @date 2023/3/15 16:24
 */
public class BaseController {
    public Long getUserId() {
        return BaseContextHandler.getUserID();
    }
    public String getUserName() {
        return BaseContextHandler.getUserName();
    }
}
