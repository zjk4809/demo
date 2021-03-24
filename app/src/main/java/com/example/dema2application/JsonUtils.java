package com.example.dema2application;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
*读取解析本地json
*
*
 */
public class JsonUtils {
    private static JsonUtils instance;

    private JsonUtils(){

    }

    public static JsonUtils getInstance(){
        if(instance == null){
            instance = new JsonUtils();
        }
        return instance;
    }

    private String read(InputStream is){
        BufferedReader reader = null;
        StringBuilder sb = null;

        String line = null;

        try{
            sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null);{
                sb.append(line);
                sb.append("\n");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if (reader!=null)is.close();
                if (is!=null)is.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        return sb.toString();
    }
/**
*
 * @param context
 * @return List<WeatherData>
 **/

    public List<WeatherData> getWeatherList(Context context){
        List<WeatherData> list = new ArrayList<>();
        InputStream is = null;
        try{
            is = context.getResources().getAssets().open("weather.json");
            String json = read(is); //调用自定义的方法 读取json字符串

            Gson gson = new Gson();
            //通过反射机制，定义一类型解析器
            Type listType = new TypeToken<List<WeatherData>>(){}.getType();
            //使用GSON库实例，解析json字符串，将结果存入list中
            list = gson.fromJson(json, listType);

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if(is != null)is.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return list;
    }
}
