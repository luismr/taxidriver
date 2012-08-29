/**
 * 
 */
package br.com.singularideas.mobile.taxidriver;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import br.com.singularideas.mobile.UIHelper;

/**
 * @author luis.reis
 *
 */
public class SettingsActivity extends PreferenceActivity {
    public static final String DEFAULT_CITY = "default";
	public static final String DEFAULT_VERSION = "default";

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
    
    /**
     * This can only invoked by the user or the app starting the activity by
     * navigating to the activity so the HOME key was not pressed.
     */
    public void onStart() {
        super.onStart();

        UIHelper.homeKeyPressed = false;
    }

    /**
     * This can only invoked by the user or the app finishing the activity
     * by navigating from the activity so the HOME key was not pressed.
     */
    public void finish() {
        UIHelper.homeKeyPressed = false;
        super.finish();
    }

    /**
     * Check if the HOME key was pressed. If the HOME key was pressed then
     * the app will be killed either safely or quickly. Otherwise the user
     * or the app is navigating away from the activity so assume that the
     * HOME key will be pressed next unless a navigation event by the user
     * or the app occurs.
     */
    public void onStop() {
        super.onStop();

        UIHelper.checkHomeKeyPressed(true);
    }

    /**
     * Disable the SEARCH key.
     */
    public boolean onSearchRequested() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save:
			save();
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}

	@Override
	public void onBackPressed() {
		save();
	}
	
	/**
	 * Save Settings
	 */
	private void save() {
		Toast.makeText(SettingsActivity.this, R.string.msg_pref_saved, Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this, WhereAmIActivity.class));
		finish();
	}

}
