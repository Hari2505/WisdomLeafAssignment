package com.wisdomleafassignment.interfaces;


import retrofit2.Call;

public interface IRequestInterface {

    void CallApi(
            Call call,
            String reqType
            );

}
