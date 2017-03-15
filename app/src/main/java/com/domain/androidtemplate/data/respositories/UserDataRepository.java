package com.domain.androidtemplate.data.respositories;

import com.domain.androidtemplate.data.datasources.SharedPreferencesDataStore;
import com.domain.androidtemplate.data.datasources.UserRestDataStore;
import com.domain.androidtemplate.domain.models.User;
import com.domain.androidtemplate.domain.repositories.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * A UserRepository for managing user data.
 */
public class UserDataRepository implements UserRepository {

    private final UserRestDataStore mUserRestDataStore;
    private final SharedPreferencesDataStore mSpDataStore;

    @Inject
    public UserDataRepository (UserRestDataStore userRestDataStore,
                               SharedPreferencesDataStore sharedPreferencesDataStore) {

        mUserRestDataStore = userRestDataStore;
        mSpDataStore = sharedPreferencesDataStore;
    }

    @Override
    public Observable<User> login(final String email, final String password) {
        return mUserRestDataStore.login(email, password);
    }

}
