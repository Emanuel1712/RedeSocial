package com.projeto.projetoredesocial.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

    private static final Pattern VALID_EMAIL_ADDRES_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z09.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean emailValid(String email){
        Matcher matcher = VALID_EMAIL_ADDRES_REGEX.matcher(email);
        return matcher.find();
    }


}
