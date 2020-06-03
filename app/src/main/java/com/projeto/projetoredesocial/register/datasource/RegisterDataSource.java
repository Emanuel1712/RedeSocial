package com.projeto.projetoredesocial.register.datasource;

import android.net.Uri;

import com.projeto.projetoredesocial.common.presenter.Presenter;

public interface RegisterDataSource {

    void CreateUser(String name, String email, String password, Presenter presenter);

    void addPhoto(Uri uri, Presenter presenter);
}
