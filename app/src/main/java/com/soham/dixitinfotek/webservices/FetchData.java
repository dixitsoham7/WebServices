package com.soham.dixitinfotek.webservices;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FetchData extends AsyncTask<Void, Void, String> {

    private String mUrl;
    private Context mContext;
    private DataTransferInterface mDataTransferInterface;

    // constructor
    public FetchData(Context context, String url, DataTransferInterface dataTransferInterface ){
        mContext = context;
        mUrl = url;
        mDataTransferInterface = dataTransferInterface;
    }

    // tasks to be executed in background so that UI thread doesn't become heavy
    @Override
    protected String doInBackground(Void... voids) {

        // creating an object of the OkHttpClient.
        OkHttpClient okHttpClient = new OkHttpClient();
        // if the connection is unable to establish then wait for 2 minutes
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        // getting the request for the JSON URL.
        Request request = new Request.Builder().url(mUrl).build();
        String responseData = null;

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                // contains the protocol of the URL
                responseData = response.body().string();
            }
        } catch (IOException e) {
            Toast.makeText(mContext, "Error: "+e, Toast.LENGTH_SHORT).show();
        }
        return responseData;
    }

// method to execute after the execution of response is completed.
    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        mDataTransferInterface.dataHere(response);
    }
}
