/**
 * 
 */
package br.com.singularideas.mobile.taxidriver;

import android.os.Bundle;
import android.preference.PreferenceActivity;
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

}
