package com.hkcyl.notepad;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        private Preference vibrate;
        private Preference notification;
        private Preference github;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);


            notification = findPreference("pre_not");
            vibrate = findPreference("pre_vib");
            github = findPreference("github");

            notification.setOnPreferenceClickListener(this);
            vibrate.setOnPreferenceClickListener(this);


        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()){
                case "github":
                    Toast.makeText(getActivity(),"github",Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    }
}
