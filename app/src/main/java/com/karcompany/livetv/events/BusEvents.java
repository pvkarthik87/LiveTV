package com.karcompany.livetv.events;

import com.karcompany.livetv.models.ChannelMetaData;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Bus Events
 */

public class BusEvents {

	public static class ChannelClicked {
		public ChannelMetaData channel;
	}

	public static class ChannelImageClickedEvent {
		public int imageNo;
	}

}
