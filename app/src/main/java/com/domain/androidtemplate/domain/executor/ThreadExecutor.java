package com.domain.androidtemplate.domain.executor;

import com.domain.androidtemplate.domain.interactors.Interactor;

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link Interactor} out of the UI thread.
 */
public interface ThreadExecutor extends Executor {}
