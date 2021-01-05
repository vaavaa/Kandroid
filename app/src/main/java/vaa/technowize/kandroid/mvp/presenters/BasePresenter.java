package vaa.technowize.kandroid.mvp.presenters;

import moxy.MvpPresenter;
import moxy.MvpView;

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {
//    private CompositeSubscription compositeSubscription = new CompositeSubscription();
//
//    protected void unsubscribeOnDestroy(@NonNull Subscription subscription) {
//        compositeSubscription.add(subscription);
//    }




    @Override public void onDestroy() {
        super.onDestroy();
//        compositeSubscription.clear();
    }
}