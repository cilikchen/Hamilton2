package com.example.taskforhamilton.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private final int updateTimeGap = 18000;

    SharedPreferences sharedpreferences;



    public void getRemoteData(Context context){

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(new Runnable() {
            @Override
            public void run() {
                String url_str = "https://v6.exchangerate-api.com/v6/bf0990164551850fa6299b2d/latest/USD";
// Making Request
                URL url = null;
                try {
                    url = new URL(url_str);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection request = null;
                try {
                    assert url != null;
                    request = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    assert request != null;
                    request.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonParser jp = new JsonParser();
                JsonElement root = null;
                try {
                    root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert root != null;
                JsonObject jsonb = root.getAsJsonObject();

                try (Reader reader = new InputStreamReader((InputStream) request.getContent())) {
                    Gson gson = new Gson();

                    QueryResult queryResult = gson.fromJson(jsonb, QueryResult.class);

                    String time_last_update_unix = queryResult.time_last_update_unix;
                    Map<String , Float> map = queryResult.conversion_rates;

                    sharedpreferences = context.getSharedPreferences("savingData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("time_last_update_unix",time_last_update_unix);
                    for (Map.Entry<String , Float> entry : map.entrySet()) {
                        editor.putFloat(entry.getKey(), entry.getValue());
                    }

                    editor.commit();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



// Convert to JSON


    }

    public float getExchangeRate(Context context,String exchangeFrom, String exchangeTo ){
        SharedPreferences sh = context.getSharedPreferences("savingData", Context.MODE_PRIVATE);
        if(checkUpdate(sh.getString("time_last_update_unix","0"))){
            getRemoteData(context);
            sh = context.getSharedPreferences("savingData", Context.MODE_PRIVATE);
        }
        float initRate = sh.getFloat(exchangeFrom,0);
        float targetRate = sh.getFloat(exchangeTo, 0);

        return targetRate / initRate ;

    }

    public void setLocalData(JsonObject jsonb){
        String path = "/app/src/main/assets/currenciesRate.json";

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(jsonb);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> getAvailableCurrency(Context context){
        List<String> currencies = new LinkedList<>();

        Gson gson = new Gson();
        try {

            AssetManager assetManager = context.getAssets();

//            Reader reader = Files.newBufferedReader(Paths.get("main/assets/currencies.json"));
            InputStream inputStream = assetManager.open("configs/currencies.json");
            Reader reader = new InputStreamReader(inputStream, "UTF-8");


//            List<User> users = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType());
//            users.forEach(System.out::println);

            Map<?, ?> map = gson.fromJson(reader, Map.class);


            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
                currencies.add(String.valueOf(entry.getKey()));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return currencies;
    }

    private boolean checkUpdate(String previousUpdateTime){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long currentTime = timestamp.getTime() /1000;

        return currentTime - Long.valueOf(previousUpdateTime) > updateTimeGap;


    }

}
