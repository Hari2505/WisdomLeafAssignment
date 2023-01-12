package com.wisdomleafassignment.apipresenter;

import androidx.annotation.NonNull;

import com.wisdomleafassignment.interfaces.IRequestInterface;
import com.wisdomleafassignment.interfaces.IResponseInterface;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIResponsePresenter implements IRequestInterface {
    IResponseInterface iResponseInterface;

    public APIResponsePresenter(IResponseInterface iResponseInterface) {
        this.iResponseInterface = iResponseInterface;
    }

    @Override
    public void CallApi(Call call, final String reqType) {

       call.enqueue(new Callback() {
           @Override
           public void onResponse(@NonNull Call call, @NonNull Response response) {
               try {
                   iResponseInterface.onResponseSuccess(response, reqType);
               } catch (JSONException | IOException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onFailure(@NonNull Call call, @NonNull Throwable t) {
               if (t instanceof ConnectException) {
                   iResponseInterface.onResponseFailure("No Internet Connection");
               } else {
                   t.printStackTrace();
                   iResponseInterface.onResponseFailure("Response Failed");
               }
           }

       });
    }

}
