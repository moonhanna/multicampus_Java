package com.example.chetclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import tcpip2.Msg;


public class MainActivity extends AppCompatActivity {
    EditText editText, editText2, editText3, editText4;
    Button button, button2;
    TextView textView;
    TextView textView2;
    //소켓을만들기위한 선언
    Socket socket;
    Sender sender;

    ArrayList<String> ipList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeUi();

    }


    private void makeUi() {
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
    }


    class ConnectThread extends Thread {

        String ip;
        int port;

        public ConnectThread() {
        }

        public ConnectThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {

            try {
                socket = new Socket(ip, port);
                textView.setText(ip + "Connected \n" + textView.getText());


            } catch (Exception e) {
                while (true) {

                    textView.setText(ip + "retry.. \n" + textView.getText());
                    try {
                        Thread.sleep(1000);
                        socket = new Socket(ip, port);
                        break;
                    } catch (Exception e1) {
                        //e1.printStackTrace();
                    }
                }
            }

            try {
                sender = new Sender(socket);
                Receiver receiver = new Receiver(socket);
                receiver.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } //end run

    }


    class Sender implements Runnable {

        OutputStream os;
        ObjectOutputStream oos;
        Msg msg;

        public Sender(Socket socket) throws IOException {
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
        }

        public void setMsg(Msg msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            if (oos != null) {
                try {
                    oos.writeObject(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class Receiver extends AsyncTask<Void,Msg,Void> {
        InputStream is;
        ObjectInputStream ois;

        public Receiver(Socket socket) throws IOException {
            is = socket.getInputStream();
            ois = new ObjectInputStream(is);
        }

        public Receiver() {
            super();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(ois != null) {
                Msg msg = null;
                try {
                        msg = (Msg) ois.readObject();
                        publishProgress(msg);
                } catch (Exception e) {
                    msg = new Msg("System", "Server Die", null);
                    publishProgress(msg);
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Msg... values) {
            super.onProgressUpdate(values);

            if(values[0].getIplist().size() > 0)
            {
                textView2.setText("");
                for(int i = 0; i < values[0].getIplist().size(); i++)
                {
                    textView2.setText(values[0].getIplist().get(i) + "\n" + textView2.getText());
                }
            }else {

                String txt = values[0].getId() + " : " + values[0].getMsg();
                textView.setText(txt + "\n" + textView.getText());
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                if(ois != null) {
                    ois.close();
                }
                if(socket != null) {
                    socket.close();
                }
            }catch(Exception e){

            }
        }
    }

    public void ckbt(View v) {
        if (v.getId() == R.id.button) {

            String ip = editText.getText().toString();
            int port = Integer.parseInt(editText2.getText().toString());
            new ConnectThread(ip, port).start(); //Thread시작
        } else if (v.getId() == R.id.button2) {
            String ip = editText3.getText().toString(); //귓속말
            String txt = editText4.getText().toString();
            Msg msg = null;
            if (ip == null || ip.equals("")) {
                msg = new Msg("Moon", txt, null);
            } else {
                msg = new Msg("Moon", txt, ip);
            }
            sender.setMsg(msg);
            new Thread(sender).start();
        }

    }
}

