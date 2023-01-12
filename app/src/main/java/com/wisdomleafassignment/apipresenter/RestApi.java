package com.wisdomleafassignment.apipresenter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface RestApi {

    @Headers("Content-Type: application/json")
    @GET(ApiConstants.List)
    Call<Object> list(@Query("page") int pageNo, @Query("limit") int limit);

   }
