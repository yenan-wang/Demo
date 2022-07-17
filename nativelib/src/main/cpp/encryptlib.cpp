#include <jni.h>
#include <string>

//
// Created by 34281 on 2022/7/17.
//

const std::string iv_content= "nlLoNPE5Tv2CfMd+vVkI/A==";
const std::string key = "5f57YnOLa6laXuSs2qtpwwqkZ5T2JtKHfSgW56AXJpM=";

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_demo_nativelib_EncryptLib_getIv(JNIEnv *env, jobject thiz) {
    return env -> NewStringUTF(iv_content.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_demo_nativelib_EncryptLib_getKey(JNIEnv *env, jobject thiz) {
    return env -> NewStringUTF(key.c_str());
}