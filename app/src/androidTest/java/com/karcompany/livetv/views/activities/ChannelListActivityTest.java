package com.karcompany.livetv.views.activities;

/**
 * Created by pvkarthik on 2017-02-16.
 *
 * Full scenario instrumentation tests.
 */

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.karcompany.livetv.LiveTVApplication;
import com.karcompany.livetv.R;
import com.karcompany.livetv.presenters.ChannelListPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ChannelListActivityTest {

	@Rule
	public ActivityTestRule<ChannelListActivity> activityRule = new ActivityTestRule<>(ChannelListActivity.class);

	private ChannelListPresenter mChannelListPresenter;

	private IdlingResource idlingResource;

	@Before
	public void setUp() {
		mChannelListPresenter = ((LiveTVApplication) activityRule.getActivity().getApplication()).getApplicationComponent()
				.channelListPresenter();
	}

	@Test
	public void testIsChannelDetailsShown() {
		onView(ViewMatchers.withId(R.id.progressView)).check(matches(isDisplayed()));
		idlingResource = startTiming(3000);
		stopTiming(idlingResource);
		onView(withId(R.id.channel_list)).perform(scrollToPosition(10));
		SystemClock.sleep(3000);
		onView(withId(R.id.channel_list)).perform(
				RecyclerViewActions.actionOnItemAtPosition(10, click()));
		SystemClock.sleep(3000);
		onView(withId(R.id.watchNowBar)).check(matches(isDisplayed()));
	}

	@Test
	public void testIsRecentChannelDetailsShown() {
		onView(withId(R.id.action_recent)).perform(click());
		onView(withId(R.id.channel_list)).perform(scrollToPosition(10));
		onView(withId(R.id.channel_list)).perform(
				RecyclerViewActions.actionOnItemAtPosition(0, click()));
		SystemClock.sleep(3000);
		onView(withId(R.id.watchNowBar)).check(matches(isDisplayed()));
	}

	public IdlingResource startTiming(long time) {
		IdlingResource idlingResource = new ElapsedTimeIdlingResource(time);
		Espresso.registerIdlingResources(idlingResource);
		return idlingResource;
	}

	public void stopTiming(IdlingResource idlingResource) {
		Espresso.unregisterIdlingResources(idlingResource);
	}

	public class ElapsedTimeIdlingResource implements IdlingResource {
		private long startTime;
		private final long waitingTime;
		private ResourceCallback resourceCallback;

		public ElapsedTimeIdlingResource(long waitingTime) {
			this.startTime = System.currentTimeMillis();
			this.waitingTime = waitingTime;
		}

		@Override
		public String getName() {
			return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
		}

		@Override
		public boolean isIdleNow() {
			long elapsed = System.currentTimeMillis() - startTime;
			boolean idle = (elapsed >= waitingTime);
			if (idle && !mChannelListPresenter.isLoading()) {
				resourceCallback.onTransitionToIdle();
			}
			return idle && !mChannelListPresenter.isLoading();
		}

		@Override
		public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
			this.resourceCallback = resourceCallback;
		}
	}

}
