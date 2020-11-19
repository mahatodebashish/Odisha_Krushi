package com.odishakrushi;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by RatnaDev008 on 7/6/2018.
 */

public class LanguageChange {


    public static void changeLanguage(Context context,String languageToLoad)
    {
        if (languageToLoad.equals("or"))
        {
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
        }
        else  if (languageToLoad.equals("en"))
        {
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
        }


    }

}
