package com.gamemetricbackend.global.aop.timetrace;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class TimeTraceAspect {

    @Around("@annotation(com.gamemetricbackend.global.aop.timetrace.TimeTrace)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("Exception occurred in method: {}", joinPoint.getSignature().toShortString(),
                ex);
            throw ex;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("Method: {} Execution Time: {}ms", joinPoint.getSignature().toShortString(),
                timeMs);
        }
    }
}
