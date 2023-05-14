package com.github.ttl.manager.ui.home;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.ttl.manager.actions.TTLModifier;
import com.github.ttl.manager.exceptions.RootAccessException;
import com.github.ttl.manager.exceptions.TTLOperationException;
import com.github.ttl.manager.exceptions.TTLValueException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeViewModel extends ViewModel {
    private static final Logger LOGGER = Logger.getLogger(HomeViewModel.class.getName());
    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        try {
            mText.setValue("Your current TTL is " + new TTLModifier().getTTL());
        } catch(RootAccessException | TTLOperationException | TTLValueException e) {
            LOGGER.log(Level.SEVERE, "Home view model cannot get TTL", e);
            mText.setValue("An error occurred while receiving TTL:" + e.getMessage());
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
}