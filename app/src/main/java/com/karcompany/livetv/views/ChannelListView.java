package com.karcompany.livetv.views;

import com.karcompany.livetv.models.ChannelMetaData;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface ChannelListView {

	void onDataReceived(ChannelMetaData[] response);

	void onFailure(String errorMsg);

}
