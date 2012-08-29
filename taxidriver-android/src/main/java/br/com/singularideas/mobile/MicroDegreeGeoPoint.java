/**
 * 
 */
package br.com.singularideas.mobile;

import com.google.android.maps.GeoPoint;

/**
 * @author luis.reis
 *
 */
public class MicroDegreeGeoPoint extends GeoPoint {
	public MicroDegreeGeoPoint(double latitude, double longitude) {
        super((int) (latitude * 1E6), (int) (longitude * 1E6));
    }
}
