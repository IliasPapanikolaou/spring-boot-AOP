package com.ipap.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
* AOP Implementation
* */

@Aspect
@Component
public class LoggingAdvice {

    Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

    // All packages example
    // 1. any method *, 2. root_directory_path.*(any package).*(any class).*(..)(any method with any number of args)
    @Pointcut(value = "execution(* com.ipap.*.*.*(..))")
    public void loggingAllPointCut() {

    }

    // Only for Product Controller and method addProduct
    //@Pointcut(value = "execution(* com.ipap.controller.ProductController.addProduct(..))")
    public void loggingProductControllerPointCut() {

    }

    // It will execute before and after join point
    //@Around("loggingAllPointCut()") //or
    @Around("execution(* com.ipap.*.*.*(..))")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        ObjectMapper mapper = new ObjectMapper();

        // Get method name
        String methodName = proceedingJoinPoint.getSignature().getName();
        // Get class name
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        // Get method arguments
        Object[] array = proceedingJoinPoint.getArgs();
        // Create logger
        log.info("Around Before >>>>>> Method invoked: {}.class : {}() arguments: {}",
                className, methodName, mapper.writeValueAsString(array));
        // Return Object
        Object obj = proceedingJoinPoint.proceed();
        log.info("Around After >>>>>> ClassName: {}.class : methodName: {}() Response: {}",
                className, methodName, mapper.writeValueAsString(obj));
        return obj;
    }

    // This will run just before execution of any method;
    @Before(value = "execution(* com.ipap.*.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before >>>>>> {}()", joinPoint.getSignature().getName());
    }

    // This will run after execution of any method;
    @After(value = "execution(* com.ipap.*.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After >>>>>> {}()", joinPoint.getSignature().getName());
    }

    // This will run after execution of any method;
    @AfterReturning(value = "execution(* com.ipap.*.*.*(..))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        log.info("After Returning >>>>>> {}()", joinPoint.getSignature().getName());
    }
}
