/**
 * 
 */
package br.com.singularideas.mobile;

import java.util.List;

import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;
import br.com.singularideas.mobile.taxidriver.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/**
 * @author luis.reis
 *
 */
public abstract class AbstractMapActivity extends MapActivity {
	protected final static int DEFAULT_ZOOM = 14;
	protected final static int DEFAULT_ZOOM_GPS = 18;
	
	protected boolean debug = false;
	
	protected MapView map;
	protected MapController controller;
	protected MyLocationOverlay mlo;
	protected List<Overlay> overlays;
	protected Projection projection;
	protected int zoom = -1;
	
	protected LocationManager locationManager;
	protected boolean localization = false;
	protected boolean gps = false;
	protected String provider = LocationManager.NETWORK_PROVIDER;


	

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
	 * Center Map to MyLocationOverlay
	 */
	protected void centerOnMyLocation() {
		debugger(R.string.provider_msg_selected, provider);

		if (zoom == -1 || zoom == DEFAULT_ZOOM || zoom == DEFAULT_ZOOM_GPS) {
			if (LocationManager.GPS_PROVIDER.equals(provider)) {
				zoom = DEFAULT_ZOOM_GPS;
			} else {
				zoom = DEFAULT_ZOOM;
			}
			
			debugger(R.string.provider_msg_default_zoom, zoom);
		}
		
		centerOnPoint(mlo.getMyLocation(), zoom);
	}
	
	/**
	 * Center Map on Location given
	 */
	protected void centerOnLocation(Location location) {
		centerOnLocation(location, DEFAULT_ZOOM);
	}
	
	/**
	 * Center Map on Location given
	 */
	protected void centerOnLocation(Location location, int zoom) {
		centerOnPoint(new MicroDegreeGeoPoint(location.getLatitude(), location.getLongitude()), zoom);
		this.zoom = zoom;
	}
	
	/**
	 * Center Map on GeoPoint given
	 */
	protected void centerOnPoint(GeoPoint point) {
		centerOnPoint(point, DEFAULT_ZOOM);
	}

	/**
	 * Center Map on GeoPoint given
	 */
	protected void centerOnPoint(GeoPoint point, int zoom) {
		if (point != null) {
			controller.animateTo(point);
			controller.setZoom(zoom);
		}
		
		this.zoom = zoom;
	}

	/**
	 * Debug Messages
	 * @param patternStringId
	 * @param args
	 */
	protected void debugger(int patternStringId, Object ... args) {
		if (debug) {
			String pattern = getString(patternStringId).replace("#", "%").replace("/", "'");
			String message = String.format(pattern, args);
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Return App Version
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
