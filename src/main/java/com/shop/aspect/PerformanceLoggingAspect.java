package com.shop.aspect;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceLoggingAspect {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	/* 서비스 로직 실행 시간 측정 AOP */
	@Around("execution(* com.shop.process..*(..))")
	public Object timeChecker(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("===== [{}] 메서드 실행 시간 측정 시작 =====", String.valueOf(joinPoint.getSignature()));
		/* 시간 측정 객체 StopWatch */
		StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
		stopWatch.start(joinPoint.getSignature().getName());
		
		Object object = joinPoint.proceed(); // 핵심 로직
		
		stopWatch.stop();
		log.info("{}", stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
		
		return object;
	}
}
