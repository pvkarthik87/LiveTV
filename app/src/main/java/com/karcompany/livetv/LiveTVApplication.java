package com.karcompany.livetv;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Android Application.
 */

import android.app.Application;

import com.karcompany.livetv.di.components.ApplicationComponent;
import com.karcompany.livetv.di.components.DaggerApplicationComponent;
import com.karcompany.livetv.di.modules.ApplicationModule;
import com.karcompany.livetv.networking.NetworkModule;

public class LiveTVApplication extends Application {

	private static ApplicationComponent applicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		initializeInjector();
	}

	private void initializeInjector() {
		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.networkModule(new NetworkModule(this))
				.build();
		applicationComponent.inject(this);
	}

	public static ApplicationComponent getApplicationComponent() {
		return applicationComponent;
	}
}

