package com.projeto.projetoredesocial.login.presentation;

import android.os.Handler;

import com.projeto.projetoredesocial.R;
import com.projeto.projetoredesocial.common.model.UserAuth;
import com.projeto.projetoredesocial.common.presenter.Presenter;
import com.projeto.projetoredesocial.common.util.Strings;
import com.projeto.projetoredesocial.login.datasource.LoginDataSource;



class LoginPresenter implements Presenter<UserAuth> {

    private final LoginView view;
    private final LoginDataSource dataSource;

    LoginPresenter(LoginView view, LoginDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    void login(String email, String password){
        if(!Strings.emailValid(email)){
            view.onFailureForm(view.getContext().getString(R.string.invalid_email), null);
            return;
        }

        view.showProgressBar();
        dataSource.login(email, password,this);
    }

    @Override
    public void onSuccess(UserAuth userAuth) {
        view.onUserLogged();
    }

    @Override
    public void onError(String message) {
        view.onFailureForm(null,message);
    }

    @Override
    public void onComplete() {}

}
