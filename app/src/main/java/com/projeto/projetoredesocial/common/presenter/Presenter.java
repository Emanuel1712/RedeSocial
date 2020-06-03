package com.projeto.projetoredesocial.common.presenter;

import com.projeto.projetoredesocial.common.model.UserAuth;

public interface Presenter<T> {

    void onSuccess(T response);

    void onError(String message);

    void onComplete();

}
