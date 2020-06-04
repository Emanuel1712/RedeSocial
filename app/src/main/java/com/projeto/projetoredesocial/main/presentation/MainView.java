package com.projeto.projetoredesocial.main.presentation;

import android.net.Uri;

import com.projeto.projetoredesocial.common.model.Post;
import com.projeto.projetoredesocial.common.view.View;

import java.util.List;

public interface MainView extends View {

    void scrollToolbarEnabled(boolean enabled);

    public interface  ProfileView  extends View {

        void showPhoto(Uri photo);

        void showData(String name, String following, String followers, String posts);

        void showPosts(List<Post> posts);
    }
}
