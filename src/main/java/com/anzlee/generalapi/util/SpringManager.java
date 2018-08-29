package com.anzlee.generalapi.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.lang.reflect.Method;

/**
 * @description Spring容器管家
 * @author : hongshu
 * @date: 2016-10-10
 * @version : 1.0
 */
@Component("springManager")
public class SpringManager implements ApplicationContextAware {

	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context)throws BeansException {
		SpringManager.context = context;
	}
	
	/**
	 * 根据spring bean名称获取bean
	 * @param name
	 * @return
	 */
	public static Object getBean(String name){
		if(context != null){
		   return context.getBean(name);
		}
		return null;
	}

	/**
	 * 根据类型获取bean
	 * @param cls
	 * @return
	 */
	public static Object getBean(Class<?> cls){
		if(context!=null){
			return context.getBean(cls);
		}
		return null;
	}

	/**
	 * 注册Bean
	 * @param beanName
	 * @param className
	 */
	public static void registerBean(String beanName,String className){
		ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) context;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
				.getBeanFactory();
		if (!beanFactory.containsBean(beanName)) {
			BeanDefinitionBuilder bdb = BeanDefinitionBuilder
					.rootBeanDefinition(className);
			bdb.setScope("prototype");
			beanFactory.registerBeanDefinition(beanName, bdb.getBeanDefinition());
		}
	}

	/**
	 * 注册Bean
	 * @param beanName
	 * @param cls
	 */
	public static void registerBean(String beanName,Class<?> cls) {
		registerBean(beanName, cls.getName());
	}

		/**
         * 去掉Controller的Mapping
         * @param controllerBeanName
         */
	public static void unregisterController(String controllerBeanName){
		final RequestMappingHandlerMapping requestMappingHandlerMapping=(RequestMappingHandlerMapping)
				SpringManager.getBean("requestMappingHandlerMapping");
		if(requestMappingHandlerMapping!=null){
			String handler=controllerBeanName;
			Object controller= SpringManager.getBean(handler);
			if(controller==null){
				return;
			}
			final Class<?> targetClass=controller.getClass();
			ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
				public void doWith(Method method) {
					Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
					try {
						Method createMappingMethod = RequestMappingHandlerMapping.class.
								getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
						createMappingMethod.setAccessible(true);
						RequestMappingInfo requestMappingInfo =(RequestMappingInfo)
								createMappingMethod.invoke(requestMappingHandlerMapping,specificMethod,targetClass);
						if(requestMappingInfo != null) {
							requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
						}
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}, ReflectionUtils.USER_DECLARED_METHODS);
		}
	}

	/**
	 * 注册Controller
	 * @param controllerBeanName
	 * @throws Exception
	 */
	public static void registerController(String controllerBeanName) throws Exception{
		final RequestMappingHandlerMapping requestMappingHandlerMapping=(RequestMappingHandlerMapping)
				SpringManager.getBean("requestMappingHandlerMapping");
		if(requestMappingHandlerMapping!=null){
			String handler=controllerBeanName;
			Object controller= SpringManager.getBean(handler);
			if(controller==null){
				return;
			}
			unregisterController(controllerBeanName);
			//注册Controller
			Method method=requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().
					getDeclaredMethod("detectHandlerMethods",Object.class);
			method.setAccessible(true);
			method.invoke(requestMappingHandlerMapping,handler);
		}
	}

}