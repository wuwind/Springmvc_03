package com.wuwind.servlet;

import com.wuwind.annotation.Controller;
import com.wuwind.annotation.Qualifier;
import com.wuwind.annotation.RequestMapping;
import com.wuwind.annotation.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class DispatcherServlet extends HttpServlet {

    private List<String> clazzs = new ArrayList<>();
    private Map<String, Object> instanceMap = new HashMap<>();
    private Map<String, Object> handlerMapping = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            getScanPackage("com.wuwind");
            filterAndInstance();
            ioc();
            getHandlerMapping();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String uri = req.getRequestURI();
        System.out.println("uri:  " + uri);
        System.out.println("getContextPath:  " + req.getContextPath());
        System.out.println("getPathInfo:  " + req.getPathInfo());

        Enumeration<String> initParameterNames = getServletConfig().getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            System.out.println("initParameterNames:  " + initParameterNames.nextElement());
        }
        for (String s : handlerMapping.keySet()) {
            if(uri.equals(s)){
                Method m = (Method) handlerMapping.get(s);
                m.setAccessible(true);
                try {
                    m.invoke(instanceMap.get(s.split("/")[1]),new HashMap<>());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    public static void main(String args[]) throws Exception {
//        DispatcherServlet dispatcherServlet = new DispatcherServlet();
//        dispatcherServlet.getScanPackage("com.wuwind");
//        dispatcherServlet.filterAndInstance();
//        dispatcherServlet.ioc();
//        dispatcherServlet.getHandlerMapping();
//    }

    private void ioc() throws IllegalAccessException {
        for (String s : instanceMap.keySet()) {
            Object o = instanceMap.get(s);
            Class c = o.getClass();
            if (c.isAnnotationPresent(Controller.class)) {
                Field[] fields = c.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Qualifier.class)) {
                        Qualifier annotation = field.getAnnotation(Qualifier.class);
                        String key = annotation.value();
                        field.setAccessible(true);
                        field.set(o, instanceMap.get(key));
                    }
                }
            }
        }
    }

    private void getHandlerMapping() {
        for (String s : instanceMap.keySet()) {
            Object o = instanceMap.get(s);
            Class c = o.getClass();
            if (c.isAnnotationPresent(Controller.class)) {
                Method[] declaredMethods = c.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    if (declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping annotation = declaredMethod.getAnnotation(RequestMapping.class);
                        String value = annotation.value();
                        handlerMapping.put("/" + s + "/" + value, declaredMethod);
                    }
                }
            }
        }
    }

    private void filterAndInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String clazz : clazzs) {
            Class c = Class.forName(clazz.replace(".class", ""));
            if (c.isAnnotationPresent(Controller.class)) {
                Object o = c.newInstance();
                Controller annotation = (Controller) c.getAnnotation(Controller.class);
                instanceMap.put(annotation.value(), o);
            } else if (c.isAnnotationPresent(Service.class)) {
                Object o = c.newInstance();
                Service annotation = (Service) c.getAnnotation(Service.class);
                instanceMap.put(annotation.value(), o);
            }
        }
    }


    private void getScanPackage(String packageName) throws Exception {
        String pn = packageName.replaceAll("\\.", "/");
        URL url = this.getClass().getClassLoader().getResource(pn);
        if (null == url)
            return;
//        System.out.println("getFile:  "+url.getFile());
        File file1 = new File(url.toURI());
        String[] list = file1.list();
        for (String s : list) {
//            System.out.println(s);
            File f = new File(url.getFile() + "/" + s);
            if (f.isDirectory()) {
                getScanPackage(packageName + "." + s);
            } else {
                System.out.println(packageName + "." + s);
                clazzs.add(packageName + "." + s);
            }
        }
    }

}
