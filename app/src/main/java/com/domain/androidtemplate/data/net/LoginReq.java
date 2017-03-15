package com.domain.androidtemplate.data.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.domain.androidtemplate.domain.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginReq extends Req<User> {

    // ========================================================================
    // Member variables
    // ========================================================================

    private final String mEmail;
    private final String mPassword;

    // ========================================================================
    // Member variables
    // ========================================================================

    public LoginReq(final String email, final String password) {
        mEmail = email;
        mPassword = password;
    }

    // ========================================================================
    // Req implementation
    // ========================================================================

    @Override
    public String getRoute() {
        return Contract.ROUTE;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public JSONObject getJsonRequest() throws JSONException {
        return new JSONObject()
                .put(Contract.REQ_EMAIL, mEmail)
                .put(Contract.REQ_PASSWORD, mPassword);
    }

    @Override
    protected User map(final JSONObject response) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());
        return mapper.convertValue(response, User.class);
    }

    @Override
    protected User handleError(ExecutionException e) {
        final Throwable cause = e.getCause();
        if (!(cause instanceof AuthFailureError)) {
            super.handleExecutionException(e);
        }
//        if (!(cause instanceof ServerError &&
//                getStatusCode((ServerError) cause) == 409)) {
//            super.handleExecutionException(e);
//        }
        return User.EMPTY;
    }

    // ========================================================================
    // Request contract
    // ========================================================================

    public static abstract class Contract {

        private static final String ROUTE = "login";
        private static final String REQ_EMAIL = "email";
        private static final String REQ_PASSWORD = "password";
    }
}