package vaa.technowize.kandroid.mvp.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Dictionary;
import java.util.List;

import javax.inject.Inject;

import kandroid.R;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import vaa.technowize.kandroid.Constants;
import vaa.technowize.kandroid.KandroidApplication;
import vaa.technowize.kandroid.kanboard.KanboardAPI;
import vaa.technowize.kandroid.kanboard.KanboardActivity;
import vaa.technowize.kandroid.kanboard.KanboardCategory;
import vaa.technowize.kandroid.kanboard.KanboardColor;
import vaa.technowize.kandroid.kanboard.KanboardColumn;
import vaa.technowize.kandroid.kanboard.KanboardDashboard;
import vaa.technowize.kandroid.kanboard.KanboardError;
import vaa.technowize.kandroid.kanboard.KanboardProject;
import vaa.technowize.kandroid.kanboard.KanboardSwimlane;
import vaa.technowize.kandroid.kanboard.KanboardTask;
import vaa.technowize.kandroid.kanboard.KanboardUserInfo;
import vaa.technowize.kandroid.kanboard.events.OnErrorListener;
import vaa.technowize.kandroid.kanboard.events.OnGetActiveSwimlanesListener;
import vaa.technowize.kandroid.kanboard.events.OnGetAllCategoriesListener;
import vaa.technowize.kandroid.kanboard.events.OnGetAllTasksListener;
import vaa.technowize.kandroid.kanboard.events.OnGetColumnsListener;
import vaa.technowize.kandroid.kanboard.events.OnGetDefaultColorsListener;
import vaa.technowize.kandroid.kanboard.events.OnGetMeListener;
import vaa.technowize.kandroid.kanboard.events.OnGetMyActivityStreamListener;
import vaa.technowize.kandroid.kanboard.events.OnGetMyDashboardListener;
import vaa.technowize.kandroid.kanboard.events.OnGetMyOverdueTasksListener;
import vaa.technowize.kandroid.kanboard.events.OnGetMyProjectsListener;
import vaa.technowize.kandroid.kanboard.events.OnGetOverdueTasksByProjectListener;
import vaa.technowize.kandroid.kanboard.events.OnGetProjectByIdListener;
import vaa.technowize.kandroid.kanboard.events.OnGetProjectUsersListener;
import vaa.technowize.kandroid.mvp.views.MainView;
import vaa.technowize.kandroid.mvp.views.SplashView;

