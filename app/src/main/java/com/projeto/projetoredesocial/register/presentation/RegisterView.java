package com.projeto.projetoredesocial.register.presentation;

import android.content.Context;
import android.net.Uri;

import com.projeto.projetoredesocial.common.view.View;

public interface RegisterView {

    void showNextView(ResgisterSteps step);

    void onUserCreated();

    void showCamera();

    void showGallery();

    interface EmailView{

        Context getContext();

        void onFailureForm(String emailError);

    }

    interface NamePasswordView extends View {

        Context getContext();

        void onFailureForm(String nameError, String passwordError);

        void onFailureCreateUser(String message);
    }

    interface WelcomeView{}

    interface PhotoView extends View{
        void onImageCropped(Uri uri);
    }
}
