package com.domain.androidtemplate.data.datasources;

import android.content.Context;

import com.domain.androidtemplate.data.net.LoginReq;
import com.domain.androidtemplate.domain.models.User;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * A repository to manage User related data.
 */
public class UserRestDataStore {

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
    public UserRestDataStore (Context ctx) {
        mCtx = ctx;
    }

    // ==========================================================================
    // UserRestDataStore public methods
    // ==========================================================================

    public Observable<User> login(final String userName, final String password) {
        return Observable.create(
            emitter -> emitter.onNext(new LoginReq(userName, password).send(mCtx))
        );
    }
}
