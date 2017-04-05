package com.karcompany.livetv.di.components;

import android.content.Context;

import com.karcompany.livetv.LiveTVApplication;
import com.karcompany.livetv.di.modules.ApplicationModule;
import com.karcompany.livetv.networking.NetworkModule;
import com.karcompany.livetv.presenters.ChannelListPresenter;
import com.karcompany.livetv.views.activities.BaseActivity;
import com.karcompany.livetv.views.activities.ChannelDetailsActivity;
import com.karcompany.livetv.views.activities.ChannelListActivity;
import com.karcompany.livetv.views.adapters.ChannelImagesAdapter;
import com.karcompany.livetv.views.adapters.ChannelListAdapter;
import com.karcompany.livetv.views.fragments.ChannelDetailsFragment;
import com.karcompany.livetv.views.fragments.ChannelImagesFragment;
import com.karcompany.livetv.views.fragments.ChannelListFragment;
import com.karcompany.livetv.views.fragments.RecentlyViewedFragment;

import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

	void inject(LiveTVApplication liveTVApplication);

	void inject(BaseActivity baseActivity);

	void inject(ChannelListAdapter channelListAdapter);

	void inject(ChannelListFragment channelListFragment);

	void inject(ChannelListActivity channelListActivity);

	void inject(ChannelDetailsActivity channelDetailsActivity);

	void inject(ChannelDetailsFragment channelDetailsFragment);

	void inject(ChannelImagesAdapter channelImagesAdapter);

	void inject(ChannelImagesFragment productImagesFragment);

	void inject(RecentlyViewedFragment recentlyViewedFragment);

	ChannelListPresenter channelListPresenter();

	//Exposed to sub-graphs.
	Context context();
}
