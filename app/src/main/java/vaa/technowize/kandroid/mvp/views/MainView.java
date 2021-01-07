package vaa.technowize.kandroid.mvp.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import vaa.technowize.kandroid.kanboard.KanboardAPI;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void combineDashboard();
    void combineProject();
    void setMeText(String text);
    void setKanboardAPI(KanboardAPI kanboardAPI);
}
