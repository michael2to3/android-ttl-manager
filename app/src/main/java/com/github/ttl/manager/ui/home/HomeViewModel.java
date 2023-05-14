package com.github.ttl.manager.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.ttl.manager.actions.TTLModifier;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() throws Exception{
        mText = new MutableLiveData<>();
        mText.setValue("Your current TTL is " + new TTLModifier().getTTL());
    }

    public LiveData<String> getText() {
        return mText;
    }
}