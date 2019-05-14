package com.example.saleem.mostpopular.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.saleem.mostpopular.R;
import com.example.saleem.mostpopular.models.Result;

public class MostPopularDetailsActivity extends AppCompatActivity {

    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initializeControls();

        if(getIntent().getExtras().getParcelable("ARTICLE") != null) {
            Result res = getIntent().getExtras().getParcelable("ARTICLE");

            String title = res.getTitle().length() > 35 ? res.getTitle().substring(0, 34) + "..." : res.getTitle();


            getSupportActionBar().setTitle(title);

            web.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return false;
                }
            });
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl(res.getUrl());
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                //NavUtils.navigateUpFromSameTask(this);
                setResult(Activity.RESULT_OK);
                finish();
                super.onBackPressed();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
        super.onBackPressed();
    }

    private void initializeControls()
    {
        web = (WebView) findViewById(R.id.web);
    }
}
