/**
 * File              : astroybat.cpp
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.02.2022
 * Last Modified Date: 14.03.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("astroybat");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("astroybat")
//      }
//    }

#include "stroybat/libstroybat.h"

#include <jni.h>
#include <cstdlib>

static jobject g_obj;

extern "C"
jobject 
smetaObjectFromSmeta(JNIEnv *env, StroybatSmeta *smeta)
{
    jclass Smeta = env->FindClass("com/example/astroybat/Smeta");
    jmethodID newSmeta = env->GetMethodID(Smeta, "<init>",
            "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

    jobject smetaObject;
    smetaObject = env->NewObject(Smeta, newSmeta,
                                 env->NewStringUTF(smeta->uuid),
                                 env->NewStringUTF(smeta->title),
                                 (int)smeta->date,
                                 env->NewStringUTF(smeta->zakazchik),
                                 env->NewStringUTF(smeta->podriadchik),
                                 env->NewStringUTF(smeta->raboti),
                                 env->NewStringUTF(smeta->obiekt),
                                 env->NewStringUTF(smeta->osnovaniye)
    );

	return smetaObject;
}

extern "C"
int get_all_smeta_callback(StroybatSmeta *smeta, void *data, char *error){
    JNIEnv *env = (JNIEnv *) data;

    if (error){
    } else {
        jobject smetaObject = smetaObjectFromSmeta(env, smeta); 
		free(smeta); //no need any more

        jclass MainActivity = env->FindClass("com/example/astroybat/MainActivity");
        jmethodID getAllSmetaCallback = env->GetMethodID(MainActivity, "getAllSmetaCallback",
                                                         "(Lcom/example/astroybat/Smeta;)V");

        env->CallVoidMethod (g_obj, getAllSmetaCallback, smetaObject);
    }
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_astroybat_MainActivity_getAllSmeta(JNIEnv* env, jobject obj) {

    g_obj = obj;
    stroybat_get_all_smeta(NULL, env, get_all_smeta_callback);

    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_astroybat_MainActivity_addNewSmeta(JNIEnv* env, jobject obj) {

    g_obj = obj;

	auto smeta = stroybat_smeta_new();
    jobject smetaObject = smetaObjectFromSmeta(env, smeta); 
	free(smeta); //no need any more

	jclass MainActivity = env->FindClass("com/example/astroybat/MainActivity");
	jmethodID newSmetaCallback = env->GetMethodID(MainActivity, "addNewSmetaCallback",
													 "(Lcom/example/astroybat/Smeta;)V");

	env->CallVoidMethod (g_obj, newSmetaCallback, smetaObject);	
	

    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_astroybat_MainActivity_removeSmeta(JNIEnv* env, jobject obj, jstring uuid) {

	return stroybat_smeta_remove_all(env->GetStringUTFChars(uuid, 0));
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_astroybat_SmetaEdit_getSmeta(JNIEnv* env, jobject obj, jstring uuid) {

    g_obj = obj;
	
	auto smeta = stroybat_smeta_with_uuid(env->GetStringUTFChars(uuid, 0));
    jobject smetaObject = smetaObjectFromSmeta(env, smeta); 
	free(smeta); //no need any more

	jclass MainActivity = env->FindClass("com/example/astroybat/SmetaEdit");
	jmethodID callback = env->GetMethodID(MainActivity, "getSmetaCallback",
													 "(Lcom/example/astroybat/Smeta;)V");

	env->CallVoidMethod (g_obj, callback, smetaObject);	

    return 0;
}
