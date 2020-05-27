package com.example.mapas;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Consultor on 23/09/2016.
 */
public class PermissionUtils {
    public static boolean validate(Activity activity, int requestCode, String... permissions) {
        List<String> list = new ArrayList<String>();
        for (String permission : permissions) {
            // Valida permissao
            //A instrucao abaixo eh responsavel por fazer a pergunta da pemissao passando a activity (classe java
            //que chamou) assim como o parametro da permissao
            boolean ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            if (! ok ) {
                //se reposta nao eh falsa entao .. adiciona permissao
                list.add(permission);
            }
        }
        if (list.isEmpty()) {
            // Tudo ok, retorna true
            return true;
        }

        // Lista de permissoees que faltam acesso.
        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        // Solicita permissao, metodo provida pela calsse app compat
        ActivityCompat.requestPermissions(activity, newPermissions, 1);

        return false;
    }



}
