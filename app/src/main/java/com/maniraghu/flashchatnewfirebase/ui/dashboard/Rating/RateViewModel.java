package com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RateViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public RateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is rating model");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
