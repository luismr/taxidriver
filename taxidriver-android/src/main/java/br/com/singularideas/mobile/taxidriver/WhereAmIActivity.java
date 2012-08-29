package br.com.singularideas.mobile.taxidriver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ZoomButtonsController;
import android.widget.ZoomButtonsController.OnZoomListener;
import br.com.singularideas.mobile.AbstractMapActivity;
import br.com.singularideas.mobile.MicroDegreeGeoPoint;
import br.com.singularideas.mobile.UIHelper;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class WhereAmIActivity extends AbstractMapActivity {
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 25; 
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 10 * 1000; 
	
	protected ImageButton retrieveLocationButton;

	private SharedPreferences preferences;

	/*
	 * (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_whereami_screen);

		preferences = PreferenceManager.getDefaultSharedPreferences(WhereAmIActivity.this);
		debug = preferences.getBoolean("debug", false);
		
		checkSettings();
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whereami, menu);
        UIHelper.homeKeyPressed = true;
        return true;
    }

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			finish();
			return true;
		case R.id.menu_exit:
			UIHelper.killApp(false);
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}

	/**
	 * Changelog verification
	 */
	private void checkChangelog() {
		try {
			String version = preferences.getString("version", SettingsActivity.DEFAULT_VERSION);
			String versionApp = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			
			if (!version.trim().equalsIgnoreCase(versionApp.trim())) {
				Editor editor = preferences.edit();
				editor.putString("version", versionApp.trim());
				editor.commit();
				
				startActivity(new Intent(this, ChangelogActivity.class));
				finish();
			}
		} catch (NameNotFoundException e) {
			Log.e("version", e.getMessage());
		}
	}

	/**
	 * Check Settings
	 */
	private void checkSettings() {
		String city = preferences.getString("city", SettingsActivity.DEFAULT_CITY);
		if (SettingsActivity.DEFAULT_CITY.equals(city)) {
			startActivity(new Intent(this, SettingsActivity.class));
			finish();
		} else {
			configLocationManager();
			configInterfaceListeners();
			configMapView();
			checkChangelog();
		}
	}

	/**
	 * Config Map View
	 */
	private void configMapView() {
		map = (MapView) findViewById(R.id.map_view);
		map.setBuiltInZoomControls(true);
		
		controller = map.getController();
		overlays = map.getOverlays();
		projection = map.getProjection();
		
		mlo = new MyLocationOverlay(this, map);
		mlo.enableCompass();
		mlo.enableMyLocation();
		
		overlays.add(mlo);
		
		ZoomButtonsController zoomButtonsController = map.getZoomButtonsController();
		zoomButtonsController.setOnZoomListener(new ZoomListener());
		
		centerOnMyLocation();
	}

	/**
	 * Config Interface Listeners for Usability
	 */
	private void configInterfaceListeners() {
		retrieveLocationButton = (ImageButton) findViewById(R.id.btn_retrieve_location);
		retrieveLocationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				configLocationManager();
				centerOnMyLocation();
			}
			
		});
	}

	/**
	 * Config Location Manager based on Preferences
	 */
	private void configLocationManager() {
		localization = preferences.getBoolean("localization", false);
		gps = preferences.getBoolean("gps", false);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		if (localization) {
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				provider = (gps) ? LocationManager.GPS_PROVIDER : LocationManager.NETWORK_PROVIDER;
			} else {
				provider = LocationManager.NETWORK_PROVIDER;
			}
			
			locationManager.requestLocationUpdates(provider, 
					MINIMUM_TIME_BETWEEN_UPDATES, 
					MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, 
					new WhereAmILocationListener());
		}
		
	}
	
	/**
	 * WhereAmILocationListener
	 * @author luis.reis
	 */
	private class WhereAmILocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			debugger(R.string.provider_msg_provider_location_changed, location.getLatitude(), location.getLongitude());
			centerOnPoint(new MicroDegreeGeoPoint(location.getLatitude(), location.getLongitude()), zoom);
		}

		@Override
		public void onProviderDisabled(String provider) {
			debugger(R.string.provider_msg_provider_disabled, provider);
			configLocationManager();
		}

		@Override
		public void onProviderEnabled(String provider) {
			debugger(R.string.provider_msg_provider_enabled, provider);
			configLocationManager();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			String providerStatus = null;
			
			switch (status) {
			case LocationProvider.AVAILABLE:
				providerStatus = getString(R.string.provider_status_available);
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				providerStatus = getString(R.string.provider_status_unavailable);
				break;
			case LocationProvider.OUT_OF_SERVICE:
				providerStatus = getString(R.string.provider_status_out_of_service);
				break;
			default:
				break;
			}
			
			debugger(R.string.provider_msg_change_status, provider, providerStatus);
		}
	}
	
	/**
	 * ZoomListener 
	 * @author luis.reis
	 */
	private class ZoomListener implements OnZoomListener {

		@Override
		public void onVisibilityChanged(boolean visible) {
		}

		@Override
		public void onZoom(boolean zoomIn) {
			if (zoomIn) {
				zoom = map.getZoomLevel() + 1;
			} else {
				if (zoom > 1) {
					zoom = map.getZoomLevel() - 1;
				}
			}
			
			controller.setZoom(zoom);
		}
		
	}

}
