package com.example.tabserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.*;
import java.net.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.*;
import java.net.ServerSocket;
import tcpip2.*;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    TextView name1, name2, name3, name4,
            data1, data2, data3, data4,
            status;

    HashMap<String, ObjectOutputStream>
            maps = new HashMap<String, ObjectOutputStream>();
    HashMap<String, String>
            ids = new HashMap<String, String>();

    ArrayAdapter<String> adapter;

    ServerSocket serverSocket;
    boolean aflag = true;
    int port = 8888;

    Sender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeUi();
        new serverReady().start();
    }

    class serverReady extends Thread{

        public serverReady(){
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(aflag) {
                Socket socket = null;

                Log.d("-----","Server Ready..");
                try {
                    socket = serverSocket.accept();
                    new Receiver(socket).start();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setList();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public ArrayList<String> getIds() {
        Collection<String>
                id = ids.values();
        Iterator<String> it = id.iterator();
        ArrayList<String> list = new ArrayList<String>();
        while(it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }
    public void sendIp() {
        Set<String>
                keys = maps.keySet();
        Iterator<String>
                its = keys.iterator();
        ArrayList<String> list = new ArrayList<String>();
        while(its.hasNext()) {
            list.add(its.next());
        }
    }

    public void setList(){
        adapter =
                new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        getIds()
                );
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    public void displayData(Msg msg){
        final String id = msg.getId();
        final String txt = msg.getTxt();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name1.setText(id);
                data1.setText(txt);

            }
        });
        new SendeServer(id,txt).start();


    }

    class Receiver extends Thread{

        InputStream is;
        ObjectInputStream ois;

        OutputStream os;
        ObjectOutputStream oos;

        Socket socket;

        public Receiver(Socket socket) throws IOException {
            this.socket = socket;
            is = socket.getInputStream();
            ois = new ObjectInputStream(is);

            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            maps.put(socket.getInetAddress().toString(),
                    oos);
            try {
                Msg msg = (Msg) ois.readObject();
                ids.put(socket.getInetAddress().toString(),
                        msg.getId());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(ois != null) {
                Msg msg = null;
                try {

                    msg = (Msg) ois.readObject();

                    if(msg.getTxt().equals("q")) {


                        maps.remove(
                                socket.getInetAddress().toString()
                        );

                        ids.remove(socket.getInetAddress().toString()
                        );
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setList();
                            }
                        });
                        break;
                    }
                    //sendMsg(msg);
                    displayData(msg);
                } catch (Exception e) {
                    maps.remove(
                            socket.getInetAddress().toString()
                    );

                    ids.remove(socket.getInetAddress().toString()
                    );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setList();
                        }
                    });
                    break;
                }
            } // end while
            try {
                if(ois != null) {
                    ois.close();
                }
                if(socket != null) {
                    socket.close();
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

    }

    class SendeServer extends Thread{
        String urlstr = "http://70.12.113.206/test/tserver";

        public SendeServer(String id, String txt){
            urlstr += "?id="+id+"&txt="+txt;
        }

        @Override
        public void run() {
            try {
                Log.d("---","Connection start");
                URL url = new URL(urlstr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.getInputStream();
                Log.d("---","Connection completed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Sender extends Thread{
        Msg msg;
        public Sender(Msg msg) {
            this.msg = msg;
        }
        @Override
        public void run() {

            Collection<ObjectOutputStream>
                    cols = maps.values();
            Iterator<ObjectOutputStream>
                    its = cols.iterator();
            while(its.hasNext()) {
                try {
                    its.next().writeObject(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class Sender2 extends Thread{
        Msg msg;
        public Sender2(Msg msg) {
            this.msg = msg;
        }
        @Override
        public void run() {
            String tid = msg.getTid();
            try {
                Collection<String>
                        col = ids.keySet();
                Iterator<String> it = col.iterator();
                String sip = "";
                while(it.hasNext()) {
                    String key = it.next();
                    if(ids.get(key).equals(tid)) {
                        sip = key;
                    }
                }
                System.out.println(sip);
                maps.get(sip).writeObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void sendMsg(Msg msg) {
        String tid = msg.getTid();

        if(tid == null || tid.equals("")) {
            Sender sender =
                    new Sender(msg);
            sender.start();
        }else {
            Sender2 sender2 =
                    new Sender2(msg);
            sender2.start();
        }

    } // end sendMsg




    private void makeUi() {
        listView = findViewById(R.id.listView);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);
        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        data3 = findViewById(R.id.data3);
        data4 = findViewById(R.id.data4);
        status = findViewById(R.id.status);

        new ConnectThread(sip,sport,null).start();

    }

    public void ckbt(View v){
        Msg msg = null;
        if(v.getId() == R.id.button){
            msg = new Msg("server","1",null);
        }else if(v.getId() == R.id.button2){
            msg = new Msg("server","0",null);
        }
        sendMsg(msg);
    }

    //------------------------------------------------------------------------------- Client

    String tabid = "tab1";
    String sip = "server ip";
    int sport = 7878;

    Socket ssocket;

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
                ssocket.setSoTimeout(2000);
                ssocket = new Socket(ip, port);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("Connected Server");
                    }
                });
            } catch (Exception e) {
                int i = 0;
                while (true) {
                    i++;
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            status.setText("Retry Server" + finalI);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                        //ssocket.setSoTimeout(2000);
                        ssocket = new Socket(ip, port);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Connected Server");
                            }
                        });
                        break;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            try {
                SReceiver sreceiver = new SReceiver(ssocket);
                sreceiver.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }// end run

    }

    class SReceiver extends AsyncTask<Void, Msg, Void> {
        InputStream is;
        ObjectInputStream ois;

        public SReceiver(Socket socket) throws IOException {
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
                if (ssocket != null) {
                    ssocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Msg... values) {

            String id = values[0].getId();
            if(id.equals("System"))
            {
                if(ssocket != null)
                {
                    try {
                        ssocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                new ConnectThread(sip,sport,null).start();
                return;
            }


            String txt = values[0].getTxt();
            status.setText(txt);
            Msg msg = null;
            if(txt.trim().equals("1"))
            {
                msg = new Msg("server","0",null);
            }
            sendMsg(msg);
        }
    }

}