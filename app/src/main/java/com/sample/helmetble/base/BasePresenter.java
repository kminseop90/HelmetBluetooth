package com.sample.helmetble.base;

public abstract class BasePresenter<VIEW extends BaseView> {

    private VIEW view;
    public abstract void afterAttachView();

    public void attchView(VIEW view) {
        this.view = view;
        afterAttachView();
    }

    public void detachView() {
        view = null;

    }
    public VIEW view() {
        return view;
    }

    public boolean isAttachView() {
        return view() != null;
    }

}
