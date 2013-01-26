/**
 * 
 */
package br.com.singularideas.mobile;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * @author luis.reis
 * 
 */
public class GenericItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private List<OverlayItem> items = null;
	private Context ctx = null;

	public GenericItemizedOverlay(Drawable defaultMarker, Context ctx) {
		super(boundCenterBottom(defaultMarker));
		this.items = new ArrayList<OverlayItem>();
		this.ctx = ctx;
	}

	public void addOverlay(OverlayItem overlay) {
		items.add(overlay);
		populate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#createItem(int)
	 */
	@Override
	protected OverlayItem createItem(int i) {
		return items.get(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#size()
	 */
	@Override
	public int size() {
		return (items != null) ? items.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#onTap(int)
	 */
	@Override
	protected boolean onTap(int index) {
		boolean tapped = false;

		OverlayItem item = items.get(index);
		if (item != null) {
			Builder builder = new Builder(ctx);
			builder.setTitle(item.getTitle());
			builder.setMessage(item.getSnippet());
			builder.show();
			tapped = true;
		}
		
		return tapped;
	}
}
