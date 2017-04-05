package com.karcompany.livetv;

/**
 * Created by pvkarthik on 2017-02-16.
 *
 * Presenter unit test cases.
 */

import com.karcompany.livetv.config.AppConfig;
import com.karcompany.livetv.config.Constants;
import com.karcompany.livetv.models.ChannelMetaData;
import com.karcompany.livetv.networking.ApiRepo;
import com.karcompany.livetv.presenters.ChannelListPresenter;
import com.karcompany.livetv.presenters.ChannelListPresenterImpl;
import com.karcompany.livetv.views.ChannelListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Subscription;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricGradleTestRunner.class)
// Change what is necessary for your project
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class ChannelListPresenterTest {

	@Mock
	private ApiRepo model;

	@Mock
	private ChannelListView view;

	@Mock
	private AppConfig appConfig;

	private ChannelListPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		presenter = new ChannelListPresenterImpl(model, appConfig);
		presenter.setView(view);
		/*
			Define the desired behaviour.

			Queuing the action in "doAnswer" for "when" is executed.
			Clear and synchronous way of setting reactions for actions (stubbing).
			*/
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				ChannelMetaData[] channels = new ChannelMetaData[0];
				((ApiRepo.GetChannelsApiCallback) presenter).onSuccess(channels);
				return Mockito.mock(Subscription.class);
			}
		}).when(model).getChannels(Constants.PROVIDER_YOUTUBE, (ApiRepo.GetChannelsApiCallback) presenter);
	}

	/**
	 Verify if model.getChannels was called once.
	 Verify if view.onDataReceived is called once with the specified object
	 */
	@Test
	public void testFetchChannels() {
		presenter.loadChannels();
		// verify can be called only on mock objects
		verify(model, times(1)).getChannels(Constants.PROVIDER_YOUTUBE, (ApiRepo.GetChannelsApiCallback) presenter);
		verify(view, times(1)).onDataReceived(any(ChannelMetaData[].class));
	}

}
