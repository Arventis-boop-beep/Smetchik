/**
 * File              : astorybat.cpp
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 19.02.2022
 * Last Modified Date: 19.02.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */

#include "stroybat/libstroybat.h"

#include <jni.h>

static jobject g_obj;

int get_all_smeta_callback(StroybatSmeta *smeta, void *data, char *error){
	JNIEnv *env = (JNIEnv *) data;
	
	jclass Smeta = env->FindClass("com/examle/astroybat/Smeta");
	jmethodID newSmeta = env->GetMethodID(Smeta, "<init>", "(Ljava/lang/String;Ljava/lang/String;J(Ljava/lang/String;(Ljava/lang/String;(Ljava/lang/String;(Ljava/lang/String;(Ljava/lang/String;)V");
	if (error){
	} else {
		jobject smeta_object = env->NewObject(Smeta, newSmeta, 
				env->NewStringUTF(smeta->uuid),
				env->NewStringUTF(smeta->title),
				smeta->date,
				env->NewStringUTF(smeta->zakazchik),
				env->NewStringUTF(smeta->podriadchik),
				env->NewStringUTF(smeta->raboti),
				env->NewStringUTF(smeta->obiekt),
				env->NewStringUTF(smeta->osnovaniye)
		);

		jmethodID getAllSmetaCallback = env->GetMethodID(MainActivity, "getAllSmetaCallback", "(Lcom/examle/astroybat/Smeta;)V");
		
		env->CallVoidMethod (g_obj, getAllSmetaCallback, smeta_object);	
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
