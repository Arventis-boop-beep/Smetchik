/**
 * File              : astroybat.cpp
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.02.2022
 * Last Modified Date: 13.05.2022
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
#include <android/log.h>

#define LOG(...) ({char ___msg[BUFSIZ]; sprintf(___msg, __VA_ARGS__); __android_log_write(ANDROID_LOG_INFO, "YA", ___msg);});
#define ERROR(...) ({char ___msg[BUFSIZ]; sprintf(___msg, __VA_ARGS__); __android_log_write(ANDROID_LOG_ERROR, "YA", ___msg);});

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
	LOG("Get Smeta object from smeta c struct");
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
Java_com_example_astroybat_activities_MainActivity_getAllSmeta(JNIEnv *env, jobject obj, jstring database) {
	// TODO: implement getAllSmeta()
	struct JNI_callback_data data;
	data.env = env; data.obj = obj;

	stroybat_smeta_get_all(env->GetStringUTFChars(database, 0), NULL, &data,
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
Java_com_example_astroybat_activities_MainActivity_addNewSmeta(JNIEnv* env, jobject obj, jstring database) {

	auto smeta = stroybat_smeta_new(env->GetStringUTFChars(database, 0));
    return smetaObjectFromSmeta(env, smeta); 
}

JNIEXPORT jint JNICALL
Java_com_example_astroybat_activities_MainActivity_removeSmeta(JNIEnv* env, jobject obj, jstring database, jstring uuid) {

	return stroybat_smeta_remove_all(env->GetStringUTFChars(database, 0), env->GetStringUTFChars(uuid, 0));
}

JNIEXPORT jobject JNICALL
Java_com_example_astroybat_activities_SmetaEdit_getSmeta(JNIEnv* env, jobject obj, jstring database, jstring uuid) {

	auto smeta = stroybat_smeta_with_uuid(env->GetStringUTFChars(database, 0), env->GetStringUTFChars(uuid, 0));
    return smetaObjectFromSmeta(env, smeta); 
}

JNIEXPORT jobject JNICALL
Java_com_example_astroybat_activities_SmetaContentMenu_getSmeta(JNIEnv* env, jobject obj, jstring database, jstring uuid) {

	auto smeta = stroybat_smeta_with_uuid(env->GetStringUTFChars(database, 0), env->GetStringUTFChars(uuid, 0));
    return smetaObjectFromSmeta(env, smeta); 
}


JNIEXPORT void JNICALL
Java_com_example_astroybat_activities_SmetaEdit_smetaSetValueForKey(JNIEnv* env, jobject obj, jstring database, jstring smeta_uuid, jstring value, jstring key) {
	stroybat_smeta_set_value_for_key(
			env->GetStringUTFChars(database, 0), 
			env->GetStringUTFChars(smeta_uuid, 0), 
			env->GetStringUTFChars(value, 0), 
			env->GetStringUTFChars(key, 0)
			);
}

jobject 
itemObjectFromItem(JNIEnv *env, StroybatItem *item)
{
    jclass Item = env->FindClass("com/example/astroybat/classes/Item");
    jmethodID newItem = env->GetMethodID(Item, "<init>",
            "(Ljava/lang/String;Ljava/lang/String;IIIILjava/lang/String;Ljava/lang/String;II)V");

    jobject oject;
    oject = env->NewObject(Item, newItem,
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

	return oject;
}

JNIEXPORT void JNICALL
Java_com_example_astroybat_activities_SmetaContentMenu_getAllItemsForSmeta(JNIEnv *env, jobject obj, jstring database, jstring smeta_uuid) {
	
	struct JNI_callback_data data;
	data.env = env; data.obj = obj;

	stroybat_smeta_items_get(env->GetStringUTFChars(database, 0), env->GetStringUTFChars(smeta_uuid, 0), &data,
		[](auto item, auto _data, auto error) -> int {
			if (_data){
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
			}
			return 0;
		}
	);
}

JNIEXPORT void JNICALL
Java_com_example_astroybat_activities_SmetaContentMenu_removeItem(JNIEnv *env, jobject obj, jstring database, jstring item_uuid) {
	stroybat_smeta_remove_item(env->GetStringUTFChars(database, 0), env->GetStringUTFChars(item_uuid, 0));
}

JNIEXPORT void JNICALL
Java_com_example_astroybat_activities_ItemList_getAllItemsFromDatabaseForParent(JNIEnv *env, jobject obj, jstring database, jint data_type, jint parent) {
	
	struct JNI_callback_data data;
	data.env = env; data.obj = obj;

	stroybat_data_get_for_parent(env->GetStringUTFChars(database, 0), static_cast<STROYBAT_DATA_TYPE>(data_type), parent, &data,
		[](auto item, auto _data, auto error) -> int {
			if (_data){
				struct JNI_callback_data *data = static_cast<JNI_callback_data *>(_data);
				JNIEnv *env = data->env; jobject obj = data->obj;
				if (error){
					//jmethodID error_callback = env->GetMethodID(env->GetObjectClass(obj), "getAllSmetaErrorCallback", "(Ljava/lang/String;)V");
					//env->CallVoidMethod (obj, error_callback, env->NewStringUTF(error));
					//free(error);
				} else {
					jmethodID callback = env->GetMethodID(env->GetObjectClass(obj), "getAllItemsFromDatabaseForParentCallback", "(Lcom/example/astroybat/classes/Item;)V");
					env->CallVoidMethod (obj, callback, itemObjectFromItem(env, item));
					LOG("ITEM: %s", item->title);
				}
			}
			return 0;
		}
	);
}

JNIEXPORT jobject JNICALL
Java_com_example_astroybat_activities_ItemList_addItemForSmeta(JNIEnv *env, jobject obj,  jstring database, jobject item_object, jstring smeta_uuid, jint data_type) {

    jclass cls = env->GetObjectClass(item_object);   

    jfieldID titleID = env->GetFieldID(cls, "title", "Ljava/lang/String;");
    jstring title = (jstring)env->GetObjectField(item_object, titleID);

    jfieldID unitID = env->GetFieldID(cls, "unit", "Ljava/lang/String;");
    jstring unit = (jstring)env->GetObjectField(item_object, unitID);

    jfieldID priceID = env->GetFieldID(cls, "price", "I");
    jint price = env->GetIntField(item_object, priceID);	

    jfieldID countID = env->GetFieldID(cls, "count", "I");
    jint count = env->GetIntField(item_object, priceID);	

	StroybatItem *item = stroybat_item_new(NULL, env->GetStringUTFChars(title, 0), env->GetStringUTFChars(unit, 0), price, count, static_cast<STROYBAT_DATA_TYPE>(data_type));
	stroybat_smeta_add_item(env->GetStringUTFChars(database, 0), env->GetStringUTFChars(smeta_uuid, 0), item);

	return itemObjectFromItem(env, item);
}

JNIEXPORT void JNICALL
Java_com_example_astroybat_activities_ItemList_itemSetValueForKey(JNIEnv* env, jobject obj, jstring database, jstring uuid, jstring value, jstring key) {
	stroybat_item_set_value_for_key(
			env->GetStringUTFChars(database, 0), 
			env->GetStringUTFChars(uuid, 0), 
			env->GetStringUTFChars(value, 0), 
			env->GetStringUTFChars(key, 0)
			);
}

#ifdef __cplusplus
}  /* end of the 'extern "C"' block */
#endif
