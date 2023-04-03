package com.example.tankapp.ui.kezdo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KezdoViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public KezdoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kezdőoldal");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
