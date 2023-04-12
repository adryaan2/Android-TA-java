package com.example.tankapp.ui.tankolas_felvetel;

import android.widget.Spinner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tankapp.data.DatabaseHelper;

import java.lang.invoke.MutableCallSite;


public class TankolasFelvetelViewModel extends ViewModel {

    private final MutableLiveData<Spinner> utSpinner;
    public TankolasFelvetelViewModel(){
        utSpinner = new MutableLiveData<>();
    }

    public LiveData<Spinner> getSpinner() {
        return utSpinner;
    }
}