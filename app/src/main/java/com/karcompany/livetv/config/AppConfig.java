package com.karcompany.livetv.config;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.utils.json.JSONParser;

import java.util.ArrayList;
import java.util.List;

import static com.karcompany.livetv.config.Constants.MAX_RECENT_ITEMS;

/**
 * Created by pvkarthik on 2017-02-15.
 */

public class AppConfig {

	private SharedPreferences mSharedPreferences;

	public AppConfig(SharedPreferences sharedPreferences) {
		mSharedPreferences = sharedPreferences;
	}

	public ChannelMetaData[] getRecentlyViewedChannels() {
		String recentJson =  mSharedPreferences.getString(Constants.KEY_RECENT_VIEWED, "");
		if(!TextUtils.isEmpty(recentJson)) {
			return JSONParser.parse(recentJson, ChannelMetaData[].class);
		}
		return null;
	}

	public void addToRecent(ChannelMetaData channel) {
		String recentJson =  mSharedPreferences.getString(Constants.KEY_RECENT_VIEWED, "");
		List<ChannelMetaData> productList = new ArrayList<>();
		if(!TextUtils.isEmpty(recentJson)) {
			ChannelMetaData[] products = JSONParser.parse(recentJson, ChannelMetaData[].class);
			for(ChannelMetaData result:products) {
				productList.add(result);
			}
			int index = productList.indexOf(channel);
			if(index >= 0) {
				productList.remove(index);
				productList.add(0, channel);
			} else {
				productList.add(0, channel);
			}
			while(productList.size() > MAX_RECENT_ITEMS) {
				productList.remove(MAX_RECENT_ITEMS);
			}
		} else {
			productList.add(channel);
		}
		mSharedPreferences.edit().putString(Constants.KEY_RECENT_VIEWED, JSONParser.toJSON(productList)).apply();
	}
}
