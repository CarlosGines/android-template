package com.domain.androidtemplate.data.net;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Request sent by our app.
 */
public abstract class Req<T> {

    // ========================================================================
    // Non-abstract methods
    // ========================================================================

    /**
     * Send a synchronous volley request.
     * @param ctx A context.
     * @return The body of the response as JSON.
     */
    public T send(final Context ctx) {
        return this.send(ctx, null);
    }

    /**
     * Send a synchronous volley request with auth.
     * @param ctx A context.
     * @param token Auth token to use in the request.
     * @return The body of the response as JSON.
     */
    public T send(final Context ctx, final String token) {
        final String route = this.getRoute();
        try {
            final JSONObject response = this.sendVolleyReq(ctx, token)
                    .get(30, TimeUnit.SECONDS);
            Log.d(route, "Response:\n" + response.toString(4));
            return this.map(response);
        } catch (JSONException e) {
            throw new RuntimeException("JSONException at " + route, e);
        } catch (TimeoutException e) {
            throw new RuntimeException("TimeoutException at: " + route, e);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException at " + route, e);
        } catch (ExecutionException e) {
            return this.handleError(e);
        }
    }

    /**
     * Prepare a volley request to be sent.
     * @param ctx A context.
     * @param token Auth token to use in the request.
     * @return A RequestFuture ready to be sent.
     */
    private RequestFuture<JSONObject> sendVolleyReq(final Context ctx,
                                                    final String token)
            throws JSONException {
        final Uri.Builder uriBuilder = new Uri.Builder()
                .encodedPath(VolleyAdapter.getBaseUrl())
                .appendEncodedPath(this.getRoute());
        final RequestFuture<JSONObject> future = RequestFuture.newFuture();
        VolleyAdapter.getInstance(ctx).addToRequestQueue(
                new VolleyJsonReq(
                        this.getMethod(),
                        uriBuilder.toString(),
                        this.getJsonRequest(),
                        future,
                        token
                )
        );
        Log.d(
                this.getRoute(),
                String.format(
                        "Sending %s request to %s\n%s",
                        this.getMethodName(),
                        uriBuilder,
                        this.getJsonRequest()
                )
        );
        return future;
    }

    /**
     * Transform the request method code to its name.
     * @return Name of the request.
     */
    private String getMethodName() {
        switch(this.getMethod()) {
            case Request.Method.GET:
                return "GET";
            case Request.Method.POST:
                return "POST";
            case Request.Method.PUT:
                return "PUT";
            case Request.Method.DELETE:
                return "DELETE";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * Handle server error sending request. This method can be overridden to perform custom handling
     */
    protected T handleError (ExecutionException e) {
        this.handleExecutionException(e);
        return null;
    }

    /**
     * Get the status code of an error response from server.
     * @param e error response of the server from Volley.
     * @return the status code of the response or 0.
     */
    protected int getStatusCode(final ServerError e) {
        final int code;
        if (e.networkResponse != null) {
            code = e.networkResponse.statusCode;
        } else {
            code = 0;
        }
        return code;
    }

    /**
     * Get the body of an error response from server.
     * @param e error response of the server from Volley.
     * @return the body of the response or 0.
     */
    private String getBody(ServerError e) {
        final String message;
        if (e.networkResponse != null) {
            message = new String(e.networkResponse.data);
        } else {
            message = "empty body";
        }
        return message;
    }

    /**
     * Always throws a RuntimeException that encapsulates either the cause of
     * the ExecutionException if known or the ExecutionException itself.
     * @param e The ExecutionException to be encapsulated.
     */
    protected void handleExecutionException(ExecutionException e) {
        final String route = this.getRoute();
        final Throwable cause = e.getCause();
        if (cause instanceof NoConnectionError) {
            throw new RuntimeException("NoConnectionError at " + route, cause);
        } else if (cause instanceof TimeoutError) {
            throw new RuntimeException("TimeoutError at " + route, cause);
        } else if (cause instanceof ServerError) {
            throw new RuntimeException("ServerError at " + route, cause);
        } else if (cause instanceof AuthFailureError) {
            throw new RuntimeException("AuthFailureError at " + route, cause);
        } else if (cause instanceof ParseError) {
            throw new RuntimeException("Volley ParseError at " + route, cause);
        } else {
            throw new RuntimeException("Unknown ExecutionException at " +
                    route, e);
        }
    }

    // ========================================================================
    // Abstract methods
    // ========================================================================

    /**
     * @return the route used to call Rest API.
     */
    protected abstract String getRoute();

    /**
     * Get the method used to call Rest API. Use the values from
     * {@link Request.Method}.
     *
     * @return the method used to call Rest API.
     */
    protected abstract int getMethod();

    /**
     * Build the JSONObject for the request from the foreground activity.
     *
     * @return the JSONObject with necessary params included.
     * @throws JSONException if a param name is null.
     */
    protected abstract JSONObject getJsonRequest() throws JSONException;

    /**
     * Map the response JSON to return type
     */
    protected abstract T map (JSONObject object);
}
