package com.example.caissev4.model.ui.statistiques;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatistiquesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StatistiquesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}