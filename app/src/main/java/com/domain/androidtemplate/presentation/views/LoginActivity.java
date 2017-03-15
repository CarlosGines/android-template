package com.domain.androidtemplate.presentation.views;

import android.os.Bundle;

import com.domain.androidtemplate.presentation.di.DaggerActivityComponent;
import com.domain.androidtemplate.presentation.presenters.LoginPresenter;

import javax.inject.Inject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    // ========================================================================
    // Member variables
    // ========================================================================

    @Inject
    LoginPresenter mPresenter;

    // ========================================================================
    // Activity lifecycle methods
    // ========================================================================

//    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initInjector();
        mPresenter.onCreate();
    }

    private void initInjector() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}

