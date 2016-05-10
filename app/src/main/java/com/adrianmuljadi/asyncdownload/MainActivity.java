package com.adrianmuljadi.asyncdownload;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

//    private int progressStatus = 0;
    private ProgressBar progressBar = null;

    private TextView textView = null;

    private static URL[] urls = new URL[5];

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        try {
            urls[0] = new URL("https://www.dropbox.com/s/gjq0ivomwti4vmw/1.txt?dl=0");
            urls[1] = new URL("https://www.dropbox.com/s/6lolv2ph08wt657/2.txt?dl=0");
            urls[2] = new URL("https://www.dropbox.com/s/voz5vmnjkzojb14/3.txt?dl=0");
            urls[3] = new URL("https://www.dropbox.com/s/wv6hika6v2pfakz/4.txt?dl=0");
            urls[4] = new URL("https://www.dropbox.com/s/7jhtlzfuz2j42d8/5.txt?dl=0");
        } catch (MalformedURLException e) {
            e.printStackTrace();
//            finish();
        }
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadFilesTask().execute(urls);
                } else {
                    textView.setText("Can't connect to Internet");
                }
            }
        });



    }
    private class DownloadFilesTask extends AsyncTask<URL[], Void, Integer> {
        static private final int SUCCESS = 0;
        static private final int FAIL = 1;

        @Override
        protected void onPreExecute() {
            textView.setText("0");
            progressBar.setProgress(0);
        }

        @Override
        protected Integer doInBackground(URL[]... URLArray) {
            urls = URLArray[0];

            for (URL url : urls) {
                try {
                    downloadURL(url);
                    publishProgress();
                } catch (IOException e) {
                    return FAIL;
                }
            }
            return SUCCESS;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progressBar.incrementProgressBy(1);
            textView.setText(Integer.toString(progressBar.getProgress()));
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == SUCCESS) {
                textView.setText("Download finish");
            } else {
                textView.setText("Download failed");
            }
        }

        private void downloadURL(URL url) throws IOException {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                }
            }
        }
    }
}
