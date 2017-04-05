package com.karcompany.livetv.views.adapters;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * View holder.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karcompany.livetv.R;

public class ChannelListItemViewHolder extends RecyclerView.ViewHolder {

	public TextView titleTxtView;
	public ImageView imageImgView;

	public ChannelListItemViewHolder(View itemView) {
		super(itemView);
		titleTxtView = (TextView) itemView.findViewById(R.id.channelTitle);
		imageImgView = (ImageView) itemView.findViewById(R.id.channelImage);
	}

}
