package com.theapache64.urlsherlock.di.modules

import com.theapache64.urlsherlock.ui.activities.checker.UrlCheckerActivity


import com.theapache64.urlsherlock.ui.activities.main.MainActivity
import com.theapache64.urlsherlock.ui.activities.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * To hold activities to support AndroidInjection call from dagger.
 */
@Module
abstract class ActivitiesBuilderModule {


    @ContributesAndroidInjector
    abstract fun getSplashActivity(): SplashActivity


    @ContributesAndroidInjector
    abstract fun getMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun getUrlCheckerActivity(): UrlCheckerActivity

}