package com.karcompany.livetv.events;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Using Rx as EventBus
 */

public class RxBus {

	public RxBus() {
	}

	private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

	public void send(Object o) {
		bus.onNext(o);
	}

	public Observable<Object> toObserverable() {
		return bus;
	}

	public boolean hasObservers() {
		return bus.hasObservers();
	}

}
