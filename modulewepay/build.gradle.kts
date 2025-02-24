import org.gradle.api.tasks.Copy
import java.io.File

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sprouts.modulewepay"
    compileSdk = 35

    defaultConfig {
//        applicationId = "com.sprouts.modulewepay"
        minSdk = 26
        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    sourceSets["main"].jniLibs.srcDirs("libs")


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    api("com.tencent.mm.opensdk:wechat-sdk-android:+")
    // 从 libs 目录中包含所有的 .jar 文件
    compileOnly(files("libs/classes.jar"))
}

// 创建自定义任务
tasks.register("customAssembleDebug") {
    // 依赖 assembleDebug 任务
    dependsOn("assembleDebug")
    var versionCode = 1

    // 定义任务执行逻辑
    doLast {
        // 获取 debug APK 输出目录
        val outputDir = File("$buildDir/outputs/aar/")
        // 过滤出 APK 文件
        val apkFiles = outputDir.listFiles { file -> file.name.endsWith(".aar") }
        if (apkFiles != null && apkFiles.isNotEmpty()) {
            // 取第一个 APK 文件
            val sourceApk = apkFiles[0]
            // 定义自定义输出目录
            val targetDir = File("$buildDir/custom_output")
            // 创建自定义输出目录（如果不存在）
            targetDir.mkdirs()
            // 定义目标 APK 文件路径
            val targetApk = File("$targetDir/wepay_release_v${versionCode}_${System.currentTimeMillis()}.aar")
//            val targetApk = File("$targetDir/${sourceApk.name}${System.currentTimeMillis()}")
            // 复制 APK 文件到自定义输出目录
            sourceApk.copyTo(targetApk, overwrite = true)
            println("Custom task: Copied APK to $targetApk")
        }
    }
}

