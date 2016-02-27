package com.addbean.autils.utils;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by AddBean on 2016/2/16.
 */
public class JLog {
    public static void e(Object obj) {
        try {
            Log.e("JLog", obj.getClass().getName() + ":\n" + getMateByReflect(obj));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static String getMateByReflect(Object obj) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String result = "";
        Field[] field = obj.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
        for (int j = 0; j < field.length; j++) {     //遍历所有属性
            String name = field[j].getName();    //获取属性的名字

            name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
            String type = field[j].getGenericType().toString();    //获取属性的类型
            if (type.equals("class java.lang.String")) {   //如果type是类类型，则前面包含"class "，后面跟类名
                Method m = obj.getClass().getMethod("get" + name);
                String value = (String) m.invoke(obj);    //调用getter方法获取属性值
                if (value != null) {
                    result = result + "\t" + name + " : " + value + "\n";
                } else {
                    result = result + "\t" + name + " : " + "null" + "\n";
                }
            }
            if (type.equals("class java.lang.Integer")) {
                Method m = obj.getClass().getMethod("get" + name);
                Integer value = (Integer) m.invoke(obj);
                result = result + "\t" + name + " : " + value + "\n";

            }
            if (type.equals("class java.lang.Short")) {
                Method m = obj.getClass().getMethod("get" + name);
                Short value = (Short) m.invoke(obj);
                result = result + "\t" + name + " : " + value + "\n";

            }
            if (type.equals("class java.lang.Double")) {
                Method m = obj.getClass().getMethod("get" + name);
                Double value = (Double) m.invoke(obj);
                result = result + "\t" + name + " : " + value + "\n";

            }
            if (type.equals("class java.lang.Boolean")) {
                Method m = obj.getClass().getMethod("get" + name);
                Boolean value = (Boolean) m.invoke(obj);
                result = result + "\t" + name + " : " + value + "\n";

            }
            if (type.equals("class java.util.Date")) {
                Method m = obj.getClass().getMethod("get" + name);
                Date value = (Date) m.invoke(obj);
                if (value != null) {
                    result = result + "\t" + name + " : " + value + "\n";
                } else {
                    result = result + "\t" + name + " : " + "null" + "\n";
                }
            }
        }
        return result;
    }

}
