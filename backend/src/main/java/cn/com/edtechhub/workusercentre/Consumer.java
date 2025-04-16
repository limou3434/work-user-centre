//package cn.com.edtechhub.workusercentre;
//
//import cn.com.edtechhub.workusercentre.service.UserService;
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Consumer implements CommandLineRunner {
//    @DubboReference
//    private UserService userService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String result = userService.test();
//        System.out.println("Receive result ======> " + result);
//    }
//}