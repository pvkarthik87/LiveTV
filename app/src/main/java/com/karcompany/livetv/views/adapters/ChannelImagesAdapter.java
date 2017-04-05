package com.karcompany.livetv.views.adapters;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Recycler view adapter which displays channel images.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.karcompany.livetv.LiveTVApplication;
import com.karcompany.livetv.R;
import com.karcompany.livetv.events.BusEvents;
import com.karcompany.livetv.events.RxBus;
import com.karcompany.livetv.models.Image;
import com.karcompany.livetv.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChannelImagesAdapter extends RecyclerView.Adapter<ChannelImageListItemViewHolder> {

	private List<Image> mImageList;
	private boolean mIsFullScreen;

	@Inject
	RxBus mEventBus;

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int itemPosition = (Integer) view.getTag();
			BusEvents.ChannelImageClickedEvent event = new BusEvents.ChannelImageClickedEvent();
			event.imageNo = itemPosition;
			mEventBus.send(event);
		}
	};

	public ChannelImagesAdapter() {
		LiveTVApplication.getApplicationComponent().inject(this);
		mImageList = new ArrayList<>(4);
	}

	@Override
	public ChannelImageListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		if(!mIsFullScreen) {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_channel_image_row_item, parent, false);
		} else {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_channel_image_row_item_fullscreen, parent, false);
		}
		return new ChannelImageListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ChannelImageListItemViewHolder holder, int position) {
		Glide.clear(holder.channelImgView);
		holder.channelImgView.setImageDrawable(null);
		if(position < mImageList.size()) {
			Image cImage = mImageList.get(position);
			if(cImage != null) {
				GlideUtils.loadImage(holder.itemView.getContext(), cImage.getUrl(), holder.channelImgView);
			}
			holder.itemView.setTag(position);
			holder.itemView.setOnClickListener(mOnClickListener);
		}
	}

	@Override
	public void onViewRecycled(ChannelImageListItemViewHolder holder) {
		Glide.clear(holder.channelImgView);
		holder.channelImgView.setImageDrawable(null);
		super.onViewRecycled(holder);
	}

	@Override
	public int getItemCount() {
		return mImageList.size();
	}

	public void addImages(List<Image> imageList) {
		clearData();
		if(imageList != null) {
			mImageList.addAll(imageList);
		}
		notifyDataSetChanged();
	}

	public void clearData() {
		mImageList.clear();
	}

	public void setFullScreenMode(boolean mode) {
		mIsFullScreen = mode;
	}
}
