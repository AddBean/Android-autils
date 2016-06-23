//
// Created by AddBean on 2016/2/18.
//
#include <jni.h>
#ifndef AUTILSDEMO_UTILS_H
#define AUTILSDEMO_UTILS_H
extern "C"{
    void C_LOG_E(char *tagStr, char *msg);
    void C_TOAST(char *msg);
    char *intToCharArray(int a);
};

#endif //AUTILSDEMO_UTILS_H
