package com.karcompany.livetv.views.activities;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Shows channel images in fullscreen mode.
 */

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.karcompany.livetv.R;
import com.karcompany.livetv.di.HasComponent;
import com.karcompany.livetv.di.components.ApplicationComponent;

public class ImageFullScreenActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_full_screen);
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}
}
