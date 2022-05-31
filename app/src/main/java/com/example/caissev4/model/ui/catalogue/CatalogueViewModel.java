package com.example.caissev4.model.ui.catalogue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatalogueViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CatalogueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}