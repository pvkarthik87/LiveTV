package com.karcompany.livetv.views.fragments;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Recently viewed channels fragment in recycler view.
 */

import android.content.Intent;
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
import com.karcompany.livetv.presenters.ChannelListPresenter;
import com.karcompany.livetv.presenters.RecentlyViewedPresenter;
import com.karcompany.livetv.views.RecentlyViewedView;
import com.karcompany.livetv.views.activities.ChannelDetailsActivity;
import com.karcompany.livetv.views.adapters.ChannelListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RecentlyViewedFragment extends BaseFragment implements RecentlyViewedView {

	private static final String TAG = DefaultLogger.makeLogTag(RecentlyViewedFragment.class);

	@Bind(R.id.channel_list)
	RecyclerView mChannelsRecyclerView;

	@Inject
	RecentlyViewedPresenter mRecentlyViewedPresenter;

	@Inject
	ChannelListPresenter mChannelListPresenter;

	private ChannelListAdapter mAdapter;

	@Inject
	RxBus mEventBus;

	private Subscription mBusSubscription;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_recently_viewed, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		setUpPresenter();
		mAdapter = new ChannelListAdapter();
		setUpRecyclerView();
	}

	private void setUpPresenter() {
		mRecentlyViewedPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false);
		mChannelsRecyclerView.setLayoutManager(layoutManager);
		mChannelsRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mRecentlyViewedPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mRecentlyViewedPresenter.onResume();
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
		if(o instanceof BusEvents.ChannelClicked) {
			BusEvents.ChannelClicked event = (BusEvents.ChannelClicked)o;
			onChannelClicked(event.channel);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mRecentlyViewedPresenter.onPause();
		autoUnsubBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		mRecentlyViewedPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mRecentlyViewedPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mChannelsRecyclerView.setAdapter(null);
		super.onDestroyView();
	}

	private void onChannelClicked(ChannelMetaData channel) {
		mChannelListPresenter.setSelectedChannel(channel);
		Intent intent = new Intent(getActivity(), ChannelDetailsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@Override
	public void updateRecentItems(ChannelMetaData[] results) {
		List<ChannelMetaData> channelList = new ArrayList<>();
		if(results != null) {
			channelList.addAll(Arrays.asList(results));
		}
		mAdapter.addData(channelList);
	}
}
