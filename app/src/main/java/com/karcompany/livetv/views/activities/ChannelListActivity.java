package com.karcompany.livetv.views.activities;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Initial launch activity which will get displayed on first launch of application.
 *
 * Displays list of channels.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.karcompany.livetv.R;
import com.karcompany.livetv.di.HasComponent;
import com.karcompany.livetv.di.components.ApplicationComponent;
import com.karcompany.livetv.logging.DefaultLogger;
import com.karcompany.livetv.presenters.ChannelListPresenter;

import javax.inject.Inject;

public class ChannelListActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	private static final String TAG = DefaultLogger.makeLogTag(ChannelListActivity.class);

	@Inject
	ChannelListPresenter mChannelListPresenter;

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_list);
		setTitle(getString(R.string.app_name));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_channel_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_recent) {
			goToRecentView();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void goToRecentView() {
		Intent intent = new Intent(this, RecentlyViewedActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
}
