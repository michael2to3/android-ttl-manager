package com.github.ttl.manager.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import com.github.ttl.manager.R;

import com.github.ttl.manager.databinding.FragmentSettingsBinding;

import java.util.prefs.Preferences;

public class SettingsFragment extends PreferenceFragmentCompat {

    private FragmentSettingsBinding binding;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}