//Нам нужно вести StateOfView
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {


    @Inject
    KanboardAPI mKanboardAPI;

    private KanboardUserInfo Me;
    //    private List<KanboardProjectInfo> mProjects;
    private KanboardProject mProject = null;
    private KanboardDashboard mDashboard = null;
    private Dictionary<String, KanboardColor> mColors = null;
    private List<KanboardProject> mProjectList = null;

    private List<KanboardActivity> mMyActivities = null;
    private List<KanboardTask> mMyOverduetasks = null;
    private List<KanboardColumn> mColumns = null;
    private List<KanboardSwimlane> mSwimlanes = null;
    private List<KanboardCategory> mCategories = null;
    private List<KanboardTask> mActiveTasks = null;
    private List<KanboardTask> mInactiveTasks = null;
    private List<KanboardTask> mOverdueTasks = null;
    private Dictionary<Integer, String> mProjectUsers = null;

    private OnErrorListener errorListener = new OnErrorListener() {
        @Override
        public void onError(KanboardError error) {
            Log.e("ApiErr", "Error" +error.Message);
        }
    };

    private OnGetMeListener getMeListener = new OnGetMeListener() {
        @Override
        public void onGetMe(boolean success, KanboardUserInfo result) {
//            boolean prog = !getViewState().showProgress(false);
//            if (success) {
//                Me = result;
//                getViewState().setMeText(Me.getName());
//                if (prog) {
//                    if (getViewState().getMode() == 0)
//                        getViewState().combineDashboard();
//                    else
//                        getViewState().combineProject();
//                }
//            }
        }
    };

    private OnGetMyDashboardListener getMyDashboardListener = new OnGetMyDashboardListener() {
        @Override
        public void onGetMyDashboard(boolean success, KanboardDashboard result) {
            if (success) {
                mDashboard = result;

                getViewState().combineDashboard();

            }
        }
    };
    private OnGetMyActivityStreamListener getMyActivityStreamListener = new OnGetMyActivityStreamListener() {
        @Override
        public void onGetMyActivityStream(boolean success, List<KanboardActivity> result) {
            if (success) {
                mMyActivities = result;

                getViewState().combineDashboard();

            }
        }
    };

    private OnGetMyOverdueTasksListener getMyOverdueTasksListener = new OnGetMyOverdueTasksListener() {
        @Override
        public void onGetMyOverdueTasks(boolean success, List<KanboardTask> result) {
            if (success) {
                mMyOverduetasks = result;

                getViewState().combineDashboard();

            }
        }
    };

    private OnGetProjectByIdListener getProjectByIdListener = new OnGetProjectByIdListener() {
        @Override
        public void onGetProjectById(boolean success, KanboardProject result) {
            if (success) {
                mProject = result;
                getViewState().combineProject();

            }
        }
    };

    private OnGetColumnsListener getColumnsListener = new OnGetColumnsListener() {
        @Override
        public void onGetColumns(boolean success, List<KanboardColumn> result) {
            if (success) {
                mColumns = result;

                getViewState().combineProject();

            }
        }
    };
    private OnGetActiveSwimlanesListener getActiveSwimlanesListener = new OnGetActiveSwimlanesListener() {
        @Override
        public void onGetActiveSwimlanes(boolean success, List<KanboardSwimlane> result) {
            if (success) {
                mSwimlanes = result;

                getViewState().combineProject();

            }
        }
    };

    private OnGetAllCategoriesListener getAllCategoriesListener = new OnGetAllCategoriesListener() {
        @Override
        public void onGetAllCategories(boolean success, List<KanboardCategory> result) {
            if (success) {
                mCategories = result;

                getViewState().combineProject();

            }
        }
    };

    private OnGetAllTasksListener getAllTasksListener = new OnGetAllTasksListener() {
        @Override
        public void onGetAllTasks(boolean success, int status, List<KanboardTask> result) {
            if (success) {
                if (status == 0) {
                    mInactiveTasks = result;

                    getViewState().combineProject();

                } else if (status == 1) {
                    mActiveTasks = result;

                    getViewState().combineProject();

                }
            }
        }
    };

    private OnGetOverdueTasksByProjectListener getOverdueTasksByProjectListener = new OnGetOverdueTasksByProjectListener() {
        @Override
        public void onGetOverdueTasksByProject(boolean success, List<KanboardTask> result) {
            if (success) {
                mOverdueTasks = result;

                getViewState().combineProject();

            }
        }
    };

    private OnGetProjectUsersListener getProjectUsersListener = new OnGetProjectUsersListener() {
        @Override
        public void onGetProjectUsers(boolean success, Dictionary<Integer, String> result) {
            if (success) {
                mProjectUsers = result;

                getViewState().combineProject();

            }
        }
    };

    private OnGetDefaultColorsListener getDefaultColorsListener = new OnGetDefaultColorsListener() {
        @Override
        public void onGetDefaultColors(boolean success, Dictionary<String, KanboardColor> colors) {
            if (success) {
                mColors = colors;

                getViewState().combineDashboard();

            }
        }
    };

    private OnGetMyProjectsListener getMyProjectsListener = new OnGetMyProjectsListener() {
        @Override
        public void onGetMyProjects(boolean success, List<KanboardProject> result) {
            if (success) {
                mProjectList = result;

                getViewState().combineDashboard();

            }
        }
    };


    public boolean createKandoardAPI(Context baseContext) {
        String serverURL, username, password;

        if (mKanboardAPI != null)
            return true;

        Log.d(Constants.TAG, "Creating API object");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        boolean showLoginScreen = false;
        if (!preferences.contains("serverurl"))
            showLoginScreen = true;
        serverURL = preferences.getString("serverurl", "");

        if (!preferences.contains("username"))
            showLoginScreen = true;
        username = preferences.getString("username", "");

        if (!preferences.contains("password"))
            showLoginScreen = true;
        password = preferences.getString("password", "");

        if (showLoginScreen) {
            Log.i("Kandroid", "No credential found. Launching login activity.");
            return false;
        } else {
            try {
                mKanboardAPI = new KanboardAPI(serverURL, username, password);
                mKanboardAPI.addErrorListener(errorListener);
                mKanboardAPI.addOnGetMeListener(getMeListener);
                mKanboardAPI.addOnGetMyDashboardListener(getMyDashboardListener);
                mKanboardAPI.addOnGetMyActivityStreamListener(getMyActivityStreamListener);
                mKanboardAPI.addOnGetMyOverdueTasksListener(getMyOverdueTasksListener);
                mKanboardAPI.addOnGetProjectByIdListener(getProjectByIdListener);
                mKanboardAPI.addOnGetColumnsListener(getColumnsListener);
                mKanboardAPI.addOnGetActiveSwimlanesListener(getActiveSwimlanesListener);
                mKanboardAPI.addOnGetAllCategoriesListener(getAllCategoriesListener);
                mKanboardAPI.addOnGetAllTasksListener(getAllTasksListener);
                mKanboardAPI.addOnGetOverdueTasksByProjectListener(getOverdueTasksByProjectListener);
                mKanboardAPI.addOnGetProjectUsersListener(getProjectUsersListener);
                mKanboardAPI.addOnGetDefaultColorsListener(getDefaultColorsListener);
                mKanboardAPI.addOnGetMyProjectsListener(getMyProjectsListener);
                ((KandroidApplication) baseContext).appComponent.inject(mKanboardAPI);
                getViewState().setKanboardAPI(mKanboardAPI);
                return true;
            } catch (IOException e) {
                Log.e(Constants.TAG, "Failed to create API object.");
                e.printStackTrace();
            }
        }
        return false;
    }

    //region public methods
    public KanboardDashboard getDashboard() {
        return mDashboard;
    }

    public KanboardProject getProject() {
        return mProject;
    }

    public KanboardUserInfo getMe() {
        return Me;
    }

    public Dictionary<String, KanboardColor> getColors() {
        return mColors;
    }

    public List<KanboardProject> getProjectList() {
        return mProjectList;
    }

    //endregion
}