package vaa.technowize.kandroid.mvp.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import vaa.technowize.kandroid.ApplicationComponent;
import vaa.technowize.kandroid.Constants;
import vaa.technowize.kandroid.kanboard.KanboardAPI;
import vaa.technowize.kandroid.mvp.views.SplashView;

//Нам нужно вести StateOfView
@InjectViewState
public class SplashPresenter extends BasePresenter<SplashView> {

}