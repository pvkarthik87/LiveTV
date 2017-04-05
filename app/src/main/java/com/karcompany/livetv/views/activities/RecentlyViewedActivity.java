package com.karcompany.livetv.views.activities;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Shows recently viewed channels.
 */

import android.os.Bundle;

import com.karcompany.livetv.R;
import com.karcompany.livetv.di.HasComponent;
import com.karcompany.livetv.di.components.ApplicationComponent;
import com.karcompany.livetv.logging.DefaultLogger;

public class RecentlyViewedActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	private static final String TAG = DefaultLogger.makeLogTag(RecentlyViewedActivity.class);

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recently_viewed_products);
		setTitle(getString(R.string.view_history));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}

}
