package com.example.saleem.mostpopular.communication;

import com.example.saleem.mostpopular.models.MostPopularModel;
import com.example.saleem.mostpopular.utils.ApiTags;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET(ApiTags.get1DayData)
    Call<MostPopularModel> get1DayData(@Query("api-key") String key);

    @GET(ApiTags.get7DaysData)
    Call<MostPopularModel> get7DaysData(@Query("api-key") String key);

    @GET(ApiTags.get30DaysData)
    Call<MostPopularModel> get30DaysData(@Query("api-key") String key);
}