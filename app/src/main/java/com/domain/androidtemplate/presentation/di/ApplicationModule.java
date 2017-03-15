package com.domain.androidtemplate.presentation.di;

import android.content.Context;

import com.domain.androidtemplate.data.executor.JobExecutor;
import com.domain.androidtemplate.data.respositories.UserDataRepository;
import com.domain.androidtemplate.domain.executor.PostExecutionThread;
import com.domain.androidtemplate.domain.executor.ThreadExecutor;
import com.domain.androidtemplate.domain.repositories.UserRepository;
import com.domain.androidtemplate.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the mApplication lifecycle.
 */
@Module
public class ApplicationModule {

    private final Context mApplicationCtx;

    public ApplicationModule(Context applicationCtx) {
        mApplicationCtx = applicationCtx;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplicationCtx;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    UserRepository provideTzRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }
}