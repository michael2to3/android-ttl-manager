package com.github.ttl.manager.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import com.github.ttl.manager.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}