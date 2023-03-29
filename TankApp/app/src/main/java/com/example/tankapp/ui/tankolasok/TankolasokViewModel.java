package com.example.tankapp.ui.tankolasok;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TankolasokViewModel extends ViewModel {

    private final MutableLiveData<TestModel> mModel;

    public TankolasokViewModel() {
        mModel = new MutableLiveData<>();
        //mText.setValue("Ez az oldal fogja ábrázolni a tankolásokat");
    }


    public LiveData<TestModel> getItem() {
        return mModel;
    }
}