package com.domain.androidtemplate.domain.interactors;


import com.domain.androidtemplate.domain.executor.PostExecutionThread;
import com.domain.androidtemplate.domain.executor.ThreadExecutor;
import com.domain.androidtemplate.domain.models.User;
import com.domain.androidtemplate.domain.repositories.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import lombok.RequiredArgsConstructor;


/**
 * This is an implementation UseCase that represents an attempt to sign in.
 */
public class LoginInteractor extends Interactor<User, LoginInteractor.Params> {

    // ========================================================================
    // Member variables
    // ========================================================================

    private final UserRepository mUserRepository;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public LoginInteractor(final UserRepository userRepository,
                           final ThreadExecutor threadExecutor,
                           final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    // ========================================================================
    // UseCase methods
    // ========================================================================

    @Override Observable<User> buildUseCaseObservable(Params params) {
        return mUserRepository.login(params.email, params.password);
    }

    // ========================================================================
    // Params
    // ========================================================================

    @RequiredArgsConstructor
    public static final class Params {

        private final String email;

        private final String password;
    }
}