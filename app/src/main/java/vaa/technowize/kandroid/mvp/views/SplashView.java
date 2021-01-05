package vaa.technowize.kandroid.mvp.views;

import moxy.MvpView;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface SplashView extends MvpView {

    void startAnimation();
    void finishAnimation();

    @StateStrategyType(SkipStrategy.class)
    void successSignIn();

    @StateStrategyType(SkipStrategy.class)
    void startSignIn();

    void checkAccess();

}
