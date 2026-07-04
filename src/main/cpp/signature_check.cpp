#include <jni.h>
#include <string>
#include <cstring>
#include <vector>
#include <iomanip>
#include <sstream>
#include <openssl/sha.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_myresponsive_activity_ui_transform_SignatureVerifier_nativeGetSignatureHash(
        JNIEnv *env, jobject /* this */, jobject contextObj) {
    jclass contextClass = env->GetObjectClass(contextObj);
    jmethodID getPackageManager = env->GetMethodID(contextClass, "getPackageManager",
                                                    "()Landroid/content/pm/PackageManager;");
    jobject pmObj = env->CallObjectMethod(contextObj, getPackageManager);

    jclass pmClass = env->GetObjectClass(pmObj);
    jmethodID getPackageInfo = env->GetMethodID(pmClass, "getPackageInfo",
                                                 "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jstring packageName = (jstring) env->CallObjectMethod(contextObj,
                                                           env->GetMethodID(contextClass, "getPackageName",
                                                                            "()Ljava/lang/String;"));
    jobject pInfoObj = env->CallObjectMethod(pmObj, getPackageInfo, packageName, 0x00000040);

    jclass pInfoClass = env->GetObjectClass(pInfoObj);
    jfieldID signaturesField = env->GetFieldID(pInfoClass, "signatures",
                                                "[Landroid/content/pm/Signature;");
    jobjectArray sigs = (jobjectArray) env->GetObjectField(pInfoObj, signaturesField);

    if (env->GetArrayLength(sigs) == 0) {
        return env->NewStringUTF("");
    }

    jobject sigObj = env->GetObjectArrayElement(sigs, 0);
    jclass sigClass = env->GetObjectClass(sigObj);
    jmethodID toByteArray = env->GetMethodID(sigClass, "toByteArray", "()[B");
    jbyteArray sigBytes = (jbyteArray) env->CallObjectMethod(sigObj, toByteArray);

    jsize len = env->GetArrayLength(sigBytes);
    jbyte *buf = env->GetByteArrayElements(sigBytes, nullptr);

    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_CTX sha256;
    SHA256_Init(&sha256);
    SHA256_Update(&sha256, buf, len);
    SHA256_Final(hash, &sha256);

    env->ReleaseByteArrayElements(sigBytes, buf, JNI_ABORT);

    std::ostringstream oss;
    for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
        oss << std::hex << std::setw(2) << std::setfill('0') << (int) hash[i];
    }

    return env->NewStringUTF(oss.str().c_str());
}