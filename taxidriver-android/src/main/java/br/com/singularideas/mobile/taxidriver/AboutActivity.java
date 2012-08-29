/**
 * 
 */
package br.com.singularideas.mobile.taxidriver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.singularideas.mobile.AbstractActivity;
import br.com.singularideas.mobile.taxidriver.R.id;

/**
 * @author luis.reis
 *
 */
public class AboutActivity extends AbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_screen);
		
		Log.d(this.getClass().getName(), "onCreate");
		
		populateFields();
		configureListeners();
	}

	private void populateFields() {
		Log.d(this.getClass().getName(), "onCreate - start");
		
		String versionPattern = getString(R.string.about_version).replace("#", "%").replace("/", "'");
		String versionApp = String.format(versionPattern, getAppVersion());

		TextView version = (TextView) findViewById(id.labelVersion);
		version.setText(versionApp);
		
		Log.d(this.getClass().getName(), "onCreate - end");
	}

	private void configureListeners() {
		Log.d(this.getClass().getName(), "configureListeners - start");
		
		ImageView developedBy = (ImageView) findViewById(R.id.img_developed_by);
		developedBy.setOnClickListener(new AboutOnClickListener());
		
		ImageView alsoVisit1 = (ImageView) findViewById(R.id.img_also_visit_1);
		alsoVisit1.setOnClickListener(new AboutOnClickListener());

		ImageView alsoVisit2 = (ImageView) findViewById(R.id.img_also_visit_2);
		alsoVisit2.setOnClickListener(new AboutOnClickListener());
		
		ImageView alsoVisit3 = (ImageView) findViewById(R.id.img_also_visit_3);
		alsoVisit3.setOnClickListener(new AboutOnClickListener());
		
		Log.d(this.getClass().getName(), "configureListeners - end");
	}

	private class AboutOnClickListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			String url = null;
			
			switch (v.getId()) {
			case R.id.img_developed_by:
				url = getString(R.string.about_url_developed_by);
				break;
			case R.id.img_also_visit_1:
				url = getString(R.string.about_url_also_visit_1);
				break;
			case R.id.img_also_visit_2:
				url = getString(R.string.about_url_also_visit_2);
				break;
			case R.id.img_also_visit_3:
				url = getString(R.string.about_url_also_visit_3);
				break;

			default:
				url = "http://www.google.com";
				break;
			}
			
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		}
		
	}
	
}
