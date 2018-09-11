package com.pinterest.demo.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.pinterest.demo.controllers.UserController;
import com.pinterest.demo.models.User;
import com.pinterest.demo.models.UserDetail;

import java.util.List;

/**
 * Created by Pravesh Sharma on 31-08-2018.
 */

public class UserViewModel extends AndroidViewModel {

    private LiveData<List<UserDetail>> mListLiveData;
    private UserController mUserController;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mUserController = new UserController(application);
        mListLiveData = mUserController.getUserLiveData();

    }


    public LiveData<List<UserDetail>> getAllUsers() {
        return mListLiveData;
    }

    public UserDetail getUserProfileData(int pos) {
        return mListLiveData.getValue().get(pos);
    }

    public void retry(){
        mUserController.retry();
    }

}
