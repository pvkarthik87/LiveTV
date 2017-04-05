package com.karcompany.livetv.networking;

import com.karcompany.livetv.models.ChannelMetaData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * REST service interface which Retrofit uses to communicate to a rest end point.
 */

public interface RestService {

	@GET("content/communities")
	Observable<ChannelMetaData[]> getChannels(@Query("providers") String provider);

}