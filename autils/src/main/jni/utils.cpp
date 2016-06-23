//
// Created by AddBean on 2016/2/18.
//
#include "utils.h"
#include "com_addbean_autils_ndk_NativeJni.h"
#include <malloc.h>

extern jclass _jClass;
extern jobject _jObject;
extern jmethodID _logeID, _toastID;
extern JNIEnv *_env;
extern "C" {
    void C_LOG_E(char *tagStr, char *msg) {
        (_env)->CallVoidMethod(_jObject, _logeID, _env->NewStringUTF(tagStr), _env->NewStringUTF(msg));
    }
    void C_TOAST(char *msg) {
        (_env)->CallVoidMethod(_jObject, _toastID, _env->NewStringUTF(msg));
    }
    char *intToCharArray(int a) {
        if (1 > 0) {
            int num0 = a / 1000;//取千位上的数
            int num1 = a / 100 % 10; //取百位上的数
            int num2 = a / 10 % 10;//取十位上的数
            int num3 = a % 10;//取个位上的数
            char *num_arr = (char *) malloc(5);//字符数组必须手动分配大小；
            num_arr[0] = num0 + 48;
            num_arr[1] = num1 + 48;
            num_arr[2] = num2 + 48;
            num_arr[3] = num3 + 48;
            num_arr[4] = 0;
            return num_arr;
        }
    }
}