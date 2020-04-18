package com.example.ingredieat.setting;

import android.content.res.Resources;
import android.util.TypedValue;


public class Setting {
    public static String PREF_NAME = "Preference";

    public static String googleId;
    public static String email;
    public static String givenName;
    public static String familyName;


    public static boolean ifSignIn;
    public static int count;
    public static boolean longClickFlag;
    public static boolean shortClickFlag;
    public static int currentMenu = -1;

    public final static class Strings {
        public final static String account_id = "account_id";
        public final static String account_email = "account email";
        public final static String account_given_name = "account given name";
        public final static String account_family_name = "account family name";
        public final static String if_signin_succ = "if_signin_succ";
    }

    public static int dpToPx(int dp, Resources r) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
