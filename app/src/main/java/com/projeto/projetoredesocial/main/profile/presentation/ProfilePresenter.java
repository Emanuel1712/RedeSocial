package com.projeto.projetoredesocial.main.profile.presentation;

import com.projeto.projetoredesocial.common.model.Post;
import com.projeto.projetoredesocial.common.model.User;
import com.projeto.projetoredesocial.common.model.UserProfile;
import com.projeto.projetoredesocial.common.presenter.Presenter;
import com.projeto.projetoredesocial.main.presentation.MainView;
import com.projeto.projetoredesocial.main.profile.datasource.ProfileDataSource;

import java.util.List;

public class ProfilePresenter implements Presenter<UserProfile> {

    private final ProfileDataSource dataSource;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public void findUser() {
        view.showProgressBar();
        dataSource.findUser(this);
    }

    @Override
    public void onSuccess(UserProfile userProfile) {
        User user = userProfile.getUser();
        List<Post> posts = userProfile.getPosts();

        view.showData(
                user.getName(),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getPosts())
        );
        view.showPosts(posts);

        if(user.getUri() != null)
            view.showPhoto(user.getUri());
    }

    @Override
    public void onError(String message) {
        // TODO: 03/06/20
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();

    }

}
