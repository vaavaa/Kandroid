package vaa.technowize.kandroid;

import javax.inject.Singleton;

import dagger.Component;
import vaa.technowize.kandroid.kanboard.KanboardAPI;
import vaa.technowize.kandroid.ui.LoginActivity;
import vaa.technowize.kandroid.ui.MainActivity;
import vaa.technowize.kandroid.ui.SplashActivity;

@Singleton
@Component(modules = {KanboardAPI.class})
public interface ApplicationComponent {

    KanboardAPI getKanAPI();

    void inject(SplashActivity splashActivity);
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
}
