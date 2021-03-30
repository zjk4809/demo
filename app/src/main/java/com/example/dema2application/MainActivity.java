package com.example.dema2application;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private VideoView vd_1;
    private Button mbt_1;//按钮控件
    private TextView tv_weather, tv_location, tv_temperature,tv_suggestion;//文字内容显示控件
    private EditText mET_1;
    private ListView mList_1;
    String cityID ,cityName;
    List<String> CityIDList = new ArrayList<>();
    List<String> CityNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //        天气初始化
        HeConfig.switchToDevService();
        HeConfig.init("HE2103091114091161", "327ea68f3d29472e83452e0b4c9bf6b6");

        tv_weather = (TextView) findViewById(R.id.weather);
        tv_location = (TextView) findViewById(R.id.location);
        tv_temperature = (TextView) findViewById(R.id.temperature);
        tv_suggestion = (TextView) findViewById(R.id.suggestion);

        mET_1 = (EditText)findViewById(R.id.ETX_1);
        
        mbt_1 = (Button) findViewById(R.id.mbt_1);

//        mList_1 = (ListView) findViewById(R.id.List_1);

     //   MediaPlayer();//视频播放

        mbt_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cityName = mET_1.getText().toString();
                if (cityName == " "){
                    Toast.makeText(MainActivity.this, "未输入城市", Toast.LENGTH_SHORT).show();
                }else {getGPI();}
                if(CityNameList.get(0) == null)
                {
                    Toast.makeText(MainActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }else if(cityID != null){
                    cityID = CityIDList.get(0);
                    getWeather();//天气
                }
//
            }
        });

    }
    //视频
    protected void MediaPlayer(){
        vd_1 = findViewById(R.id.videoView);
        vd_1.setMediaController(new MediaController(this));
        Uri videoUri = Uri.parse("/storage/emulated/0/Download/" + "beta.mp4");
        vd_1.start();
        vd_1.setVideoURI(videoUri);

        vd_1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
        vd_1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                vd_1.start();
                vd_1.setVideoURI(videoUri);
            }
        });
    }
    //天气
    protected void getWeather(){

        /**
         * 实况天气数据
         * @param location 所查询的地区，可通过该地区名称、ID、IP和经纬度进行查询经纬度格式：纬度,经度
         *                 （英文,分隔，十进制格式，北纬东经为正，南纬西经为负)
         * @param lang     (选填)多语言，可以不使用该参数，默认为简体中文
         * @param unit     (选填)单位选择，公制（m）或英制（i），默认为公制单位
         * @param listener 网络访问结果回调
         */
        QWeather.getWeatherNow(MainActivity.this, cityID, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherBean) {
//                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));
//                Log.i(TAG, "onSuccess: " + new Gson().toJson(weatherBean.getBasic()));

                String weather = null, temperature = null, city = null, district = null, cid = null;
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK.getCode().equalsIgnoreCase(String.valueOf(weatherBean.getCode()))) {
                    //在此查看返回数据失败的原因
                    Code status = weatherBean.getCode();
                    Code code = Code.toEnum(String.valueOf(status));
                    Log.i(TAG, "failed code: " + code);
                } else {
                    String JsonNow = new Gson().toJson(weatherBean.getNow());
                    JSONObject jsonObject = null;

                    try {
                        jsonObject = new JSONObject(JsonNow);
                        weather = jsonObject.getString("text");
                        temperature = jsonObject.getString("temp");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String temperature2 = temperature + "℃";
                tv_weather.setText(weather);
                tv_temperature.setText(temperature2);

            }
        });
    }

    protected void getGPI()
    {
        QWeather.getGeoCityLookup(MainActivity.this,cityName ,new QWeather.OnResultGeoListener()
        {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(GeoBean geoBean) {


                String JsonGPI = new Gson().toJson(geoBean.getLocationBean());
                Gson gson = new Gson();
                List<GsonF> gsonFList = gson.fromJson(JsonGPI, new TypeToken<List<GsonF>>(){}.getType());
                for (GsonF gsonf : gsonFList){
                    CityIDList.add(gsonf.getId());
                    CityNameList.add(gsonf.getName());
                }
                Log.i(TAG, "城市: "+CityNameList.get(0));
                Log.i(TAG, "城市ID: "+CityIDList.get(0));

            }

        });
    }

}



    