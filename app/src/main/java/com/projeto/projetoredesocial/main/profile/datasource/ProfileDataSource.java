package com.projeto.projetoredesocial.main.profile.datasource;

import com.projeto.projetoredesocial.common.model.UserProfile;
import com.projeto.projetoredesocial.common.presenter.Presenter;
import com.projeto.projetoredesocial.main.profile.presentation.ProfilePresenter;

public interface ProfileDataSource {

    void findUser(Presenter<UserProfile> presenter);
}
