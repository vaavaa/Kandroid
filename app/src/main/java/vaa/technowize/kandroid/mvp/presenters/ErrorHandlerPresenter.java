package vaa.technowize.kandroid.mvp.presenters;
import vaa.technowize.kandroid.AppHandleError;
import vaa.technowize.kandroid.AppShowError;

public class ErrorHandlerPresenter extends BasePresenter<AppShowError> {

    AppHandleError errorHandler;

    public void setErrorHandler(AppHandleError errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void detachView() {

    }
}