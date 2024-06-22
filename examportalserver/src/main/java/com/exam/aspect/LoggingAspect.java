//package com.exam.aspect;
//
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//
//
//@Aspect
//@Component
////@Order(1)
//public class LoggingAspect{
//
//	 private Logger logger;
//
//	 public LoggingAspect() {
//	        this.logger = LoggerFactory.getLogger(getClass());
//	    }
//	
//	private final Set<String> excludeMethods = new HashSet<String>(Arrays.asList(
//			"userDetailsService","method2","method3"
//	));
//	
//	@Pointcut("execution(* com.exam..*(..))")
//	public void allMethods() {}
//	
//	@Before("allMethods()")
//	public void logMethodEntry(JoinPoint joinPoint) {
//		Method method = getMethod(joinPoint);
//		if(method!=null && excludeMethods.contains(method.getName())) {
//			return;
//		}
//		String methodName = joinPoint.getSignature().getName();
//		String className = joinPoint.getTarget().getClass().getName();
//		Object[] methodArgs = joinPoint.getArgs();
//		StringBuilder entryLog = new StringBuilder("Entering Method: ");
//		entryLog.append(className).append(".").append(methodName).append("() entry # ");
//		if(method!=null) {
//			String[] parameterNames = Arrays.stream(method.getParameters())
//					.map(param->param.getName())
//					.toArray(String[]::new);
//			for(int i=0;i<methodArgs.length;i++) {
//				entryLog.append(parameterNames[i]).append(":")
//				.append(methodArgs[i]).append(",");
//			}
//			if(methodArgs.length>0) {
//				entryLog.delete(entryLog.length()-2, entryLog.length());
//			}
//		}
//		logger.info(entryLog.toString());
//	}
//	
//	public void logMethodExit(JoinPoint joinPoint) {
//		Method method = getMethod(joinPoint);
//		if(method!=null && excludeMethods.contains(method)) {
//			return;
//		}
//		String methodName = joinPoint.getSignature().getName();
//		String className = joinPoint.getTarget().getClass().getName();
//		logger.info("Exiting Method: {}.{}",className,methodName);
//		
//	}
//	private Method getMethod(JoinPoint joinPoint) {
//		Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
//		String methodName = joinPoint.getSignature().getName();
//		Method resulst = Arrays.stream(methods).filter(method->method.getName().equals(methodName))
//		.findFirst().orElseGet(null);
//		return resulst!=null?resulst:null;
//	}
//	
//}
