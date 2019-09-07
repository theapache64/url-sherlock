package com.theapache64.urlsherlock


import android.app.Activity
import android.app.Application
import com.theapache64.twinkill.TwinKill
import com.theapache64.twinkill.googlefonts.GoogleFonts
import com.theapache64.urlsherlock.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()

        // Dagger
        DaggerAppComponent.builder()


            .build()
            .inject(this)

        // TwinKill
        TwinKill.init(
            TwinKill
                .builder()


                .setDefaultFont(GoogleFonts.GoogleSansRegular)
                .build()
        )

    }


}
