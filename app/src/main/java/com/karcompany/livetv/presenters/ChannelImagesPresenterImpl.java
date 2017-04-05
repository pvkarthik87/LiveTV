package com.karcompany.livetv.presenters;

import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.views.ChannelImagesView;

import javax.inject.Inject;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Presenter implementation which handles core features (Fullscreen images).
 */

public class ChannelImagesPresenterImpl implements ChannelImagesPresenter {

	private ChannelImagesView mView;
	private ChannelListPresenter mChannelListPresenter;

	@Inject
	public ChannelImagesPresenterImpl(ChannelListPresenter channelListPresenter) {
		mChannelListPresenter = channelListPresenter;
	}

	@Override
	public void setView(ChannelImagesView view) {
		mView = view;
	}

	@Override
	public void onStart() {
		loadImages();
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


	private void loadImages() {
		ChannelMetaData selectedChannel = mChannelListPresenter.getSelectedChannel();
		if(selectedChannel != null) {
			if(mView != null) {
				mView.updateImages(selectedChannel);
			}
		}
	}
}
