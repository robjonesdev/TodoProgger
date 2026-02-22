# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\robjo\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.kts.

# Room specific rules
-keep class * extends androidx.room.RoomDatabase { <init>(); }
-keep class com.robjonesdev.todoprogger.data.** { *; }

# Kotlin Multiplatform / Compose specific rules
-keep class org.jetbrains.compose.** { *; }
-keep interface org.jetbrains.compose.** { *; }
