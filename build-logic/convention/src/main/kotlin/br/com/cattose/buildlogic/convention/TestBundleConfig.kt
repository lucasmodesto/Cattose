package br.com.cattose.buildlogic.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.testBundleImplementation(target: Project) {
    target.libs.findBundle("test-commons").ifPresent { bundle ->
        bundle.get().forEach {
            "testImplementation"(it)
        }
    }
}

fun DependencyHandlerScope.androidTestBundleImplementation(target: Project) {
    target.libs.findBundle("android-test-commons").ifPresent { bundle ->
        bundle.get().forEach {
            "androidTestImplementation"(it)
        }
    }
}
