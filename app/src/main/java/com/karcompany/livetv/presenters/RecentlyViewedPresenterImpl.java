package com.karcompany.livetv.presenters;

import com.karcompany.livetv.config.AppConfig;
import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.views.RecentlyViewedView;

import javax.inject.Inject;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Presenter implementation which handles core features (show Recently viewed channels).
 */

public class RecentlyViewedPresenterImpl implements RecentlyViewedPresenter {

	private RecentlyViewedView mView;
	private AppConfig mAppConfig;

	@Inject
	public RecentlyViewedPresenterImpl(AppConfig appConfig) {
		mAppConfig = appConfig;
	}

	@Override
	public void setView(RecentlyViewedView view) {
		mView = view;
	}

	@Override
	public void onStart() {
		loadRecentlyViewedChannels();
	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
		mView = null;
	}


	private void loadRecentlyViewedChannels() {
		ChannelMetaData[] recentProducts = mAppConfig.getRecentlyViewedChannels();
		if(mView != null) {
			mView.updateRecentItems(recentProducts);
		}
	}
}
