//
// Created by AddBean on 2016/2/17.
//

#include "com_addbean_autils_ndk_NativeJni.h"
#include "utils.h"
#include "stdio.h"
#include <malloc.h>
#include "jni.h"
#include <effects_blur.h>
#ifndef NATIVE_JNI
#define NATIVE_JNI

 jclass _jClass;
 jobject _jObject;
 jmethodID _logeID, _toastID;
 JNIEnv *_env;
extern "C"{
    /**
     * 初始化常用方法；
     */
    JNIEXPORT void JNICALL Java_com_addbean_autils_ndk_NativeJni_nativeInit(JNIEnv *env, jobject jthiz) {
        jclass clazz = env->GetObjectClass(jthiz);
        _jClass = (jclass)(env)->NewGlobalRef(clazz);
        _jObject = (jobject)(env)->NewGlobalRef(jthiz);
        _logeID = (env)->GetStaticMethodID(_jClass, "loge", "(Ljava/lang/String;Ljava/lang/String;)V");
        _toastID = (env)->GetStaticMethodID(_jClass, "toast", "(Ljava/lang/String;)V");
        _env =env;
    }
    JNIEXPORT jstring JNICALL Java_com_addbean_autils_ndk_NativeJni_getString(JNIEnv *env, jobject jthiz, jstring jstr) {
        C_LOG_E("com_addbean_autils_ndk_NativeJni","test");
        return jstr;
    }
    JNIEXPORT jintArray JNICALL Java_com_addbean_autils_ndk_NativeJni_blur(JNIEnv *env, jobject jthiz, jintArray jarr, jint w, jint h, jint r){
        C_TOAST("开始处理。。。。");
        int *intArr = (int*)env->GetIntArrayElements(jarr,0);
        StackBlur(intArr,w,h,r);
        return jarr;
    }
}
#endif
