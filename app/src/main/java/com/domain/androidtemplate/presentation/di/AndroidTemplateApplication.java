package com.domain.androidtemplate.presentation.di;

import android.app.Application;

/**
 * Android Main Application
 */
public class AndroidTemplateApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    public ApplicationComponent getApplicationComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this)).build();
        }
        return mApplicationComponent;
    }
}
