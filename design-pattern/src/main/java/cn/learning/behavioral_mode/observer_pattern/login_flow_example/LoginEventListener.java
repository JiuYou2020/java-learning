package cn.learning.behavioral_mode.observer_pattern.login_flow_example;

import java.util.concurrent.Flow;

public abstract class LoginEventListener implements Flow.Subscriber<LoginEvent> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(LoginEvent event) {
        handleEvent(event);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
    }

    protected abstract void handleEvent(LoginEvent event);
}
