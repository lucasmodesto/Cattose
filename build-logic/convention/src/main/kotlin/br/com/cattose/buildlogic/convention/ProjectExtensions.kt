package br.com.cattose.buildlogic.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.the

val Project.libs
    get(): VersionCatalog = the<VersionCatalogsExtension>().named("libs")
