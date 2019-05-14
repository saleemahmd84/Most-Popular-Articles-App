package com.example.saleem.mostpopular.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
//import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saleem.mostpopular.R;
import com.example.saleem.mostpopular.adapter.CustomAdapter;
import com.example.saleem.mostpopular.communication.GetDataService;
import com.example.saleem.mostpopular.communication.RetrofitClientInstance;
import com.example.saleem.mostpopular.models.MostPopularModel;
import com.example.saleem.mostpopular.models.Result;
import com.example.saleem.mostpopular.services.NotificationReceiver;
import com.example.saleem.mostpopular.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularHomeActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private TextView dtLastUpdated, btnCentigrade, btnFht;
    private List<Result> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);



        ScheduleRepeatedNotifications();

        initializeControls();
        initializeListeners();

        loadDataFromServer();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btnCentigrade:
            {
                loadDataFromServer();
                break;
            }

            case R.id.btnFht:
            {
                loadDataFromServer();
                break;
            }
        }

    }



    @Override
    public void onItemClick(Result item) {

        //Toast.makeText(MostPopularHomeActivity.this, "Clicked item: " + item.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MostPopularHomeActivity.this, MostPopularDetailsActivity.class);
        intent.putExtra("ARTICLE", item);

        startActivity(intent);
    }

    private void initializeControls()
    {
        dtLastUpdated = (TextView) findViewById(R.id.tvLastUpdated);
        btnCentigrade = (TextView) findViewById(R.id.btnCentigrade);
        btnFht = (TextView) findViewById(R.id.btnFht);

        btnCentigrade.setOnClickListener(this);
        btnFht.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initializeListeners()
    {
        mList = new ArrayList<>();
        adapter = new CustomAdapter(this, mList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MostPopularHomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadDataFromServer()
    {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        progressDoalog = new ProgressDialog(MostPopularHomeActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        String key = getResources().getString(R.string.api_key);

        //Call<RetrofitWeather> call = service.getAllWeatherData("292968,292932,292223,292878,291074,292672,290595", unit, "41e3090fcfb9002186fd9110f34e8077");
        Call<MostPopularModel> call = service.get7DaysData(key);
        call.enqueue(new Callback<MostPopularModel>() {
            @Override
            public void onResponse(Call<MostPopularModel> call, Response<MostPopularModel> response) {
                progressDoalog.dismiss();

                String test = "";

                if(response.body().getResults() != null)
                {
                    mList.clear();
                    mList.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();
                }

                //List<WeatherEntityModel> list = retrieveDataList(response.body().getList());


                //new StoreLocalWeather().execute(list);
            }

            @Override
            public void onFailure(Call<MostPopularModel> call, Throwable t) {
                progressDoalog.dismiss();
                //Toast.makeText(MostPopularHomeActivity.this, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(MostPopularHomeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ScheduleRepeatedNotifications()
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);
        calendar.set(Calendar.SECOND, 10);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }
}
