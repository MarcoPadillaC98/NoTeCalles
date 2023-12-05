package com.example.notecalles.activity.ui.otras.otherspubs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MisPublicacionesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MisPublicacionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}