package com.hkcyl.notepad;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().beginTransaction().replace(R.id.st_fragment_container, new SettingFragment()).commit();

    }
    public static class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        private Preference vibrate;
        private Preference notification;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);


            notification = findPreference("pre_not");
            vibrate = findPreference("pre_vib");

            notification.setOnPreferenceClickListener(this);
            vibrate.setOnPreferenceClickListener(this);


        }

        @Override
        public boolean onPreferenceClick(Preference preference) {

            return false;
        }
    }
}
