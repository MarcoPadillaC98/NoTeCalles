package com.example.notecalles.activity.ui.otras.otherspubs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OthersPublicacionesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OthersPublicacionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}