package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import tcpip2.Msg;

public class SecondActivity extends AppCompatActivity {

    TextView textView;
    EditText editText, editText2;
    ListView listView;

    String ip;
    String id;
    int port;

    Socket socket;
    Sender sender;
    ArrayList<String> ids;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        listView = findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tid = ids.get(position);
                editText.setText(tid);
            }
        });

        Intent intent = getIntent();
        ip = intent.getStringExtra("ip").trim();
        port = Integer.parseInt(intent.getStringExtra("port").trim());
        id = intent.getStringExtra("id").trim();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new ConnectThread(ip,port,id).start();
        Log.d("------------","start");

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            socket.close();
            Log.d("------------","close");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class ConnectThread extends Thread {
        String ip;
        int port;
        String id;

        public ConnectThread() {
        }

        public ConnectThread(String ip, int port, String id) {
            this.ip = ip;
            this.port = port;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                socket = new Socket(ip, port);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Connected \n" + textView.getText());
                    }
                });
            } catch (Exception e) {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(ip + " Retry \n" + textView.getText());
                        }
                    });
                    try {
                        Thread.sleep(1000);
                        socket = new Socket(ip, port);
                        break;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            try {
                sender = new Sender(socket);
                Receiver receiver = new Receiver(socket);
                receiver.execute();

                Msg msg = new Msg(id, null, null);
                sender.setMsg(msg);
                new Thread(sender).start();
                Log.d("===", "Reciver Start");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }// end run

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
                    Log.d("===", "id " + msg.getId());
                    oos.writeObject(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Receiver extends AsyncTask<Void, Msg, Void> {
        InputStream is;
        ObjectInputStream ois;

        public Receiver(Socket socket) throws IOException {
            is = socket.getInputStream();
            ois = new ObjectInputStream(is);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (ois != null) {
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
        protected void onPostExecute(Void aVoid) {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Msg... values) {

            if (values[0].getIps() == null || values[0].getIps().size() == 0) {
                String txt =
                        values[0].getId() + ":" + values[0].getMsg();
                textView.setText(
                        txt + "\n" + textView.getText()
                );
            } else {
                ids = values[0].getIps();
                adapter = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_list_item_1, ids);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

        }
    }


    public void ckbt(View v) {
        String tid = editText.getText().toString();
        String txt = editText2.getText().toString();
        Msg msg = null;
        if (tid == null || tid.equals("")) {
            if (txt != null || txt.equals("")) {
                msg = new Msg(id, txt, null);
            } else {
                return;
            }
        }else{
            if (txt != null || txt.equals("")) {
                msg = new Msg(id, txt, tid);
            } else {
                return;
            }
        }
        sender.setMsg(msg);
        new Thread(sender).start();
    }
}