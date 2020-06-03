package com.projeto.projetoredesocial.login.datasource;

import com.projeto.projetoredesocial.common.model.Database;
import com.projeto.projetoredesocial.common.model.UserAuth;
import com.projeto.projetoredesocial.common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {
    @Override
    public void login(String email, String senha, Presenter presenter) {
        Database.getInstance().login(email,senha)
                .addOnSuccessListener((Database.OnSuccessListener<UserAuth>) response -> presenter.onSuccess(response))
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(() -> presenter.onComplete());
    }
}
