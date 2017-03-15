package com.domain.androidtemplate.presentation.presenters;

import android.text.TextUtils;

import com.domain.androidtemplate.domain.interactors.DefaultObserver;
import com.domain.androidtemplate.domain.interactors.LoginInteractor;
import com.domain.androidtemplate.domain.models.User;
import com.domain.androidtemplate.presentation.Navigator;

import javax.inject.Inject;

/**
 * Presenter that controls communication between Login views and models.
 */
public class LoginPresenter {

    // ========================================================================
    // Member variables
    // ========================================================================

    /**
     * View object for events callbacks.
     */
    private final LoginView mView;

    /**
     * Navigator
     */
    private final Navigator mNavigator;

    // Interactors
    private final LoginInteractor mLoginInteractor;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public LoginPresenter (LoginView view, Navigator navigator,
                           LoginInteractor loginInteractor) {
        mView = view;
        mNavigator = navigator;
        mLoginInteractor = loginInteractor;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onCreate() {
        mView.initView();
    }

    public void onActionClick(final String email, final String password) {
        this.attemptLogin(email, password);
    }

    public void onDestroy() {
        mLoginInteractor.dispose();
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    /**
     * Attempts to sign in the account specified by the form. If there are form
     * errors (invalid username, missing fields, etc.), the errors are
     * presented and sign in is not made.
     */
    private void attemptLogin(final String email, final String password) {
        boolean hasErrors = false;
        // Reset errors.
        mView.resetErrors();
        // Check for a valid username.
        if (TextUtils.isEmpty(email)) {
            mView.setEmptyUserNameError();
            hasErrors = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mView.setEmptyPasswordError();
            hasErrors = true;
        }

        if (!hasErrors) {
            mLoginInteractor.execute(
                new PrintErrorObserver<User>(
                        mView,
                        new LoginObserver()
                ),
                new LoginInteractor.Params(email, password)
            );
        }
    }

    // ========================================================================
    // Interactor Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from SigninUseCase
     */
    private final class LoginObserver extends DefaultObserver<User> {

        @Override
        public void onNext(final User user) {
            if(user != User.EMPTY) {
                mView.showMessage("Super successful login!");
//                mNavigator.navigateToTzListActivity(user);
            } else {
                mView.showMessage("Who are you? What are you trying?");
//                mView.showProgress(false);
//                mView.setAuthFailedError();
            }
        }
    }

}
