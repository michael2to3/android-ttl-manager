package com.github.ttl.manager.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.github.ttl.manager.actions.TTLModifier;
import com.github.ttl.manager.exceptions.RootAccessException;
import com.github.ttl.manager.exceptions.TTLOperationException;
import com.github.ttl.manager.exceptions.TTLValueException;
import com.github.ttl.manager.interfaces.ITTLModifier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeViewModel extends ViewModel {

  private static final Logger LOGGER = Logger.getLogger(HomeViewModel.class.getName());
  private final MutableLiveData<String> mText;
  private final ITTLModifier ttlModifier = new TTLModifier();
  private static int ttlDefault = -1;

  public HomeViewModel() {
    mText = new MutableLiveData<>();
    try {
      int ttl = new TTLModifier().getTTL();
      mText.setValue("Your current TTL is " + ttl);
    } catch (RootAccessException | TTLOperationException | TTLValueException e) {
      LOGGER.log(Level.SEVERE, "Home view model cannot get TTL", e);
      mText.setValue("An error occurred while receiving TTL:" + e.getMessage());
    }
  }

  public LiveData<String> getText() {
    return mText;
  }

  public void onApply(int ttl) {
    storeInitialTTLValueIfNeeded();
    try {
      ttlModifier.setTTL(ttl);
    } catch (RootAccessException | TTLOperationException | TTLValueException e) {
      LOGGER.log(Level.SEVERE, "Error change ttl", e);
    }
    updateTTLText();
  }

  public void onRestore() {
    int value = ttlDefault == -1 ? 64 : ttlDefault;
    try {
      ttlModifier.setTTL(value);
    } catch (RootAccessException | TTLOperationException | TTLValueException e) {
      LOGGER.log(Level.SEVERE, "Error change default value ttl", e);
    }
    updateTTLText();
  }

  public void updateTTLText() {
    try {
      int ttl = ttlModifier.getTTL();
      mText.setValue("Your current TTL is " + ttl);
    } catch (RootAccessException | TTLOperationException | TTLValueException e) {
      LOGGER.log(Level.SEVERE, "Home view model cannot get TTL", e);
      mText.setValue("An error occurred while receiving TTL:" + e.getMessage());
    }
  }

  private void storeInitialTTLValueIfNeeded() {
    if (ttlDefault == -1) {
      try {
        ttlDefault = new TTLModifier().getTTL();
      } catch (RootAccessException | TTLOperationException | TTLValueException e) {
        LOGGER.log(Level.SEVERE, "Error save default ttl", e.getMessage());
      }
    }
  }
}
