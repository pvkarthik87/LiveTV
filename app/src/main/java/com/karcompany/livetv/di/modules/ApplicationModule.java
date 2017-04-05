package com.karcompany.livetv.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.karcompany.livetv.LiveTVApplication;
import com.karcompany.livetv.config.AppConfig;
import com.karcompany.livetv.events.RxBus;
import com.karcompany.livetv.networking.ApiRepo;
import com.karcompany.livetv.presenters.ChannelListPresenter;
import com.karcompany.livetv.presenters.ChannelListPresenterImpl;
import com.karcompany.livetv.presenters.ChannelDetailsPresenter;
import com.karcompany.livetv.presenters.ChannelDetailsPresenterImpl;
import com.karcompany.livetv.presenters.ChannelImagesPresenter;
import com.karcompany.livetv.presenters.ChannelImagesPresenterImpl;
import com.karcompany.livetv.presenters.RecentlyViewedPresenter;
import com.karcompany.livetv.presenters.RecentlyViewedPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
	private final LiveTVApplication application;

	public ApplicationModule(LiveTVApplication application) {
		this.application = application;
	}

	@Provides @Singleton
	Context provideApplicationContext() {
		return this.application;
	}

	@Provides @Singleton
	ChannelListPresenter provideChannelListPresenter(ApiRepo apiRepo, AppConfig appConfig) {
		return new ChannelListPresenterImpl(apiRepo, appConfig);
	}

	@Provides @Singleton
	RxBus provideRxBus() {
		return new RxBus();
	}

	@Provides @Singleton
	ChannelDetailsPresenter provideChannelDetailsPresenter(ChannelListPresenter channelListPresenter) {
		return new ChannelDetailsPresenterImpl(channelListPresenter);
	}

	@Provides @Singleton
	ChannelImagesPresenter provideChannelImagesPresenter(ChannelListPresenter channelListPresenter) {
		return new ChannelImagesPresenterImpl(channelListPresenter);
	}

	@Provides @Singleton
	SharedPreferences providesSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(this.application);
	}

	@Provides @Singleton
	AppConfig provideAppConfig(SharedPreferences sharedPreferences) {
		return new AppConfig(sharedPreferences);
	}

	@Provides @Singleton
	RecentlyViewedPresenter provideRecentlyViewedPresenter(AppConfig appConfig) {
		return new RecentlyViewedPresenterImpl(appConfig);
	}
}
