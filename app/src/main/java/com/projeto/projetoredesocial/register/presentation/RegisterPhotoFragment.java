package com.projeto.projetoredesocial.register.presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.projeto.projetoredesocial.R;
import com.projeto.projetoredesocial.common.component.CustomDialog;
import com.projeto.projetoredesocial.common.view.AbstractFragment;
import com.projeto.projetoredesocial.common.component.LoadingButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView{

    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;

    @BindView(R.id.register_camera_icon)
    ImageView imageViewCropped;

    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter){
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        presenter.setPhotoView(fragment);
        fragment.setPresenter(presenter);
        return fragment;
    }

    public RegisterPhotoFragment(){}


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);
    }

    @Override
    public void showProgressBar() {
        buttonNext.showProgress(true);
    }

    @Override
    public void onImageCropped(Uri uri) {
        try {
            if(getContext() != null && getContext().getContentResolver() != null){
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageViewCropped.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            Log.e("Teste" , e.getMessage(), e);
        }
    }


    @OnClick(R.id.register_button_next)
    public void onButtonNext(){
        buttonNext.setEnabled(true);
        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) ->{
                    switch (v.getId()){
                        case R.string.take_picture:
                            presenter.showCamera();
                            break;
                        case R.string.search_gallery:
                            presenter.showGallery();
                            break;
                    }
                }, R.string.take_picture, R.string.search_gallery)
                .build();

        customDialog.show();

    }
    @OnClick(R.id.register_button_jump)
    public void onButtonJumpClick(){
        presenter.jumpRegistration();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }


}
