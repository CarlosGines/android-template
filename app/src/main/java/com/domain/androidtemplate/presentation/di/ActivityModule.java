package com.domain.androidtemplate.presentation.di;

import android.app.Activity;

import com.domain.androidtemplate.presentation.presenters.LoginView;
import com.domain.androidtemplate.presentation.views.LoginActivity;
import com.domain.androidtemplate.presentation.views.LoginActivityView;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity provideActivity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    LoginActivity provideLauncherActivity() {
        return (LoginActivity) activity;
    }

    @Provides
    @PerActivity
    LoginView provideLauncherView(final LoginActivityView view) {
        return view;
    }
}
