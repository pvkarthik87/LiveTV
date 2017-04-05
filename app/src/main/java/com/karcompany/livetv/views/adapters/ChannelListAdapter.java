package com.karcompany.livetv.views.adapters;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Recycler view adapter which displays channel list data.
 */

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.karcompany.livetv.LiveTVApplication;
import com.karcompany.livetv.R;
import com.karcompany.livetv.events.BusEvents;
import com.karcompany.livetv.events.RxBus;
import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.models.Image;
import com.karcompany.livetv.presenters.ChannelListPresenter;
import com.karcompany.livetv.utils.GlideUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListItemViewHolder> {

	private Map<String, ChannelMetaData> mChannelsDataMap;
	private List<String> mChannelIdList;

	@Inject
	RxBus mEventBus;

	@Inject
	ChannelListPresenter mChannelListPresenter;

	private boolean mIsDataLoaded;

	private int VIEW_TYPE_ITEM = 1;
	private int VIEW_TYPE_PROGRESS = 2;

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int itemPosition = (Integer) view.getTag();
			String id = mChannelIdList.get(itemPosition);
			BusEvents.ChannelClicked event =  new BusEvents.ChannelClicked();
			event.channel = mChannelsDataMap.get(id);
			mEventBus.send(event);
		}
	};

	public ChannelListAdapter() {
		LiveTVApplication.getApplicationComponent().inject(this);
		mChannelsDataMap = new LinkedHashMap<>();
		mChannelIdList = new ArrayList<>(4);
	}

	@Override
	public ChannelListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view;
		if(viewType == VIEW_TYPE_ITEM) {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_channel_row_item, parent, false);
		} else {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_progress, parent, false);
		}
		return new ChannelListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ChannelListItemViewHolder holder, int position) {
		if(getItemViewType(position) == VIEW_TYPE_ITEM) {
			holder.titleTxtView.setText("");
			Glide.clear(holder.imageImgView);
			holder.imageImgView.setImageDrawable(null);
			if (position < mChannelIdList.size()) {
				ChannelMetaData channel = mChannelsDataMap.get(mChannelIdList.get(position));
				if (channel != null) {
					if (!TextUtils.isEmpty(channel.getTitle())) {
						holder.titleTxtView.setText(channel.getTitle());
					}
					List<Image> channelImages = channel.getImages();
					if(channelImages != null && channelImages.size() > 0) {
						GlideUtils.loadImage(holder.itemView.getContext(), channelImages.get(0).getUrl(), holder.imageImgView);
					}
					holder.itemView.setTag(position);
					holder.itemView.setOnClickListener(mOnClickListener);
				}
			}
		}
	}

	@Override
	public void onViewRecycled(ChannelListItemViewHolder holder) {
		if(holder.imageImgView != null) {
			Glide.clear(holder.imageImgView);
			holder.imageImgView.setImageDrawable(null);
		}
		super.onViewRecycled(holder);
	}

	@Override
	public int getItemCount() {
		if(mIsDataLoaded) {
			return mChannelIdList.size();
		} else {
			return mChannelIdList.size() + 1;
		}
	}

	public void clearData() {
		mChannelIdList.clear();
		mChannelsDataMap.clear();
	}

	public ArrayList<ChannelMetaData> getImageList() {
		ArrayList<ChannelMetaData> imageList = new ArrayList<>();
		Iterator<Map.Entry<String, ChannelMetaData>> iterator = mChannelsDataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, ChannelMetaData> entry = iterator.next();
			imageList.add(entry.getValue());
		}
		return imageList;
	}

	public void addData(ChannelMetaData[] response) {
		if(response != null) {
			for (ChannelMetaData image : response) {
				mChannelsDataMap.put(image.getId(), image);
				mChannelIdList.add(image.getId());
			}
			mIsDataLoaded = true;
			notifyDataSetChanged();
		}
	}

	public void addData(List<ChannelMetaData> productList) {
		clearData();
		if (productList != null) {
			for (ChannelMetaData image : productList) {
				mChannelsDataMap.put(image.getId(), image);
				mChannelIdList.add(image.getId());
			}
			mIsDataLoaded = true;
			notifyDataSetChanged();
		}
	}

	public void loadData() {
		if(!mIsDataLoaded) {
			mChannelListPresenter.loadChannels();
		}
	}

	@Override
	public int getItemViewType(int position) {
		if(!mIsDataLoaded && position == mChannelIdList.size()){
			return VIEW_TYPE_PROGRESS;
		}
		return VIEW_TYPE_ITEM;
	}

	public boolean isLoadingPos(int position) {
		return getItemViewType(position) == VIEW_TYPE_PROGRESS;
	}
}
