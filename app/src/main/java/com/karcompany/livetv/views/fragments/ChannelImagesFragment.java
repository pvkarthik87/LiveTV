package com.karcompany.livetv.views.fragments;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * displays channel images in fullscreen mode.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.livetv.R;
import com.karcompany.livetv.di.components.ApplicationComponent;
import com.karcompany.livetv.events.BusEvents;
import com.karcompany.livetv.events.RxBus;
import com.karcompany.livetv.logging.DefaultLogger;
import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.presenters.ChannelImagesPresenter;
import com.karcompany.livetv.views.ChannelImagesView;
import com.karcompany.livetv.views.adapters.ChannelImagesAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ChannelImagesFragment extends BaseFragment implements ChannelImagesView {

	private static final String TAG = DefaultLogger.makeLogTag(ChannelImagesFragment.class);

	@Inject
	ChannelImagesPresenter mChannelImagesPresenter;

	@Bind(R.id.channel_image_list)
	RecyclerView mChannelImagesRecyclerView;

	private ChannelImagesAdapter mAdapter;

	@Inject
	RxBus mEventBus;

	private Subscription mBusSubscription;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_channel_images, container, false);
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
		mChannelImagesPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.HORIZONTAL, false);
		mChannelImagesRecyclerView.setLayoutManager(layoutManager);
		mAdapter = new ChannelImagesAdapter();
		mAdapter.setFullScreenMode(true);
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
		mChannelImagesPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mChannelImagesPresenter.onResume();
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
			onImageClicked();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mChannelImagesPresenter.onPause();
		autoUnsubBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		mChannelImagesPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mChannelImagesPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void updateImages(ChannelMetaData channel) {
		if(channel != null) {
			mAdapter.addImages(channel.getImages());
		}
	}

	@OnClick(R.id.fullScreenBtn)
	public void onFullScreenBtnClicked() {
		onImageClicked();
	}

	private void onImageClicked() {
		getActivity().finish();
	}
}
