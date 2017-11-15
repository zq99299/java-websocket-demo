package cn.mrcode.javawebsocketdemo.stomp.ws;

import java.security.Principal;

/**
 * <pre>
 *  Version         Date            Author          Description
 * ---------------------------------------------------------------------------------------
 *  1.0.0           2017/10/10     zhuqiang        -
 * </pre>
 * @author zhuqiang
 * @version 1.0.0 2017/10/10 13:47
 * @date 2017/10/10 13:47
 * @since 1.0.0
 */
public class MyPrincipal implements Principal {
    private String name;

    public MyPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
