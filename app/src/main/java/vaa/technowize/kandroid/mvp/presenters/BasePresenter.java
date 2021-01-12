package vaa.technowize.kandroid.mvp.presenters;

import androidx.annotation.NonNull;

import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import moxy.MvpView;

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {


    private final CompositeDisposable disposables = new CompositeDisposable();

    protected void unsubscribeOnDestroy(@NonNull Subscription subscription) {
        disposables.add(Disposable.fromSubscription(subscription));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}