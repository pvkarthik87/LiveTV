package com.karcompany.livetv.views.fragments;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Displays detailed info about a channel.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karcompany.livetv.R;
import com.karcompany.livetv.di.components.ApplicationComponent;
import com.karcompany.livetv.events.BusEvents;
import com.karcompany.livetv.events.RxBus;
import com.karcompany.livetv.logging.DefaultLogger;
import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.presenters.ChannelDetailsPresenter;
import com.karcompany.livetv.views.ChannelDetailsView;
import com.karcompany.livetv.views.activities.ImageFullScreenActivity;
import com.karcompany.livetv.views.adapters.ChannelImagesAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ChannelDetailsFragment extends BaseFragment implements ChannelDetailsView {

	private static final String TAG = DefaultLogger.makeLogTag(ChannelDetailsFragment.class);

	@Inject
	ChannelDetailsPresenter mChannelDetailsPresenter;

	@Bind(R.id.channelDescription)
	TextView mChannelDescriptionView;

	@Bind(R.id.channelNo)
	TextView mChannelNo;

	@Inject
	RxBus mEventBus;

	private Subscription mBusSubscription;

	@Bind(R.id.channel_image_list)
	RecyclerView mChannelImagesRecyclerView;

	private ChannelImagesAdapter mAdapter;

	private LinearLayoutManager mLayoutManager;

	private ChannelMetaData mCurrentChannel;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_channel_details, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		setUpPresenter();
		setUpRecyclerView();
	}

	private void setUpPresenter() {
		mChannelDetailsPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		mLayoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.HORIZONTAL, false);
		mChannelImagesRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new ChannelImagesAdapter();
		mChannelImagesRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mChannelDetailsPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mChannelDetailsPresenter.onResume();
		autoUnsubBus();
		subscribeBus();
	}

	private void subscribeBus() {
		mBusSubscription = mEventBus.toObserverable()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						new Action1<Object>() {
							@Override
							public void call(Object o) {
								handlerBus(o);
							}
						}
				);
	}

	private void autoUnsubBus() {
		if (mBusSubscription != null && !mBusSubscription.isUnsubscribed()) {
			mBusSubscription.unsubscribe();
		}
	}

	private void handlerBus(Object o) {
		if (o instanceof BusEvents.ChannelImageClickedEvent) {
			BusEvents.ChannelImageClickedEvent event = (BusEvents.ChannelImageClickedEvent) o;
			onChannelImageClicked(event.imageNo);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mChannelDetailsPresenter.onPause();
		autoUnsubBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		mChannelDetailsPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mChannelDetailsPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mChannelImagesRecyclerView.setAdapter(null);
		super.onDestroyView();
	}

	@Override
	public void updateChannelDetails(ChannelMetaData channel) {
		if(channel != null) {
			mCurrentChannel = channel;
			if(!TextUtils.isEmpty(channel.getDescription())) {
				mChannelDescriptionView.setText(channel.getDescription());
			}
			if(!TextUtils.isEmpty(channel.getChannelNumber())) {
				mChannelNo.setText(channel.getChannelNumber());
			}
			mAdapter.addImages(channel.getImages());
		}
	}

	private void onChannelImageClicked(int imageNo) {
		Intent intent = new Intent(getActivity(), ImageFullScreenActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@OnClick(R.id.fullScreenBtn)
	public void onFullScreenBtnClicked() {
		if(mCurrentChannel != null) {
			int clickedImagePos = mLayoutManager.findFirstVisibleItemPosition();
			BusEvents.ChannelImageClickedEvent event =  new BusEvents.ChannelImageClickedEvent();
			event.imageNo = clickedImagePos;
			mEventBus.send(event);
		}
	}

	@OnClick(R.id.watchNowBar)
	public void onWatchClicked() {

	}
}
