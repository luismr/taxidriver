/**
 * 
 */
package br.com.singularideas.mobile;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * @author luis.reis
 *
 */
public abstract class AbstractActivity extends Activity {
    /**
     * Check if the app was just launched. If the app was just launched then
     * assume that the HOME key will be pressed next unless a navigation
     * event by the user or the app occurs. Otherwise the user or the app
     * navigated to this activity so the HOME key was not pressed.
     */
	public void onStart() {
        super.onStart();

        UIHelper.checkJustLaunced();
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
     * the app will be killed. Otherwise the user or the app is navigating
     * away from this activity so assume that the HOME key will be pressed
     * next unless a navigation event by the user or the app occurs.
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
    
    /**
     * Return Application Version
     * @return
     */
    public String getAppVersion() {
		String appVersion = null;
		
		try {
			appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(this.getClass().getName(), e.getMessage());
		}

		return appVersion;
    }
}
