package vaa.technowize.kandroid.mvp.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    boolean showProgress(final boolean show);
    void combineDashboard();
    void combineProject();
    void setMeText(String text);
    int getMode();
}
