package com.karcompany.livetv.presenters;

import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.views.ChannelDetailsView;

import javax.inject.Inject;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Presenter implementation which handles core features (shows selected channel details).
 */

public class ChannelDetailsPresenterImpl implements ChannelDetailsPresenter {

	private ChannelDetailsView mView;
	private ChannelListPresenter mChannelListPresenter;

	@Inject
	public ChannelDetailsPresenterImpl(ChannelListPresenter channelListPresenter) {
		mChannelListPresenter = channelListPresenter;
	}

	@Override
	public void setView(ChannelDetailsView view) {
		mView = view;
	}

	@Override
	public void onStart() {
		loadChannelDetails();
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

	private void loadChannelDetails() {
		ChannelMetaData product = mChannelListPresenter.getSelectedChannel();
		if(product != null) {
			if (mView != null) {
				mView.updateChannelDetails(product);
			}
		}
	}
}
