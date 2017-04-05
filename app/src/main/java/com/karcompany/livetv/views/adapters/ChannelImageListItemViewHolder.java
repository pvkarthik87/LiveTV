package com.karcompany.livetv.views.adapters;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * View holder.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.karcompany.livetv.R;

public class ChannelImageListItemViewHolder extends RecyclerView.ViewHolder {

	public ImageView channelImgView;

	public ChannelImageListItemViewHolder(View itemView) {
		super(itemView);
		channelImgView = (ImageView) itemView.findViewById(R.id.channelImage);
	}

}
