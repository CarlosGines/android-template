package com.domain.androidtemplate.presentation.di;

import android.app.Activity;

import com.domain.androidtemplate.presentation.views.LoginActivity;

import dagger.Component;

/**
 * A base component upon which fragments' components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation @PerActivity
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);

    //Exposed to sub-graphs.
    Activity activity();
}