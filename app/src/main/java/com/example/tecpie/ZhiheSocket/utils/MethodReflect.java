package com.example.tecpie.ZhiheSocket.utils;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodReflect
  implements Serializable
{
  private static final long serialVersionUID = -3643431517396360966L;

  public static Object execute(Object owner, String methodName, Object[] args)
  {
    Object result = null;
    Class ownerClass = owner.getClass();
    if ((args != null) && (args.length != 0)) {
      Class[] argsClass = new Class[args.length];
      int i = 0; for (int j = args.length; i < j; i++) {
    	  if(args[i]==null){
    		  argsClass[i]=null;
    	  }else
        argsClass[i] = args[i].getClass();
      }
      Method method = null;
      try {
        method = ownerClass.getMethod(methodName, argsClass);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      try {
        result = method.invoke(owner, args);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    } else {
      Method method = null;
      try {
        method = ownerClass.getMethod(methodName, new Class[0]);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      try {
        result = method.invoke(owner, new Object[0]);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    return result;
  }
}