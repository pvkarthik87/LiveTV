package com.karcompany.livetv.presenters;

import com.karcompany.livetv.config.AppConfig;
import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.networking.ApiRepo;
import com.karcompany.livetv.networking.NetworkError;
import com.karcompany.livetv.views.ChannelListView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.karcompany.livetv.config.Constants.PROVIDER_YOUTUBE;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Presenter implementation which handles core features (fetches channels from server).
 */

public class ChannelListPresenterImpl implements ChannelListPresenter, ApiRepo.GetChannelsApiCallback {

	private ChannelListView mView;

	private ApiRepo mApiRepo;

	private boolean mIsLoading;
	private CompositeSubscription subscriptions;

	private ChannelMetaData mSelectedChannel;

	private AppConfig mAppConfig;

	@Inject
	public ChannelListPresenterImpl(ApiRepo apiRepo, AppConfig appConfig) {
		mApiRepo = apiRepo;
		mAppConfig = appConfig;
	}

	@Override
	public void setView(ChannelListView view) {
		mView = view;
		subscriptions = new CompositeSubscription();
	}

	@Override
	public void onStart() {

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
		if(subscriptions != null) {
			subscriptions.unsubscribe();
		}
	}

	@Override
	public void loadChannels() {
		mIsLoading = true;
		Subscription subscription = mApiRepo.getChannels(PROVIDER_YOUTUBE, this);
		subscriptions.add(subscription);
	}

	@Override
	public void onSuccess(ChannelMetaData[] response) {
		mIsLoading = false;
		if (mView != null) {
			mView.onDataReceived(response);
		}
	}

	@Override
	public void onError(NetworkError networkError) {
		mIsLoading = false;
		if (mView != null) {
			mView.onFailure(networkError.getAppErrorMessage());
		}
	}

	@Override
	public boolean isLoading() {
		return mIsLoading;
	}

	@Override
	public void setSelectedChannel(ChannelMetaData channel) {
		mSelectedChannel = channel;
		mAppConfig.addToRecent(channel);
	}

	@Override
	public ChannelMetaData getSelectedChannel() {
		return mSelectedChannel;
	}
}
