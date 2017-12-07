package com.example.alesis.myapplication.services;

import android.os.Environment;
import android.util.Log;

import com.example.alesis.myapplication.repo.SemaforoRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alesis on 21/08/2016.
 */
public class JSONService {

    private static JSONService instance = null;

    public static JSONService getInstance() {
        if(instance == null) {
            instance = new JSONService();
        }
        return instance;
    }

    private final String sdcard_alesis = "/alesis/";
    final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + sdcard_alesis );

    public JsonObject getJsonFromFile(String fileName){
        JsonObject jsonObject = null;
        try {
            final File myFile = new File(dir,fileName);
            FileInputStream is = new FileInputStream(myFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String s_json = new String(buffer, "UTF-8");
            jsonObject = new Gson().fromJson(s_json, JsonObject.class);
        }catch (FileNotFoundException f){
            Log.e("ALERT","could not find file");
            return null;
        }

        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonObject;
    }

    public JsonObject getJsonFromFile(File file){
        JsonObject jsonObject = null;
        try {
            FileInputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String s_json = new String(buffer, "UTF-8");
            jsonObject = new Gson().fromJson(s_json, JsonObject.class);
        }catch (FileNotFoundException f){
            Log.e("ALERT","could not find file");
            return null;
        }

        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonObject;
    }

    public List<JsonObject> getAll(){
        List<JsonObject> list = new ArrayList<>();
        for(File f: getListFiles(dir)){
            list.add(getJsonFromFile(f));
        }

        return list;
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".json")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }

    public void saveJsonFile(String fileName, String json)
    {
        FileOutputStream fos = null;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + sdcard_alesis );

            if (!dir.exists())
            {
                if(!dir.mkdirs()){
                    Log.e("ALERT","could not create the directories");
                }
            }

            final File myFile = new File(dir,fileName);

            if (!myFile.exists())
            {
                myFile.createNewFile();
            }

            fos = new FileOutputStream(myFile);

            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
