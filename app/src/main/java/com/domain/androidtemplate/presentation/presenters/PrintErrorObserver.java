package com.domain.androidtemplate.presentation.presenters;


import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;

import java.util.concurrent.TimeoutException;

import io.reactivex.observers.DisposableObserver;
import lombok.RequiredArgsConstructor;

/**
 * Observer base class that prints errors and delegates to other observer.
 */
@RequiredArgsConstructor
public class PrintErrorObserver<T> extends DisposableObserver<T> {

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private final BaseView mBaseView;

    private final DisposableObserver<T> mOrigin;

    // ==========================================================================
    // DisposableObserver<T> methods
    // ==========================================================================

    @Override
    public void onNext(T t) {
        mOrigin.onNext(t);
    }

    @Override
    public final void onError(Throwable e) {
        e.printStackTrace();
        Throwable cause = e.getCause();
        mBaseView.showProgress(false);
        if (cause != null) {
            if (cause instanceof NoConnectionError
                    || cause instanceof TimeoutError
                    || cause instanceof TimeoutException
                    ) {
                mBaseView.showNoConnection(true);
                mOrigin.onError(e);
                return;
            }
            if (cause instanceof AuthFailureError) {
                mBaseView.showMessage("Unauthorized");
                mOrigin.onError(e);
                return;
            }
        }
        mBaseView.showErrorMessage(e);
        mOrigin.onError(e);
    }

    @Override
    public void onComplete() {
        mOrigin.onComplete();
    }
}
