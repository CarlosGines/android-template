package com.domain.androidtemplate.data.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Helper class to send Volley requests
 */
public class VolleyAdapter {

    // ========================================================================
    // Constants
    // ========================================================================

    /**
     * Base URL of development server used for every web service call. Don't
     * use it directly, use {@code VolleyAdapter.getBaseUrl(Context)} to obtain
     * the right url dynamically.
     */
    public final static String BASE_URL = "http://192.168.1.1:3000";

    // ========================================================================
    // Member variables
    // ========================================================================

    private static VolleyAdapter mInstance;
    private final RequestQueue mRequestQueue;

    // ========================================================================
    // Getters & Setters
    // ========================================================================

    /**
     * Get the base URL of the server to send requests. It can be changed by
     * app admins at runtime.
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }

    // ========================================================================
    // Instantiation
    // ========================================================================

    private VolleyAdapter(final Context ctx) {
        mRequestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }

    public static synchronized VolleyAdapter getInstance(final Context context) {
        if (mInstance == null) {
            mInstance = new VolleyAdapter(context.getApplicationContext());
        }
        return mInstance;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }
}