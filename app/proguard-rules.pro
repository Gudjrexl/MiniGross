# REMOVE LOGS
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# KEEP RETROFIT INTERFACES
-keep interface retrofit2.** { *; }

# KEEP RETROFIT ANNOTATIONS
-keepattributes Signature
-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations

# KEEP GSON MODEL CLASSES
-keep class com.example.minigross.Model.DataClass.** {
    *;
}

# KEEP SERIALIZED NAMES
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# KEEP VIEWMODEL
-keep class * extends androidx.lifecycle.ViewModel

# KEEP COROUTINES
-keep class kotlinx.coroutines.** { *; }

# KEEP NAVIGATION
-keep class androidx.navigation.** { *; }

# KEEP COIL
-keep class coil.** { *; }

# REMOVE UNUSED CODE
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# OPTIMIZATION
-optimizationpasses 5
-overloadaggressively
-repackageclasses ''
-flattenpackagehierarchy

# REMOVE SOURCE FILE INFO
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# ENABLE SHRINKING
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

# REMOVE UNUSED RESOURCES
-adaptresourcefilenames
-adaptresourcefilecontents