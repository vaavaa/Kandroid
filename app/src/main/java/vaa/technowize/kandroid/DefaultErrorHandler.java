package vaa.technowize.kandroid;

import android.content.res.Resources;

import java.lang.ref.WeakReference;

import kandroid.R;

public class DefaultErrorHandler implements AppHandleError {

    private Resources resourceManager = null;

    private WeakReference<AppShowError> view;

    public void DefaultErrorHandler(Resources ResourceManager) {
        this.resourceManager = ResourceManager;
    }

    @Override
    public void proceed(Throwable error) {
        AppShowError errCurrent;
        try {
            errCurrent = view.get();
        } catch (Exception e) {
            throw new IllegalStateException("View must be attached to AppHandleError");
        }
        String message = error.getMessage();
        errCurrent.showError(message);
    }

    @Override
    public void attachView(AppShowError attachedView) {
        this.view = new WeakReference<>(attachedView);
    }

    @Override
    public void detachView() {
        this.view.clear();
    }
}
