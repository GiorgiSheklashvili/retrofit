package ge.gita.restexample;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //http://www.groupkt.com/post/c9b0ccb9/country-and-other-related-rest-webservices.htm
    //https://github.com/codepath/android_guides/wiki/Using-OkHttp

//    private Type collectionType = new TypeToken<List<Appointment>>() {
//    }.getType();
//List<Appointment> appointments = new Gson().fromJson(result, collectionType);

    private TextView resultTextView;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView) findViewById(R.id.tv_result);
    }

    public void onGetDataClick(View view) {
        getDataUsingSeparateThread();
    }

    private void getDataUsingAsyncTask() {
        new MySyncTask().execute(Constants.URL_STRING);
    }

    private void getDataUsingOkHttp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.URL_STRING)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                resultTextView.setText(response.body().toString());
            }
        });
    }

    private void getDataUsingSeparateThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = getDataUsingHttpUrlConnection(Constants.URL_STRING, null);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText(result);
                    }
                });
            }
        }).start();
    }

    private String getDataUsingHttpUrlConnection(String urlString, ContentValues params) {
        HttpURLConnection httpUrlConnection = null;
        StringBuilder result = new StringBuilder("");
        try {

//            urlString += "?" + getQuery(params);
            httpUrlConnection = (HttpURLConnection) new URL(urlString).openConnection();

            httpUrlConnection.setConnectTimeout(5000);
            httpUrlConnection.setReadTimeout(15000);
            httpUrlConnection.setDoInput(true);

            httpUrlConnection.setRequestMethod(Constants.GET_REQUEST_METHOD);
            if (params != null && params.size() > 0) {
                httpUrlConnection.setDoOutput(true);
                OutputStream os = httpUrlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String qruerySring = getQuery(params);
                writer.write(qruerySring);
                writer.flush();
                writer.close();
                os.close();
            }

            httpUrlConnection.connect();
            InputStream in = httpUrlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpUrlConnection != null)
                httpUrlConnection.disconnect();
        }
        return result.toString();
    }

    @NonNull
    private String getQuery(ContentValues params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Set<Map.Entry<String, Object>> s = params.valueSet();

        for (Map.Entry entry : s) {
            if (first)
                first = false;
            else
                result.append("&");

            if ((entry.getValue()) != null && !TextUtils.isEmpty((String) entry.getValue())
                    && (entry.getKey()) != null && !TextUtils.isEmpty((String) entry.getKey())) {
                result.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
            }
        }
        return result.toString();
    }

    private class MySyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return getDataUsingHttpUrlConnection(params[0], null);
        }

        @Override
        protected void onPostExecute(String s) {
            resultTextView.setText(s);
        }
    }
}