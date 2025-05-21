# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Google Sign_In 관련 클래스 예외 처리
# Google Sign-In 관련 클래스 보호
-keep class com.google.android.gms.auth.api.signin.** { *; }
-keep class com.google.android.gms.common.api.** { *; }
-keep class com.google.android.gms.tasks.** { *; }
-keep class com.google.android.gms.base.** { *; }
-keep class com.google.android.gms.auth.api.Auth { *; }

# Supabase SDK 보호
-keep class io.supabase.** { *; }
-keep class io.postgrest.** { *; }
-keep class io.ktor.** { *; }

# JSON 직렬화 관련 클래스 보호 (Gson & Kotlinx.serialization)
-keep class kotlinx.serialization.** { *; }
-keep class com.google.gson.** { *; }

# Data Class 직렬화 유지
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# UserDTO 유지 (패키지명이 맞는지 확인 필요)
-keep class com.app.domain.model.user.UserDTO { *; }

# 리플렉션 사용 보호
-keepattributes Signature
-keepattributes *Annotation*

# 제네릭 타입 정보 유지 (중요!)
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken { *; }
-keep public class * implements java.lang.reflect.Type

# TypeAdapter 관련 정보 유지 (allowobfuscation 제거)
-keep class * implements com.google.gson.TypeAdapter { *; }
-keep class * implements com.google.gson.TypeAdapterFactory { *; }
-keep class * implements com.google.gson.JsonSerializer { *; }
-keep class * implements com.google.gson.JsonDeserializer { *; }

# JSON 직렬화에 사용될 데이터 모델 유지 (패키지명 확인 필요)
-keep class com.app.domain.model.** { *; }
-keep class com.app.domain.model.state.** { *; }

# Supabase에서 사용하는 JSON 관련 클래스 보호
-keep class org.json.** { *; }
-keep class com.fasterxml.jackson.** { *; }