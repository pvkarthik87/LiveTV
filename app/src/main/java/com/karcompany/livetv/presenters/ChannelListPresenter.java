package com.karcompany.livetv.presenters;

import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.mvputils.Presenter;
import com.karcompany.livetv.views.ChannelListView;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Presenter interface which helps in getting channels from server.
 *
 */

public interface ChannelListPresenter extends Presenter {

	void setView(ChannelListView channelListView);

	void loadChannels();

	boolean isLoading();

	ChannelMetaData getSelectedChannel();

	void setSelectedChannel(ChannelMetaData channel);

}
