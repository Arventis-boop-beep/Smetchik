/**
 * File              : astroybat.cpp
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.02.2022
 * Last Modified Date: 18.03.2022
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

#ifdef __cplusplus
extern "C" {
#endif

struct JNI_callback_data {
	JNIEnv *env;
	jobject obj;
};

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
	free(smeta); //no need any more

	return smetaObject;
}

JNIEXPORT void JNICALL
Java_com_example_astroybat_MainActivity_getAllSmeta(JNIEnv *env, jobject obj) {
	// TODO: implement getAllSmeta()
	struct JNI_callback_data data;
	data.env = env; data.obj = obj;

	stroybat_get_all_smeta(NULL, &data,
		[](auto smeta, auto _data, auto error) -> int {
			struct JNI_callback_data *data = static_cast<JNI_callback_data *>(_data);
			JNIEnv *env = data->env; jobject obj = data->obj;
			if (error){
				//jmethodID error_callback = env->GetMethodID(env->GetObjectClass(obj), "getAllSmetaErrorCallback", "(Ljava/lang/String;)V");
				//env->CallVoidMethod (obj, error_callback, env->NewStringUTF(error));
				//free(error);
			} else {
				jmethodID callback = env->GetMethodID(env->GetObjectClass(obj), "getAllSmetaCallback", "(Lcom/example/astroybat/Smeta;)V");
				env->CallVoidMethod (obj, callback, smetaObjectFromSmeta(env, smeta));
			}
			return 0;
		}
	);
}

JNIEXPORT jobject JNICALL
Java_com_example_astroybat_MainActivity_addNewSmeta(JNIEnv* env, jobject obj) {

	auto smeta = stroybat_smeta_new();
    return smetaObjectFromSmeta(env, smeta); 
}

JNIEXPORT jint JNICALL
Java_com_example_astroybat_MainActivity_removeSmeta(JNIEnv* env, jobject obj, jstring uuid) {

	return stroybat_smeta_remove_all(env->GetStringUTFChars(uuid, 0));
}

JNIEXPORT jobject JNICALL
Java_com_example_astroybat_SmetaEdit_getSmeta(JNIEnv* env, jobject obj, jstring uuid) {

	auto smeta = stroybat_smeta_with_uuid(env->GetStringUTFChars(uuid, 0));
    return smetaObjectFromSmeta(env, smeta); 
}


#ifdef __cplusplus
}  /* end of the 'extern "C"' block */
#endif
