package com.example.deneme_veri_cekme_4;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    static String MQTTHOST = "tcp://broker.emqx.io:1883";
    static String USERNAME = "emqx";
    static String PASSWORD = "public";
    String topicStr = "MESSAGE";
    MqttAndroidClient client;
    TextView subText;
    MqttConnectOptions options;

    private static final String url="jdbc:mysql://192.168.212.217/Yeni";
    private static final String user="test123";
    private static final String pass="test";
    List<String> heart= new ArrayList<>();
    List<String> time= new ArrayList<>();
    List<String> heart_yazısız= new ArrayList<>();
    List<String> kıyasla= new ArrayList<>();
    List<Integer> heart_yazısız_int= new ArrayList<>();
   EditText veri;
TextView kontrol,risk_sayısı;
String metin,b;
int risk=0;
int say=0,q=0;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);




         String clientId = MqttClient.generateClientId();
         client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
         options = new MqttConnectOptions();
         options.setUserName(USERNAME);
         options.setPassword(PASSWORD.toCharArray());



         try {
             IMqttToken token = client.connect(options);
             token.setActionCallback(new IMqttActionListener() {
                 @Override
                 public void onSuccess(IMqttToken asyncActionToken) {
                     Toast.makeText(MainActivity.this,"connected",Toast.LENGTH_LONG).show();
                     setSubscruption();
                 }

                 @Override
                 public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                     Toast.makeText(MainActivity.this,"connection faild",Toast.LENGTH_LONG).show();


                 }
             });
         } catch (MqttException e) {
             e.printStackTrace();
         } client.setCallback(new MqttCallback() {
             @Override
             public void connectionLost(Throwable cause) {

             }

             @Override
             public void messageArrived(String topic, MqttMessage message) throws Exception {
                 subText.setText(new String(message.getPayload()));


             }

             @Override
             public void deliveryComplete(IMqttDeliveryToken token) {

             }
         });



         testDB();

         veri = (EditText) findViewById(R.id.editTextPhone2);

         kontrol=(TextView) findViewById(R.id.textView3);
         risk_sayısı=(TextView) findViewById(R.id.textView);
         int x=2;

do {


         new CountDownTimer(5000, 5000) {
             @Override
             public void onTick(long l) {

             }

             @Override
             public void onFinish() {
                 risk=0;
                 q=0;


                 String r,n;
                 int y = 130;
                 int k=30;
                 for (int i = 0; i < 5000 ; i++) {
                     if (heart_yazısız_int.get(i) >= y) {
                         risk=risk+1;
                     }
                 }
                 for (int u = 0; u< 5000 ; u++) {
                     if (heart_yazısız_int.get(u)<= k) {
                         q=q+1;
                     }
                 }
                 n=Integer.toString(q);
                 r = Integer.toString(risk);
                 risk_sayısı.setText("üst sınırı aşan "+r+"alt sınırı aşan "+n);




                 String topic = topicStr;
                 String message = "üst sınırı aşan "+r+"alt sınırı aşan "+n;


                 try{
                     client.publish(topic, message.getBytes(),0,false);
                 } catch ( MqttException e) {
                     e.printStackTrace();
                 }

             }
         }.start();




         }while(x<0);

}





public  void  bastım(View view){
    metin=" ";

    b=(String)veri.getText().toString();
    say=Integer.parseInt(b);

        for (int i=say*60;i<((say*60)+60);i++){
        metin=metin+"\n"+heart_yazısız_int;


        }

       kontrol.setText(metin);



}
    public  void karşilaştır(View view) {
        risk=0;
        q=0;


        String r,n;
        int y = 130;
        int k=30;
        for (int i = 0; i < 5000 ; i++) {
            if (heart_yazısız_int.get(i) >= y) {
                  risk=risk+1;
            }
        }
        for (int u = 0; u< 5000 ; u++) {
            if (heart_yazısız_int.get(u)<= k) {
                q=q+1;
            }
        }
            n=Integer.toString(q);
            r = Integer.toString(risk);
            risk_sayısı.setText("üst sınırı aşan "+r+"alt sınırı aşan "+n);




        String topic = topicStr;
        String message = "üst sınırı aşan "+r+"alt sınırı aşan "+n;


        try{
            client.publish(topic, message.getBytes(),0,false);
        } catch ( MqttException e) {
            e.printStackTrace();
        }

    }


    private void setSubscruption(){
        try {
            client.subscribe(topicStr,0);
        }catch (MqttException e){
            e.printStackTrace();


        }
    }
    public void testDB(){
        try{

            StrictMode.ThreadPolicy policy=
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");

            Connection con= DriverManager.getConnection(url, user, pass);

            String result="database connection succes \n";
            Statement st=con.createStatement();

            ResultSet rs=st.executeQuery("SELECT * FROM heartratedb"+"\n"+"\n");

            ResultSetMetaData rsmd=rs.getMetaData();
            while(rs.next()){
                result += rsmd.getColumnName(1)+":"+rs.getString(2)+"\n"+"\n";


             //   heart.add(rsmd.getColumnName(2)+rs.getString(2)+"\n"+"\n") ;
                heart_yazısız_int.add(rs.getInt(4));
               // time.add(rsmd.getColumnName(2)+":"+rs.getString(2)+"\n"+"\n") ;
                result += rsmd.getColumnName(5)+":"+rs.getString(4)+"\n"+"\n";
                kıyasla.add("65");


            }



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}