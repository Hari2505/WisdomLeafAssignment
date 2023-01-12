package com.wisdomleafassignment.screens;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.util.Log;

import com.wisdomleafassignment.apipresenter.APIResponsePresenter;
import com.wisdomleafassignment.apipresenter.ApiReqType;
import com.wisdomleafassignment.interfaces.IRequestInterface;
import com.wisdomleafassignment.interfaces.IResponseInterface;
import com.wisdomleafassignment.singelton.AppController;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Response;

public class ListPresenter implements IListPresenter, IResponseInterface {
    private final IRequestInterface iRequestInterface;
    private final IListView iListView;

    public ListPresenter(IListView iListView) {
        this.iRequestInterface = new APIResponsePresenter(this);
        this.iListView = iListView;
    }

    @Override
    public void onResponseSuccess(Response response, String reqType) throws JSONException, IOException {
        if (response != null) {
            if (reqType.equalsIgnoreCase(ApiReqType.List)) {
                Object model = response.body();
                iListView.setList(model);
            }
        }
    }

    @Override
    public void onResponseFailure(String responseError) {
        Log.i(TAG, "responseError: " + responseError);
    }

    @Override
    public void getList(int pageNo, int limit) {
        iRequestInterface.CallApi(AppController.getInstance().service.list(pageNo, limit), ApiReqType.List);

    }
}
