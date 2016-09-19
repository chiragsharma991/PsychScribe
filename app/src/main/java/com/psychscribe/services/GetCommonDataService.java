package com.psychscribe.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.psychscribe.R;
import com.psychscribe.model.StateModel;
import com.psychscribe.patients.HomeActivity;
import com.psychscribe.prelogin.model.SignInModel;
import com.psychscribe.rest.APIRequest;
import com.psychscribe.rest.ResponseInterface;
import com.psychscribe.sqlite.StateHandler;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ubuntu on 8/3/16.
 */
public class GetCommonDataService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(AppUtils.isOnline(getApplicationContext()) && !SharedPreferenceUtil.getBoolean(Constants.KEY_IS_STATE_TAKEN, false)){
            getCommonData();
        }else{
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    private void getCommonData() {
        ResponseInterface userDataRequest = APIRequest.provideInterface();
        Call<StateModel> getUserCall = userDataRequest.getState();

        getUserCall.enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(Call<StateModel> call,
                                   Response<StateModel> response) {
                if (response.isSuccessful()) {
                    if(response.body().getSuccess()){
                        SharedPreferenceUtil.putValue(Constants.KEY_IS_STATE_TAKEN, true);
                        SharedPreferenceUtil.save();
                        StateHandler stateHandler = new StateHandler();
                        stateHandler.deleteState();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            stateHandler.addState(response.body().getData().get(i));
                        }
                        stopSelf();
                    }
                }else
                    stopSelf();
            }
            @Override
            public void onFailure(Call<StateModel> call, Throwable t) {
               stopSelf();
            }
        });
    }
}
