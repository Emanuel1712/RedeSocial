package com.projeto.projetoredesocial.login.presentation;

import com.projeto.projetoredesocial.common.view.View;

public interface LoginView extends View {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();
}
