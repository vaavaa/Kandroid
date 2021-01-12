package vaa.technowize.kandroid;

public interface AppHandleError {
    void proceed(Throwable error);
    void attachView(AppShowError view);
    void detachView();
}
