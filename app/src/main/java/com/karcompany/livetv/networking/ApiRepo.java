package com.karcompany.livetv.networking;

import com.karcompany.livetv.logging.DefaultLogger;
import com.karcompany.livetv.models.ChannelMetaData;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * REST Client which communicates to server to perform some operations
 */

public class ApiRepo {

	private static final String TAG = DefaultLogger.makeLogTag(ApiRepo.class);

	public interface GetChannelsApiCallback {
		void onSuccess(ChannelMetaData[] response);

		void onError(NetworkError networkError);
	}

	private final RestService mRestService;

	public ApiRepo(RestService restService) {
		this.mRestService = restService;
	}

	/*
	    Retrives images from server
	 */
	public Subscription getChannels(String provider, final GetChannelsApiCallback callback) {
		return mRestService.getChannels(provider).cache()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends ChannelMetaData[]>>() {
					@Override
					public Observable<? extends ChannelMetaData[]> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<ChannelMetaData[]>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						NetworkError ne = new NetworkError(e);
						callback.onError(ne);
						DefaultLogger.d(TAG, "onError "+ne.getAppErrorMessage());
					}

					@Override
					public void onNext(ChannelMetaData[] response) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(response);
					}
				});
	}

}
