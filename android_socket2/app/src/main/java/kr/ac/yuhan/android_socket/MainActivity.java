package kr.ac.yuhan.android_socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetSocketAddress;


public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    Socket socket;
    private String ip="221.151.157.101";
    private int port = 9999;
    EditText et;
    TextView msgTV;
    Button btn1;

    @Override
    protected void onStop() {
        super.onStop();
        try {
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        et = (EditText)findViewById(R.id.Edit1);
        btn1 = (Button)findViewById(R.id.btn1);
        msgTV = (TextView)findViewById(R.id.Text1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText().toString()!=null || et.getText().toString().equals("")) {
                    ConnectThread th = new ConnectThread();
                    th.start();
                }
            }
        });



    }

    class ConnectThread extends Thread{
        @Override
        public void run() {
            try {
                //소켓생성
                InetAddress serverAddr = InetAddress.getByName(ip);
                socket = new Socket(serverAddr,port);
                //입력메시지
                String sndMsg = et.getText().toString();
                Log.d("======",sndMsg);
                //데이터 전송
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                out.println(sndMsg);
                //데이터 수신
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String read = input.readLine();
                //화면 출력
                mHandler.post(new msgUpdate(read));
                Log.d("======",read);
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // 받은 메시지 출력
    class msgUpdate implements Runnable{
        private String msg;
        public msgUpdate(String str){
            this.msg = str;
        }

        @Override
        public void run() {
            msgTV.setText(msgTV.getText().toString()+msg+"\n");
        }
    };
}