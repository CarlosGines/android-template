package com.domain.androidtemplate.presentation.presenters;

/**
 * View for Log in screen
 */
public interface LoginView extends BaseView {

    /**
     * Delete all error messages on fields.
     */
    void resetErrors();

    /**
     * Set empty error message on user name field.
     */
    void setEmptyUserNameError();

    /**
     * Set empty error message on password field.
     */
    void setEmptyPasswordError();

    /**
     * Set authentication failure error message on password field.
     */
    void setAuthFailedError();
}
