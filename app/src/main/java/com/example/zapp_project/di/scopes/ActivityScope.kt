package com.example.zapp_project.di.scopes

import javax.inject.Scope

@Scope
@kotlin.annotation.Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FILE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope