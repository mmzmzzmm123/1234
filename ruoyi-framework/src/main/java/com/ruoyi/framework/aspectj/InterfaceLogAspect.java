package com.ruoyi.framework.aspectj;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.InterfaceLog;
import com.ruoyi.common.enums.BusinessStatus;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.domain.model.InterfaceLogRes;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 接口调用日志记录处理
 * 
 * @author ruoyi
 */
@Aspect
@Service
public class InterfaceLogAspect
{
    private static final Logger log = LoggerFactory.getLogger(InterfaceLogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.ruoyi.common.annotation.InterfaceLog)")
    public void logPointCut()
    {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult)
    {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作-TODO:验证
     * 
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult)
    {
        try
        {
            // 获得注解
            InterfaceLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }

            String parameter = parseParameterValue(joinPoint,controllerLog.parameter());
            String operatorName = parseParameterValue(joinPoint,controllerLog.operaterName());
            String operatorIdCard = parseParameterValue(joinPoint,controllerLog.operaterIdCard());
            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            operLog.setOperParam(parameter);
            // 汇聚平台接口日志类型固定为100
            operLog.setBusinessType(100);
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            InterfaceLogRes interfaceLogRes = new InterfaceLogRes();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(new Date());


            interfaceLogRes.setInterface_medthod_name(controllerLog.title());
            interfaceLogRes.setInterface_medthod_addr(controllerLog.url());
            interfaceLogRes.setRequest_network("政务外网互联网接入区");
            interfaceLogRes.setRequest_ip_addr(ip);
            interfaceLogRes.setRequest_mac_addr("");

            interfaceLogRes.setRequest_time(dateString);
            interfaceLogRes.setRequest_param(parameter);
            interfaceLogRes.setRequest_reason("发改委信贷直通车企业或个体工商调用接口回填信息");
            interfaceLogRes.setDept_name("泉州市发展和改革委员会");
            interfaceLogRes.setDept_code("11350500003813613F");
            interfaceLogRes.setOperater_name(operatorName);
            interfaceLogRes.setOperater_idcard(operatorIdCard);

            // 返回参数(构造共享云平台的日志规范作为响应值)
            operLog.setJsonResult(JSON.toJSONString(JSON.toJSONString(interfaceLogRes)));

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());

            if (e != null)
            {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            operLog.setTitle(controllerLog.title());
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private InterfaceLog getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(InterfaceLog.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (int i = 0; i < paramsArray.length; i++)
            {
                if (StringUtils.isNotNull(paramsArray[i]) && !isFilterObject(paramsArray[i]))
                {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     * 
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                return iter.next() instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext();)
            {
                Map.Entry entry = (Map.Entry) iter.next();
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

    private String parseParameterValue(JoinPoint joinPoint,String parameterValue) throws Exception {

        if(StringUtils.isEmpty(parameterValue)){
            return parameterValue;
        }

        //获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //将注解的值中的El表达式部分进行替换
        //创建解析器
        SpelExpressionParser parser = new SpelExpressionParser();
        //获取表达式
        Expression expression = parser.parseExpression(parameterValue);
        //设置解析上下文(有哪些占位符，以及每种占位符的值)
        EvaluationContext context = new StandardEvaluationContext();
        //获取参数值
        Object[] args = joinPoint.getArgs();
        //获取运行时参数的名称
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i],args[i].toString());
        }
        //解析,获取替换后的结果
        return expression.getValue(context).toString();
    }
}
