//package com.yishi.code.general.modify;
//
//import org.springframework.util.MultiValueMap;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
//import org.springframework.web.servlet.handler.HandlerMethodMappingNamingStrategy;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Map;
//import java.util.Set;
//
//public class RequestMappingHandlerMappingX extends RequestMappingHandlerMapping {
//
////    private Map<RequestMappingInfo, HandlerMethod> handlerMethods=new LinkedHashMap<>();
//
//
//    @Override
//    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
//        Field handlerMethodsField=ReflectionUtils.findField(AbstractHandlerMethodMapping.class,"handlerMethods");
//        Field urlMapField=ReflectionUtils.findField(AbstractHandlerMethodMapping.class,"urlMap");
//        Field namingStrategyField=ReflectionUtils.findField(AbstractHandlerMethodMapping.class,"namingStrategy");
//        handlerMethodsField.setAccessible(true);
//        urlMapField.setAccessible(true);
//        namingStrategyField.setAccessible(true);
//        Map<RequestMappingInfo,HandlerMethod>handlerMethods=null;
//        MultiValueMap<String, RequestMappingInfo> urlMap=null;
//        HandlerMethodMappingNamingStrategy<RequestMappingInfo> namingStrategy=null;
//        try {
//            handlerMethods= (Map<RequestMappingInfo, HandlerMethod>) handlerMethodsField.get(this);
//            urlMap= (MultiValueMap<String, RequestMappingInfo>) urlMapField.get(this);
//            namingStrategy= (HandlerMethodMappingNamingStrategy<RequestMappingInfo>) namingStrategyField.get(this);
//
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        HandlerMethod newHandlerMethod = createHandlerMethod(handler, method);
//        HandlerMethod oldHandlerMethod = handlerMethods.get(mapping);
////        if (oldHandlerMethod != null && !oldHandlerMethod.equals(newHandlerMethod)&&mapping.get) {
////            throw new IllegalStateException("Ambiguous mapping found. Cannot map '" + newHandlerMethod.getBean() +
////                    "' bean method \n" + newHandlerMethod + "\nto " + mapping + ": There is already '" +
////                    oldHandlerMethod.getBean() + "' bean method\n" + oldHandlerMethod + " mapped.");
////        }
//
//        handlerMethods.put(mapping, newHandlerMethod);
//        if (logger.isInfoEnabled()) {
//            logger.info("Mapped \"" + mapping + "\" onto " + newHandlerMethod);
//        }
//
//        Set<String> patterns = getMappingPathPatterns(mapping);
//        for (String pattern : patterns) {
//            if (!getPathMatcher().isPattern(pattern)) {
//                urlMap.add(pattern, mapping);
//            }
//        }
//
//        if (namingStrategy != null) {
//            String name = namingStrategy.getName(newHandlerMethod, mapping);
//
//            Method updateNameMap=ReflectionUtils.findMethod(AbstractHandlerMethodMapping.class,"updateNameMap",String.class,HandlerMethod.class);
//            updateNameMap.setAccessible(true);
//            try {
//                updateNameMap.invoke(this,name,newHandlerMethod);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//    @Override
//    public Map<RequestMappingInfo, HandlerMethod> getHandlerMethods(){
//        return null;
//    }
//
//    /**
//     * @RequestMapping("test")
//     * 	public void initController() throws InvocationTargetException, IllegalAccessException {
//     * 		System.out.println("-------------------------------------------------------------");
//     * 		//将applicationContext转换为ConfigurableApplicationContext
//     * //        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
//     * 		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) ContextLoader.getCurrentWebApplicationContext();   ;
//     *
//     * 		// 获取bean工厂并转换为DefaultListableBeanFactory
//     * 		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
//     *
//     * 		// 通过BeanDefinitionBuilder创建bean定义
//     * 		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(GenerateController.class);
//     *
//     * 		// 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
//     * //        beanDefinitionBuilder.addPropertyReference("userService", "userService");
//     *
//     * 		// 注册bean
//     * 		if(configurableApplicationContext.containsBean("generateController")){
//     * 			configurableApplicationContext.
//     * 		}
//     * 		defaultListableBeanFactory.registerBeanDefinition("generateController", beanDefinitionBuilder.getRawBeanDefinition());
//     *
//     *
//     * 		GenerateController generateController = (GenerateController) configurableApplicationContext.getBean("generateController");
//     * 		System.out.println(generateController.getTableMeta(null,"base_option",null));
//     * 		RequestMappingHandlerMapping requestMappingHandlerMapping=configurableApplicationContext.getBean(RequestMappingHandlerMapping.class);
//     * 		AbstractHandlerMethodMapping rmapping=configurableApplicationContext.getBean(AbstractHandlerMethodMapping.class);
//     * //		Method getMappingForMethod =ReflectionUtils.findMethod(RequestMappingHandlerMapping.class, "getMappingForMethod",Method.class,Class.class);
//     * 		Method getMappingForMethod =ReflectionUtils.findMethod(AbstractHandlerMethodMapping.class, "detectHandlerMethods",Object.class);
//     * 		getMappingForMethod.setAccessible(true);
//     * //		RequestMappingInfo mapping_info = (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, getMappingForMethod,GenerateController.class);
//     * 		Field handmethods=ReflectionUtils.findField(AbstractHandlerMethodMapping.class,"handlerMethods");
//     * 		handmethods.setAccessible(true);
//     * 		Map<Object,HandlerMethod> handlerMethodMap= (Map<Object, HandlerMethod>) handmethods.get(rmapping);
//     * 		handlerMethodMap.remove();
//     *
//     * 		getMappingForMethod.invoke(rmapping,generateController);
//     * //        return userController.toAction("动态注册生成调用");
//     *
//     * 		//删除bean.
//     * 		//defaultListableBeanFactory.removeBeanDefinition("testService");
//     * 	}
//     */
//}
