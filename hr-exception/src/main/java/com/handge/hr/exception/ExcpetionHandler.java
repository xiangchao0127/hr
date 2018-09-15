package com.handge.hr.exception;

import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Liujuhao
 * @date 2018/6/5.
 */
@ControllerAdvice
public class ExcpetionHandler implements HandlerExceptionResolver {

    Log logger = LogFactory.getLog(this.getClass());

    @Nullable
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @Nullable Object o, Exception e) {
        logger.error(e);
        e.printStackTrace();
        if (e instanceof UnifiedException) {
            return transform2DTO((UnifiedException) e);
        } else {
            return wrapJavaException(e);
        }
    }

    /**
     * 计划型异常包装
     *
     * @param e 计划型异常
     * @return
     */
    private ModelAndView transform2DTO(UnifiedException e) {
        if (e.getThrowable() != null) {
            return wrapJavaException((Exception) e.getThrowable());
        }
        ModelAndView modelAndView = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map<String, Object> attributes = new HashMap();
        attributes.put("code", e.getCode());
        attributes.put("description", e.getDescription());
        view.setAttributesMap(attributes);
        modelAndView.setView(view);
        return modelAndView;
    }

    /**
     * 非计划型异常包装
     *
     * @param e 非计划型异常
     * @return
     */
    private ModelAndView wrapJavaException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map<String, Object> attributes = new HashMap(1);
        if (e instanceof UnauthenticatedException) {
            attributes.put("code", ExceptionWrapperEnum.Auth_NOT_Validate.getCode());
            attributes.put("description", ExceptionWrapperEnum.Auth_NOT_Validate.getExplain2());
        } else if (e instanceof UnauthorizedException) {
            attributes.put("code", ExceptionWrapperEnum.Role_NOT_Power.getCode());
            attributes.put("description", ExceptionWrapperEnum.Role_NOT_Power.getExplain2());
        }
        //空指针
        else if (e instanceof NullPointerException) {
            attributes.put("code", ExceptionWrapperEnum.NullPointerException.getCode());
            attributes.put("description", ExceptionWrapperEnum.NullPointerException.getExplain2());
        }
        //数学运算异常--分母为零
        else if (e instanceof ArithmeticException) {
            attributes.put("code", ExceptionWrapperEnum.ArithmeticException.getCode());
            attributes.put("description", ExceptionWrapperEnum.ArithmeticException.getExplain2());
        }
        //数组下标越界
        else if (e instanceof ArrayIndexOutOfBoundsException) {
            attributes.put("code", ExceptionWrapperEnum.ArrayIndexOutOfBoundsException.getCode());
            attributes.put("description", ExceptionWrapperEnum.ArrayIndexOutOfBoundsException.getExplain2());
        }
        //数据操作异常
        else if (e instanceof DataAccessException) {
            attributes.put("code", ExceptionWrapperEnum.DataAccessException.getCode());
            attributes.put("description", ExceptionWrapperEnum.DataAccessException.getExplain2());
        }
        //数据库异常
        else if (e instanceof SQLException) {
            attributes.put("code", ExceptionWrapperEnum.SQLException.getCode());
            attributes.put("description", ExceptionWrapperEnum.SQLException.getExplain2());
        }
        //IO异常
        else if (e instanceof IOException) {
            attributes.put("code", ExceptionWrapperEnum.IOException.getCode());
            attributes.put("description", ExceptionWrapperEnum.IOException.getExplain2());
        }
        //指定类不存在
        else if (e instanceof ClassNotFoundException) {
            attributes.put("code", ExceptionWrapperEnum.ClassNotFoundException.getCode());
            attributes.put("description", ExceptionWrapperEnum.ClassNotFoundException.getExplain2());
        }
        //字符串转数字错误
        else if (e instanceof NumberFormatException) {
            attributes.put("code", ExceptionWrapperEnum.NumberFormatException.getCode());
            attributes.put("description", ExceptionWrapperEnum.NumberFormatException.getExplain2());
        }
        //参数错误
        else if (e instanceof IllegalArgumentException) {
            attributes.put("code", ExceptionWrapperEnum.IllegalArgumentException.getCode());
            attributes.put("description", ExceptionWrapperEnum.IllegalArgumentException.getExplain2());
        }
        //没有该类的访问权限
        else if (e instanceof IllegalAccessException) {
            attributes.put("code", ExceptionWrapperEnum.IllegalAccessException.getCode());
            attributes.put("description", ExceptionWrapperEnum.IllegalAccessException.getExplain2());
        }
        //数据类型转换异常
        else if (e instanceof ClassCastException) {
            attributes.put("code", ExceptionWrapperEnum.ClassCastException.getCode());
            attributes.put("description", ExceptionWrapperEnum.ClassCastException.getExplain2());
        }
        //数组存储异常
        else if (e instanceof ArrayStoreException) {
            attributes.put("code", ExceptionWrapperEnum.ArrayStoreException.getCode());
            attributes.put("description", ExceptionWrapperEnum.ArrayStoreException.getExplain2());
        }
        //文件未找到
        else if (e instanceof FileNotFoundException) {
            attributes.put("code", ExceptionWrapperEnum.FileNotFoundException.getCode());
            attributes.put("description", ExceptionWrapperEnum.FileNotFoundException.getExplain2());
        }
        //文件已结束
        else if (e instanceof EOFException) {
            attributes.put("code", ExceptionWrapperEnum.EOFException.getCode());
            attributes.put("description", ExceptionWrapperEnum.EOFException.getExplain2());
        }
        //违背安全原则
        else if (e instanceof SecurityException) {
            attributes.put("code", ExceptionWrapperEnum.SecurityException.getCode());
            attributes.put("description", ExceptionWrapperEnum.SecurityException.getExplain2());
        }
        //方法未找到
        else if (e instanceof NoSuchMethodException) {
            attributes.put("code", ExceptionWrapperEnum.NoSuchMethodException.getCode());
            attributes.put("description", ExceptionWrapperEnum.NoSuchMethodException.getExplain2());
        }
        //线程被中断
        else if (e instanceof InterruptedException) {
            attributes.put("code", ExceptionWrapperEnum.InterruptedException.getCode());
            attributes.put("description", ExceptionWrapperEnum.InterruptedException.getExplain2());
        } else if (e instanceof IncorrectCredentialsException) {
            attributes.put("code", ExceptionWrapperEnum.Auth_Password_Error.getCode());
            attributes.put("description", ExceptionWrapperEnum.Auth_Password_Error.getExplain2());
        } else if (e instanceof LockedAccountException) {
            attributes.put("code", ExceptionWrapperEnum.Auth_Status_Error.getCode());
            attributes.put("description", ExceptionWrapperEnum.Auth_Status_Error.getExplain2());
        } else if (e instanceof AuthenticationException) {
            attributes.put("code", ExceptionWrapperEnum.Auth_NOT_Validate.getCode());
            attributes.put("description", ExceptionWrapperEnum.Auth_NOT_Validate.getExplain2());
        } else {
            attributes.put("code", ExceptionWrapperEnum.UNKNOW_ERROR.getCode());
            attributes.put("description", ExceptionWrapperEnum.UNKNOW_ERROR.getExplain2());
        }
        view.setAttributesMap(attributes);
        modelAndView.setView(view);
        return modelAndView;
    }
}
