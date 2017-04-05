package com.karcompany.livetv.presenters;

import com.karcompany.livetv.mvputils.Presenter;
import com.karcompany.livetv.views.RecentlyViewedView;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Presenter interface.
 *
 */

public interface RecentlyViewedPresenter extends Presenter {

	void setView(RecentlyViewedView recentlyViewedView);

}
