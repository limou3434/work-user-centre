package cn.com.edtechhub.workusercentre.service.impl;

import cn.com.edtechhub.workusercentre.service.TestService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService // 向远端注册服务
public class TestServiceImpl implements TestService {

    @Override
    public String test(String name) {
        return "Hello " + name + "!";
        /*
        开发环境下可以使用下面 curl 其中一条命令进行测试
        curl --header "Content-Type: text/plain" --data 'Dubbo' http://127.0.0.1:50052/cn.com.edtechhub.workusercentre.service.UserService/test/
        curl --header "Content-Type: text/plain" --data 'Dubbo' http://10.10.174.232:50052/cn.com.edtechhub.workusercentre.service.UserService/test/
        */
    }

}
