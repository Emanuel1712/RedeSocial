package com.projeto.projetoredesocial.main.profile.datasource;

import com.projeto.projetoredesocial.common.model.Database;
import com.projeto.projetoredesocial.common.model.Post;
import com.projeto.projetoredesocial.common.model.User;
import com.projeto.projetoredesocial.common.model.UserProfile;
import com.projeto.projetoredesocial.common.presenter.Presenter;
import com.projeto.projetoredesocial.main.profile.presentation.ProfilePresenter;

import java.util.List;

public class ProfileLocalDataSource implements ProfileDataSource {
    @Override
    public void findUser(Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(db.getUser().getUUID())
                .addOnSuccessListener((Database.OnSuccessListener<User>) user ->{
                   db.findPosts(user.getUuid())
                           .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts ->{
                               presenter.onSuccess(new UserProfile(user, posts));
                               presenter.onComplete();
                           });
                });
    }
}
