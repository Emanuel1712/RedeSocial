package com.projeto.projetoredesocial.register.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.projeto.projetoredesocial.R;
import com.projeto.projetoredesocial.common.component.MediaHelper;
import com.projeto.projetoredesocial.common.view.AbstractActivity;
import com.projeto.projetoredesocial.main.presentation.MainActivity;
import com.projeto.projetoredesocial.register.datasource.RegisterDataSource;
import com.projeto.projetoredesocial.register.datasource.RegisterLocalDataSouce;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends AbstractActivity implements RegisterView, MediaHelper.OnImageCroppedListener {

    @BindView(R.id.register_root_container)
    FrameLayout rootContainer;

    @BindView(R.id.register_scrollview)
    ScrollView scrollView;

    @BindView(R.id.register_crop_image_view)
    CropImageView cropImageView;

    @BindView(R.id.register_button_crop)
    Button buttonCrop;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private RegisterPresenter presenter;
    private MediaHelper mediaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();

        mediaHelper = MediaHelper.getInstance(this)
                .cropView(cropImageView)
                .listener(this);
    }

    @Override
    protected void onInject() {
        RegisterDataSource dataSource = new RegisterLocalDataSouce();
        presenter = new RegisterPresenter(dataSource);
        presenter.setRegisterView(this);

        showNextView(ResgisterSteps.EMAIL);

    }

    @Override
    public void showNextView(ResgisterSteps step) {
        Fragment frag = null;



        switch (step){
            case EMAIL:
                frag = RegisterEmailFragment.newInstance(presenter);
                break;
            case NAME_PASSWORD:
                frag = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                frag = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                buttonCrop.setEnabled(true);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
                layoutParams.gravity = Gravity.TOP;
                scrollView.setLayoutParams(layoutParams);
                frag = RegisterPhotoFragment.newInstance(presenter);
                break;
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if(manager.findFragmentById(R.id.register_fragment) == null){
            transaction.add(R.id.register_fragment, frag, step.name());
        }else{
            transaction.replace(R.id.register_fragment, frag, step.name());
            transaction.addToBackStack(step.name());
        }


        transaction.commit();
    }

    @Override
    public void onImageCropped(Uri uri) {
        presenter.setUri(uri);
    }

    @Override
    public void onImagePicked(Uri uri) {
        cropImageView.setImageUriAsync(uri);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        cropViewEnable(true);
        MediaHelper mediaHelper = MediaHelper.getInstance(this);
        mediaHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void cropViewEnable(boolean enabled) {
        cropImageView.setVisibility(enabled ? View.VISIBLE:View.GONE);
        scrollView.setVisibility(enabled ? View.GONE:View.VISIBLE);
        buttonCrop.setVisibility(enabled ? View.VISIBLE:View.GONE);
        rootContainer.setBackgroundColor(enabled ? findColor(android.R.color.black):findColor(android.R.color.white));
    }

    @Override
    public void showCamera() {
        mediaHelper.chooserCamera();
    }

    @Override
    public void showGallery() {
        mediaHelper.chooserGallery();
    }

    @OnClick(R.id.register_button_crop)
    public void onButtonCropClick(){
        cropViewEnable(false);
        MediaHelper.getInstance(this).cropImage(cropImageView);
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this, MainActivity.REGISTER_ACTIVITY);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

}
