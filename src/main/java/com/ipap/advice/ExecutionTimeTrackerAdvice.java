package com.ipap.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {

    Logger log = LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);

    // 1st Option - Use @Around annotation
    // @Around("execution(* com.ipap.controller.ProductController.processAll(..))")
    // 2nd Option - Use custom annotation @TrackExecutionTime
    @Around("@annotation(com.ipap.advice.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Process time of method : {}() >>>>>> {} ms", joinPoint.getSignature().getName(), (endTime - startTime));
        return obj;
    }

}
