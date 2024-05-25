package cn.learning.behavioral_mode.observer_pattern.login_reactive_example;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public abstract class LoginEventListener {
    private Disposable disposable;

    public void subscribe(PublishSubject<LoginEvent> subject) {
        disposable = subject.subscribe(this::handleEvent);
    }

    protected abstract void handleEvent(LoginEvent event);

    public void unsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
