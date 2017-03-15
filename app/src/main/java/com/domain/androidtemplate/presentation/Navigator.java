package com.domain.androidtemplate.presentation;

import android.app.Activity;
import android.content.Intent;

import com.domain.androidtemplate.presentation.di.PerActivity;
import com.domain.androidtemplate.presentation.views.LoginActivity;

import javax.inject.Inject;

/**
 * Class used to navigate through the application.
 */
@PerActivity
public class Navigator {

    /**
     * Default request code
     */
    private static final int DEFAULT_RC = 0;
    public static final String PARAM_KEY = "param";

    private final Activity mActivity;

    @Inject
    public Navigator(Activity activity) {
        mActivity = activity;
    }

    public void navigateBack() {
        mActivity.finish();
    }

    // Navigate to activity clearing stack
    public void navigateToLoginActivityAndClear() {
        Intent i = new Intent(mActivity, LoginActivity.class);
        i.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK
        );
        mActivity.startActivity(i);
    }

    // Navigate to activity with params
    public void navigateToLoginActivity(final String param) {
        Intent i = new Intent(mActivity, LoginActivity.class);
        i.putExtra(PARAM_KEY, param);
        mActivity.startActivity(i);
    }

    // Navigate to activity for result
    public void navigateToLoginActivityForResult() {
        Intent i = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivityForResult(i, DEFAULT_RC);
    }

    public void navigateFromLoginActivityDone(final String param) {
        mActivity.setResult(
                Activity.RESULT_OK, new Intent().putExtra(PARAM_KEY, param)
        );
        mActivity.finish();
    }
}