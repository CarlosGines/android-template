package com.domain.androidtemplate.presentation.views;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.domain.androidtemplate.presentation.di.ActivityModule;
import com.domain.androidtemplate.presentation.di.AndroidTemplateApplication;
import com.domain.androidtemplate.presentation.di.ApplicationComponent;

/**
 * Base Activity class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    // ==========================================================================
    // Activity lifecycle methods
    // ==========================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    // ==========================================================================
    // Di helper methods
    // ==========================================================================

    /**
     * Get the Main Application component for dependency injection.
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidTemplateApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     * @return ActivityModule
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
