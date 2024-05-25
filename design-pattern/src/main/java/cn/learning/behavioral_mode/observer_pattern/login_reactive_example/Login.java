package cn.learning.behavioral_mode.observer_pattern.login_reactive_example;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class Login {
    private final PublishSubject<LoginEvent> subject = PublishSubject.create();

    public void fireEvent(LoginEvent event) {
        subject.onNext(event);
    }

    public PublishSubject<LoginEvent> getSubject() {
        return subject;
    }
}
