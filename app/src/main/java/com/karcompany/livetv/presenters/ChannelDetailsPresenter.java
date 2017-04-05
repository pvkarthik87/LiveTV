package com.karcompany.livetv.presenters;

import com.karcompany.livetv.mvputils.Presenter;
import com.karcompany.livetv.views.ChannelDetailsView;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Presenter interface which helps in showing selected channel details.
 *
 */

public interface ChannelDetailsPresenter extends Presenter {

	void setView(ChannelDetailsView channelDetailsView);

}
