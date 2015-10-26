package com.claudia.misalon.Application;

import android.app.Application;

import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.BO.Producto;
import com.claudia.misalon.BO.Servicio;
import com.claudia.misalon.views.MainActivity;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by gaspar on 17/10/15.
 */
public class MyApp extends Application {
    private final String APPID = "fdekst9m5LsXcvXsLa6p0F21aZZ9GkrAQASRbl6q";
    private final String CLIENTKEY = "D45k6GI9M9yLmlC0fwZutlUwWjHsAuEY9IeVJwbG";


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, APPID, CLIENTKEY);

        ParseObject.registerSubclass(Producto.class);
        ParseObject.registerSubclass(Servicio.class);
        ParseObject.registerSubclass(Cliente.class);

    }
}
