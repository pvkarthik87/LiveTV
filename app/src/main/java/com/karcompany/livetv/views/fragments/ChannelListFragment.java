package com.karcompany.livetv.views.fragments;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * channel list fragment which displays server data in a recycler view.
 */

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.karcompany.livetv.R;
import com.karcompany.livetv.di.components.ApplicationComponent;
import com.karcompany.livetv.events.BusEvents;
import com.karcompany.livetv.events.RxBus;
import com.karcompany.livetv.logging.DefaultLogger;
import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.presenters.ChannelListPresenter;
import com.karcompany.livetv.views.ChannelListView;
import com.karcompany.livetv.views.activities.ChannelDetailsActivity;
import com.karcompany.livetv.views.adapters.ChannelListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ChannelListFragment extends BaseFragment implements ChannelListView {

	private static final String TAG = DefaultLogger.makeLogTag(ChannelListFragment.class);

	private static final String CURRENT_CHANNEL_LIST = "CURRENT_CHANNEL_LIST";

	@Bind(R.id.channel_list)
	RecyclerView mChannelsRecyclerView;

	@Inject
	ChannelListPresenter mChannelListPresenter;

	private ChannelListAdapter mAdapter;

	@Inject
	RxBus mEventBus;

	private Subscription mBusSubscription;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_channel_list, container, false);
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
		if (savedInstanceState != null) {
			if(savedInstanceState.containsKey(CURRENT_CHANNEL_LIST)) {
				ArrayList<ChannelMetaData> imageList = savedInstanceState.getParcelableArrayList(CURRENT_CHANNEL_LIST);
				mAdapter.addData(imageList);
			}
		}
	}

	private void setUpPresenter() {
		mChannelListPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		WindowManager w = getActivity().getWindowManager();

		Point size = new Point();
		w.getDefaultDisplay().getSize(size);
		int measuredWidth = size.x;
		float channelItemWidth = getResources().getDimension(R.dimen.card_width);
		int channelsPerRow = measuredWidth / (int) (channelItemWidth);
		final GridLayoutManager layoutManager = new GridLayoutManager(
				getActivity(), channelsPerRow);
		mChannelsRecyclerView.setLayoutManager(layoutManager);
		layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				return (mAdapter.isLoadingPos(position)) ? layoutManager.getSpanCount() : 1;
			}
		});
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
		mChannelListPresenter.onStart();
		if (!mChannelListPresenter.isLoading()) {
			//End of the items
			mAdapter.loadData();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mChannelListPresenter.onResume();
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
		mChannelListPresenter.onPause();
		autoUnsubBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		mChannelListPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mChannelListPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mChannelsRecyclerView.setAdapter(null);
		super.onDestroyView();
	}

	@Override
	public void onDataReceived(ChannelMetaData[] response) {
		mAdapter.addData(response);
	}

	@Override
	public void onFailure(String errorMsg) {

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(CURRENT_CHANNEL_LIST, mAdapter.getImageList());
	}

	private void onChannelClicked(ChannelMetaData channel) {
		mChannelListPresenter.setSelectedChannel(channel);
		Intent intent = new Intent(getActivity(), ChannelDetailsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
}
