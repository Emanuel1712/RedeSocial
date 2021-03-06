package com.projeto.projetoredesocial.main.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.projeto.projetoredesocial.R;
import com.projeto.projetoredesocial.common.view.AbstractActivity;
import com.projeto.projetoredesocial.login.presentation.LoginActivity;
import com.projeto.projetoredesocial.main.camera.presentation.CameraFragment;
import com.projeto.projetoredesocial.main.home.presentation.HomeFragment;
import com.projeto.projetoredesocial.main.profile.datasource.ProfileDataSource;
import com.projeto.projetoredesocial.main.profile.datasource.ProfileLocalDataSource;
import com.projeto.projetoredesocial.main.profile.presentation.ProfileFragment;
import com.projeto.projetoredesocial.main.profile.presentation.ProfilePresenter;
import com.projeto.projetoredesocial.main.search.presentation.SearchFragment;

public class MainActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView{

    public static final String ACT_SOURCE = "act_source";
    public static final int LOGIN_ACTIVITY = 0;
    public static final int REGISTER_ACTIVITY = 1;

    Fragment homeFragment;
    Fragment profileFragment;
    Fragment cameraFragment;
    Fragment searchFragment;
    Fragment active;

    public static void launch(Context context, int source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACT_SOURCE, source);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        BottomNavigationView bv = findViewById(R.id.main_bottom_nav);
        bv.setOnNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int source = extras.getInt(ACT_SOURCE);
            if (source == REGISTER_ACTIVITY){
                getSupportFragmentManager().beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

       if(getSupportActionBar() != null){
            Drawable drawable = getResources().getDrawable(R.drawable.ic_rede_camera);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onInject() {
        ProfileDataSource profileDataSource = new ProfileLocalDataSource();
        ProfilePresenter profilePresenter = new ProfilePresenter(profileDataSource);

        homeFragment = HomeFragment.newInstance(this);
        profileFragment = ProfileFragment.newInstance(this, profilePresenter);
        cameraFragment = new CameraFragment();
        searchFragment = new SearchFragment();

        active = homeFragment;

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.main_fragment, homeFragment).hide(homeFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, profileFragment).hide(profileFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, cameraFragment).hide(cameraFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, searchFragment).hide(searchFragment).commit();
    }

    @Override
    public void showProgressBar() {
        super.showProgressBar();
        findViewById(R.id.main_progress).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        super.hideProgressBar();
        findViewById(R.id.main_progress).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void scrollToolbarEnabled(boolean enabled) {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.main_appbar);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();

        if (enabled){
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
            appBarLayout.setLayoutParams(appBarLayoutParams);
        }else{
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
            appBarLayout.setLayoutParams(appBarLayoutParams);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager fm = getSupportFragmentManager();
        switch (menuItem.getItemId()){
            case R.id.menu_bottom_home:
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                scrollToolbarEnabled(false);
                return true;

            case R.id.menu_bottom_search:
                fm.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                scrollToolbarEnabled(false);
                return true;

            case R.id.menu_bottom_add:
                fm.beginTransaction().hide(active).show(cameraFragment).commit();
                active = cameraFragment;
                scrollToolbarEnabled(true);
                return true;

            case R.id.menu_bottom_profile:
                fm.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(true);
                return true;
        }
        return false;
    }


}
