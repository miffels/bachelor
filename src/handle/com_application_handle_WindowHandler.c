#include <jni.h>
#include <stdio.h>
#include "com_application_handle_WindowHandler.h"

JNIEXPORT jint JNICALL Java_com_application_handle_WindowHandler_getHwnd
     (JNIEnv *env, jobject obj, jstring title){
 HWND hwnd = NULL;
 const char *str = NULL;

 str = (*env)->GetStringUTFChars(env, title, 0);
 hwnd = FindWindow(NULL,str);
 (*env)->ReleaseStringUTFChars(env, title, str);
 return (jint) hwnd;
 }