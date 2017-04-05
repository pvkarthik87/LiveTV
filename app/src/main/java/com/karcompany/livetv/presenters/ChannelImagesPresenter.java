package com.karcompany.livetv.presenters;

import com.karcompany.livetv.mvputils.Presenter;
import com.karcompany.livetv.views.ChannelImagesView;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Presenter interface.
 *
 */

public interface ChannelImagesPresenter extends Presenter {

	void setView(ChannelImagesView channelImagesView);

}
