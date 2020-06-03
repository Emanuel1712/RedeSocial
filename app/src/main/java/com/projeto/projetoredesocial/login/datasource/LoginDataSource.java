package com.projeto.projetoredesocial.login.datasource;

import com.projeto.projetoredesocial.common.presenter.Presenter;

public interface LoginDataSource  {

    void login(String email, String senha, Presenter presenter);
}
