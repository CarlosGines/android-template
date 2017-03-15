package com.domain.androidtemplate.data.datasources;

import android.content.Context;
import android.content.SharedPreferences;

import com.domain.androidtemplate.domain.models.User;

import javax.inject.Inject;

/**
 * A repository to manage User related data.
 */
public class SharedPreferencesDataStore {

    // ==========================================================================
    // Constants
    // ==========================================================================

    /**
     * Name for the default SharedPreferences file.
     */
    private static final String DEFAULT_PREFS = "default_prefs";

    /**
     * ID of the authenticated user.
     */
    private static final String PREFS_USER_ID = "user_id";

    /**
     * User name of the authenticated user.
     */
    private static final String PREFS_USER_EMAIL = "user_email";

    /**
     * User name of the authenticated user.
     */
    private static final String PREFS_USER_FULLNAME = "user_fullname";

    /**
     * Auth token key
     */
    private static final String PREFS_AUTH_TOKEN = "token";

    /**
     * ID to use when no user stored.
     */
    private static final long NO_USER_ID = -1;

    // ==========================================================================
    // Member variables
    // ==========================================================================

    /**
     * A context
     */
    private final Context mCtx;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    @Inject
    public SharedPreferencesDataStore (Context ctx) {
        mCtx = ctx;
    }

    // ==========================================================================
    // SharedPreferencesDataStore public methods
    // ==========================================================================

    public User getAuthUser() {
        final SharedPreferences sp = mCtx.getSharedPreferences(
                DEFAULT_PREFS, Context.MODE_PRIVATE
        );

        final long id = sp.getLong(PREFS_USER_ID, NO_USER_ID);
        if(id != NO_USER_ID) {
            return new User(
                id,
                sp.getString(PREFS_USER_EMAIL, ""),
                sp.getString(PREFS_USER_FULLNAME, ""),
                sp.getString(PREFS_AUTH_TOKEN, "")
            );
        }
        return null;
    }

    public void setAuthUser(final User user) {
        mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .edit()
                .putLong(PREFS_USER_ID, user.getId())
                .putString(PREFS_USER_EMAIL, user.getEmail())
                .putString(PREFS_USER_FULLNAME, user.getFullName())
                .putString(PREFS_AUTH_TOKEN, user.getToken())
                .apply();
    }

    public void deleteAuthUser() {
        mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .edit()
                .remove(PREFS_USER_ID)
                .remove(PREFS_USER_EMAIL)
                .remove(PREFS_USER_FULLNAME)
                .remove(PREFS_AUTH_TOKEN)
                .apply();
    }

    public String getAuthToken() {
        return mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .getString(PREFS_AUTH_TOKEN, "");
    }
}
