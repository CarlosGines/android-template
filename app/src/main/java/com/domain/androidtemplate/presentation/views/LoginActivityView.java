package com.domain.androidtemplate.presentation.views;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.domain.androidtemplate.R;
import com.domain.androidtemplate.presentation.presenters.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * LoginView implementation using LoginActivity.
 */

public class LoginActivityView extends BaseActivityView
        implements LoginView {

    // UI references.
    @BindView(R.id.email)
    public EditText mEmailView;
    @BindView(R.id.password)
    public EditText mPasswordView;

    /**
     * Base activity used to render views.
     */
    private final LoginActivity mActivity;


    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public LoginActivityView (final LoginActivity activity) {
        super(activity);
        mActivity = activity;
    }

    // ========================================================================
    // User input
    // ========================================================================

    @OnClick(R.id.action_button)
    public void onActionClick() {
        mActivity.mPresenter.onActionClick(
                mEmailView.getText().toString().trim(),
                mPasswordView.getText().toString()
        );
    }

    // ========================================================================
    // LoginView implementation
    // ========================================================================

    @Override
    public void initView() {
        mActivity.setContentView(R.layout.activity_login);
        ButterKnife.bind(this, mActivity);

        // Editor action listener (IME Actions)
        mPasswordView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int id,
                                                  KeyEvent keyEvent) {
                        if (id == EditorInfo.IME_ACTION_DONE) {
                            LoginActivityView.this.onActionClick();
                            return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public void resetErrors() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }

    @Override
    public void setEmptyUserNameError() {
        mEmailView.setError(getString(R.string.error_field_required));
        mEmailView.requestFocus();
    }

    @Override
    public void setEmptyPasswordError() {
        mPasswordView.setError(getString(R.string.error_field_required));
        mPasswordView.requestFocus();
    }

    @Override
    public void setAuthFailedError() {
        mPasswordView.setError(getString(R.string.error_auth_failed));
        mPasswordView.requestFocus();
    }
}

