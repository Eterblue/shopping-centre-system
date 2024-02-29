package com.eterblue.util;

public class ThreadUtil {
    public static ThreadLocal<Long> threadLocal =new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
         return threadLocal.get();
    }
}
