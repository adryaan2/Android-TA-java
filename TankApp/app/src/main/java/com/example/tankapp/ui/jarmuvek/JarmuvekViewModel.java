package com.example.tankapp.ui.jarmuvek;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JarmuvekViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public JarmuvekViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ez az oldal fogja ábrázolni a járműveket");
    }

    public LiveData<String> getText() {
        return mText;
    }
}