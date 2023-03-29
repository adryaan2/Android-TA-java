package com.example.tankapp.ui.stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StatsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ez az oldal fogja ábrázolni a statisztikákat");
    }

    public LiveData<String> getText() {
        return mText;
    }
}