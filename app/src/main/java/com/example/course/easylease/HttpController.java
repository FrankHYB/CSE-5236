package com.example.course.easylease;
import android.content.Context;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request;


public class  HttpController {

    private static HttpController mInstance;
    private OkHttpClient client;
    private static Context mCtx;
    private final String TAG="Cancel all requests";

    private HttpController() {
        client = new OkHttpClient();
    }

    public static synchronized HttpController getInstance() {
        if (mInstance == null) {
            mInstance = new HttpController();
        }
        return mInstance;
    }
    public String run(String url, RequestBody body) throws IOException{
        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()) throw new IOException("Unexpected Error: " + response);
        return response.body().string();
    }

}

