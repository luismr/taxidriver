/**
 * 
 */
package br.com.singularideas.mobile.taxidriver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import br.com.singularideas.mobile.AbstractActivity;

/**
 * @author luis.reis
 * 
 */
public class ChangelogActivity extends AbstractActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changelog_screen);
		
		Button goButton = (Button) findViewById(R.id.btn_go);
		goButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), WhereAmIActivity.class));
				finish();
			}
			
		});
		
		String changes = readTextFromResource(R.raw.changelog);
		
		WebView view = (WebView) findViewById(R.id.webview_changelog);
		view.loadData(changes, "text/html", "utf-8");

	}

	private String readTextFromResource(int resourceID) {
		InputStream raw = getResources().openRawResource(resourceID);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		int i;
		try {
			i = raw.read();
			
			while (i != -1) {
				stream.write(i);
				i = raw.read();
			}
			
			raw.close();
		} catch (IOException e) {
			Log.e(this.getClass().getCanonicalName(), e.getMessage());
		}
		
		return stream.toString();
	}

}
