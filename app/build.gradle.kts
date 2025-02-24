import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sprouts.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sprouts.myapplication"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    // 配置签名信息
    signingConfigs {
        // 定义一个名为 "config" 的签名配置
        create("config") {
            // JKS 文件的路径，这里使用相对路径，可根据实际情况修改
            storeFile = file("linkedlife.jks")
            // JKS 文件的存储密码
            storePassword = "linkedlife@link"
            // 密钥的别名
            keyAlias = "key0"
            // 密钥的密码
            keyPassword = "linkedlife@link"
        }
    }
    // 配置构建类型
    buildTypes {
        // 配置 debug 构建类型
        getByName("debug") {
            // 开启调试模式
            isDebuggable = true
            // 设置签名配置
            signingConfig = signingConfigs.getByName("config")
        }
        // 配置 release 构建类型
        getByName("release") {
            // 不开启调试模式
            isDebuggable = false
            // 设置签名配置
            signingConfig = signingConfigs.getByName("config")
        }
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
    implementation(project(":modulewepay"))
}