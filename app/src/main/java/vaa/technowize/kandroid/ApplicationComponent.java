package vaa.technowize.kandroid;

import dagger.Component;
import vaa.technowize.kandroid.ui.LoginActivity;
import vaa.technowize.kandroid.ui.MainActivity;
import vaa.technowize.kandroid.ui.SplashActivity;

@Component
public interface ApplicationComponent {
    void inject(SplashActivity splashActivity);
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
}
