package com.karcompany.livetv.views.activities;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Channel details activity which displays detailed view of channel.
 */

import android.os.Bundle;

import com.karcompany.livetv.R;
import com.karcompany.livetv.di.HasComponent;
import com.karcompany.livetv.di.components.ApplicationComponent;
import com.karcompany.livetv.logging.DefaultLogger;
import com.karcompany.livetv.presenters.ChannelListPresenter;

import javax.inject.Inject;

public class ChannelDetailsActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	private static final String TAG = DefaultLogger.makeLogTag(ChannelDetailsActivity.class);

	@Inject
	ChannelListPresenter mChannelListPresenter;

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_details);
		setTitle(mChannelListPresenter.getSelectedChannel().getTitle());
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}

}
