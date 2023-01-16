package com.example.utbprekladac.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class historyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public historyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Posledni prelozena polozka");
    }

    public LiveData<String> getText() {
        return mText;
    }
}