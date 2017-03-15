package com.domain.androidtemplate.domain.interactors;

import com.domain.androidtemplate.domain.executor.PostExecutionThread;
import com.domain.androidtemplate.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>Abstract class for an Interactor.
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).</p>
 *
 * <p>By convention each Interactor implementation will return the result using a {@link org.reactivestreams.Subscriber}
 * that will execute its job in a background thread and will post the result in the UI thread.</p>
 */
public abstract class Interactor<T, Params> {

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private final CompositeDisposable mDisposables;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    Interactor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
        mDisposables = new CompositeDisposable();
    }

    // ==========================================================================
    // Abstract methods
    // ==========================================================================

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link Interactor}.
     */
    abstract Observable<T> buildUseCaseObservable(Params params);

    // ==========================================================================
    // Public methods
    // ==========================================================================

    /**
     * Executes the current use case.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public void execute(@NonNull DisposableObserver<T> observer, Params params) {
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler());
        this.addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    // ==========================================================================
    // Private methods
    // ==========================================================================

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }
}
