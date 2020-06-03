package com.projeto.projetoredesocial.register.presentation;

import android.net.Uri;

import com.projeto.projetoredesocial.R;
import com.projeto.projetoredesocial.common.model.UserAuth;
import com.projeto.projetoredesocial.common.presenter.Presenter;
import com.projeto.projetoredesocial.common.util.Strings;
import com.projeto.projetoredesocial.register.datasource.RegisterDataSource;

public class RegisterPresenter implements Presenter<UserAuth> {

    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView namePasswordView;
    private RegisterView registerView;
    private RegisterView.NamePasswordView setNamePasswordView;
    private final RegisterDataSource dataSource;
    private RegisterView.PhotoView photoView;

    private String email;
    private String name;
    private Uri uri;

    public void setRegisterView(RegisterView registerView){
        this.registerView = registerView;
    }

    public RegisterPresenter(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView){
        this.namePasswordView = namePasswordView;
    }

    public void setUri(Uri uri){
        this.uri = uri;
        if(photoView != null) {
            photoView.onImageCropped(uri);
            photoView.showProgressBar();

            dataSource.addPhoto(uri, new UpdatePhotoCallback());
        }
    }

    public void setEmail(String email){
        if(!Strings.emailValid(email)){
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;
        registerView.showNextView(ResgisterSteps.NAME_PASSWORD);
    }

    public void setNameAndPassword(String name, String password, String confirmPassword){
        if(!password.equals(confirmPassword)) {
            namePasswordView.onFailureForm(null, namePasswordView.getContext().getString(R.string.password_not_equal));
            return;
        }
        this.name = name;


        namePasswordView.showProgressBar();
        dataSource.CreateUser(name, email, password, this);
    }

    public void setPhotoView(RegisterView.PhotoView photoView){
        this.photoView = photoView;
    }

    public String getName(){
        return name;
    }

    public void showPhotoView() {
        registerView.showNextView(ResgisterSteps.PHOTO);
    }

    public void showCamera(){
        registerView.showCamera();
    }

    public void showGallery(){
        registerView.showGallery();
    }

    public void jumpRegistration(){
        registerView.onUserCreated();
    }
    @Override
    public void onSuccess(UserAuth response) {
        registerView.showNextView(ResgisterSteps.WELCOME);
    }

    @Override
    public void onError(String message) {
        namePasswordView.onFailureCreateUser(message);
    }

    @Override
    public void onComplete() {
        namePasswordView.hideProgressBar();
    }

    private class UpdatePhotoCallback implements Presenter<Boolean>{

        @Override
        public void onSuccess(Boolean response) {
            registerView.onUserCreated();
        }

        @Override
        public void onError(String message) {

        }

        @Override
        public void onComplete() {

        }
    }

}
