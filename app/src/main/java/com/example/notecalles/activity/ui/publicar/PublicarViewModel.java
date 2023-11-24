package com.example.notecalles.activity.ui.publicar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PublicarViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PublicarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}