/**
 * File              : astroybat.cpp
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.02.2022
 * Last Modified Date: 22.03.2022
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
    jclass Smeta = env->FindClass("com/example/astroybat/classes/Smeta");
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
Java_com_example_astroybat_activities_MainActivity_getAllSmeta(JNIEnv *env, jobject obj) {
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
				jmethodID callback = env->GetMethodID(env->GetObjectClass(obj), "getAllSmetaCallback", "(Lcom/example/astroybat/classes/Smeta;)V");
				env->CallVoidMethod (obj, callback, smetaObjectFromSmeta(env, smeta));
			}
			return 0;
		}
	);
}

JNIEXPORT jobject JNICALL
Java_com_example_astroybat_activities_MainActivity_addNewSmeta(JNIEnv* env, jobject obj) {

	auto smeta = stroybat_smeta_new();
    return smetaObjectFromSmeta(env, smeta); 
}

JNIEXPORT jint JNICALL
Java_com_example_astroybat_activities_MainActivity_removeSmeta(JNIEnv* env, jobject obj, jstring uuid) {

	return stroybat_smeta_remove_all(env->GetStringUTFChars(uuid, 0));
}

JNIEXPORT jobject JNICALL
Java_com_example_astroybat_activities_SmetaEdit_getSmeta(JNIEnv* env, jobject obj, jstring uuid) {

	auto smeta = stroybat_smeta_with_uuid(env->GetStringUTFChars(uuid, 0));
    return smetaObjectFromSmeta(env, smeta); 
}

jobject 
itemObjectFromItem(JNIEnv *env, StroybatItem *item)
{
    jclass Item = env->FindClass("com/example/astroybat/classes/Item");
    jmethodID newItem = env->GetMethodID(Item, "<init>",
            "(Ljava/lang/String;Ljava/lang/String;IIIILjava/lang/String;Ljava/lang/String;II)V");

    jobject smetaObject;
    smetaObject = env->NewObject(Item, newItem,
                                 env->NewStringUTF(item->uuid),
                                 env->NewStringUTF(item->smeta_uuid),
                                 item->id,
                                 item->parent,
                                 item->number,
                                 item->index,
                                 env->NewStringUTF(item->title),
                                 env->NewStringUTF(item->unit),
                                 item->price,
                                 item->count
    );
	free(item); //no need any more

	return smetaObject;
}

JNIEXPORT void JNICALL
Java_com_example_astroybat_activities_SmetaContentMenu_getAllItemsForSmeta(JNIEnv *env, jobject obj, jstring smeta_uuid) {
	
	struct JNI_callback_data data;
	data.env = env; data.obj = obj;

	stroybat_get_items_for_smeta(env->GetStringUTFChars(smeta_uuid, 0), &data,
		[](auto item, auto _data, auto error) -> int {
			struct JNI_callback_data *data = static_cast<JNI_callback_data *>(_data);
			JNIEnv *env = data->env; jobject obj = data->obj;
			if (error){
				//jmethodID error_callback = env->GetMethodID(env->GetObjectClass(obj), "getAllSmetaErrorCallback", "(Ljava/lang/String;)V");
				//env->CallVoidMethod (obj, error_callback, env->NewStringUTF(error));
				//free(error);
			} else {
				jmethodID callback = env->GetMethodID(env->GetObjectClass(obj), "getAllItemsForSmetaCallback", "(Lcom/example/astroybat/classes/Item;)V");
				env->CallVoidMethod (obj, callback, itemObjectFromItem(env, item));
			}
			return 0;
		}
	);
}


#ifdef __cplusplus
}  /* end of the 'extern "C"' block */
#endif
