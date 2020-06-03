package com.projeto.projetoredesocial.register.datasource;

import android.net.Uri;
import android.provider.ContactsContract;

import com.projeto.projetoredesocial.common.model.Database;
import com.projeto.projetoredesocial.common.model.UserAuth;
import com.projeto.projetoredesocial.common.presenter.Presenter;

public class RegisterLocalDataSouce implements RegisterDataSource{
    @Override
    public void CreateUser(String name, String email, String password, Presenter presenter) {
        Database.getInstance().createUser(name, email, password)
                .addOnSuccessListener((Database.OnSuccessListener<UserAuth>) presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

    @Override
    public void addPhoto(Uri uri, Presenter presenter) {
        Database db = Database.getInstance();
        db.addPhoto(db.getUser().getUUID(), uri)
                .addOnSuccessListener((Database.OnSuccessListener<Boolean>) presenter::onSuccess);

    }
}
