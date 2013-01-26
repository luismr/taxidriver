package br.com.singularideas.mobile.taxidriver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import br.com.singularideas.mobile.AbstractActivity;

public class SplashActivity extends AbstractActivity implements Runnable {

    private static final long DEFAULT_DELAY = 3000;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_splash_screen);
        
        Handler h = new Handler();
        h.postDelayed(this, DEFAULT_DELAY);
    }

	@Override
	public void run() {
		startActivity(new Intent(this, WhereAmIActivity.class));
		finish();
	}
}
