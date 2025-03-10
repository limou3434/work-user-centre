package com.work.workusercentre.aop;

import com.work.workusercentre.annotation.AuthCheck;
import com.work.workusercentre.entity.User;
import com.work.workusercentre.service.UserService;
import com.work.workusercentre.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import jakarta.annotation.Resource;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 权限校验 AOP
 *
 * @author ljp
 */
@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    /**
     * 拦截 @AuthCheck 注解
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取必须得到角色
        String mustRole = authCheck.mustRole();

        // 获取当前登录用户
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes(); // RequestContextHolder 可以获取当前线程的请求上下文
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        UserVO loginUser = userService.userGetLoginState(request);

//        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
//        // 不需要权限，放行
//        if (mustRoleEnum == null) {
//            return joinPoint.proceed();
//        }
//        // 必须有该权限才通过
//        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
//        if (userRoleEnum == null) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
//        // 如果被封号，直接拒绝
//        if (UserRoleEnum.BAN.equals(userRoleEnum)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
//        // 必须有管理员权限
//        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)) {
//            // 用户没有管理员权限，拒绝
//            if (!UserRoleEnum.ADMIN.equals(userRoleEnum)) {
//                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//            }
//        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}
