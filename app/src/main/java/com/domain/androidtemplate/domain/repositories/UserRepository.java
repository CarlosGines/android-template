package com.domain.androidtemplate.domain.repositories;

import com.domain.androidtemplate.domain.models.User;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for handling {@link User} related data.
 */
public interface UserRepository {

  /**
   * Get an {@link Observable} which will emit a {@link User}.
   *
   * @param email The user email used to authenticate user.
   * @param password The user password used to authenticate user.
   */
  Observable<User> login(final String email, final String password);
}
