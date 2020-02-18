package tcpip2;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Server {

   HashMap<String, ObjectOutputStream>
   maps = new HashMap<>();
   
   ServerSocket serverSocket;
   boolean aflag = true;
   
   public Server() {}
   public Server(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      System.out.println("Start Server");
      Runnable r = new Runnable() {
         @Override
         public void run() {
            while(aflag) {
               Socket socket = null;
               try {
                  System.out.println("Server Ready..");
                  socket = 
                        serverSocket.accept();
                  new Receiver(socket).start();
                  System.out.println(socket.getInetAddress());
               } catch (IOException e) {
                  break;
                  //e.printStackTrace();
               }
            }
         }
      };
      if(serverSocket != null) {
         new Thread(r).start();
      }
      
   }
   
   public void sendIp() {
      Set<String> 
      keys = maps.keySet();
      Iterator<String>
      its = keys.iterator();
      ArrayList<String> list = new ArrayList<>();
      while(its.hasNext()) {
         list.add(its.next());
      }
      Msg msg = new Msg();
      msg.setIplist(list);
      Sender sender = 
            new Sender(msg);
      sender.start();
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
         clientList(maps);
         sendIp();
         lb.setText("접속자:"+maps.size());
         System.out.println("접속자수:"+maps.size());
      }

      @Override
      public void run() {
         while(ois != null) {
            Msg msg = null;
            try {
               
               msg = (Msg) ois.readObject();
               System.out.println(
                  msg.getId()+":"+msg.getMsg());
               leftList.add(msg.getId()+":"+msg.getMsg(), 0);
               if(msg.getMsg().equals("q")) {
                  System.out.println(
                        msg.getId()+":Exit ..");
                  maps.remove(
                        socket.getInetAddress().toString()
                        );
                  sendIp();
                  clientList(maps);
                  lb.setText("접속자:"+maps.size());
                  System.out.println("접속자수:"+maps.size());
                  break;
               }
               sendMsg(msg);
            } catch (Exception e) {
               maps.remove(
                     socket.getInetAddress().toString()
                     );
               sendIp();
               clientList(maps);
               System.out.println(
                     socket.getInetAddress()+":Exit ..");
               lb.setText("접속자:"+maps.size());
               System.out.println("접속자수:"+maps.size());
                  break;
            }   
         }
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
   
   class Sender extends Thread{
      Msg msg;
      public Sender(Msg msg) {
         this.msg = msg;
      }
      @Override
      public void run() {
         // HashMap에 있는 oos를 꺼낸다음
         // for문을 돌리면서 전송 한다.
         Collection<ObjectOutputStream> 
         cols = maps.values();
         Iterator<ObjectOutputStream>
         its = cols.iterator();
         while(its.hasNext()) {
            try {
               its.next().writeObject(msg);
               System.out.println("--");

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
         String ip = msg.getIp();
         try {
            maps.get(ip).writeObject(msg);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
   }
   
   
   public void sendMsg(Msg msg) {
      String ip = msg.getIp();
      if(ip == null || ip.equals("")) {
         Sender sender = 
               new Sender(msg);
         sender.start();
      }else {
         Sender2 sender2 = 
               new Sender2(msg);
         sender2.start();
      }
      
   }
   
   Frame frame;
   List leftList,rightList;
   Panel panel,centerPanel;
   TextField ipTx, txtTx;
   Label lb;
   
   Button button;
   
   ArrayList<String> ipList;
   
   public void clientList(
   HashMap<String,ObjectOutputStream> maps) {
      Set<String> keys = maps.keySet();
      Iterator<String> skeys = 
            keys.iterator();
      rightList.removeAll();
      ipList = new ArrayList<>();
      while(skeys.hasNext()) {
         String ip = skeys.next();
         ipList.add(ip);
         rightList.add(ip);
      }
      
   }
   
   
   public void serverStart() {
      frame = new Frame();
      leftList = new List();
      rightList = new List();
      panel = new Panel();
      centerPanel = new Panel();
      ipTx = new TextField(8); 
      txtTx = new TextField(8);
      button = new Button("SEND");
      lb = new Label("접속자수:0");
      
      panel.add(lb);
      panel.add(ipTx);
      panel.add(txtTx);
      panel.add(button);
      
      
      frame.add(panel,"North");
      centerPanel.setLayout(
            new GridLayout(1, 2));
      centerPanel.add(leftList);
      centerPanel.add(rightList);
      frame.add(centerPanel,"Center");
      
      frame.setSize(500,400);
      frame.setVisible(true);
      
      button.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            String ip = ipTx.getText();
            String txt = txtTx.getText();
            if(ip == null || ip.equals("")) {
               Msg msg = new Msg("Admin", txt, "");
               Sender sender = new Sender(msg);
               sender.start();
            }else {
               Msg msg = new Msg("Admin", txt, ip);
               Sender2 sender2 = new Sender2(msg);
               sender2.start();
            }
         }
      });
      
      rightList.addItemListener(new ItemListener() {
         
         @Override
         public void itemStateChanged(ItemEvent e) {
            String ip = 
                  ipList.get(
                        (int)e.getItem()
                  );
            ipTx.setText(ip);
            
         }
      });
      
      
      frame.addWindowListener(new WindowListener() {
         
         @Override
         public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub
            
         }
         
         @Override
         public void windowIconified(WindowEvent e) {
            // TODO Auto-generated method stub
            
         }
         
         @Override
         public void windowDeiconified(WindowEvent e) {
            // TODO Auto-generated method stub
            
         }
         
         @Override
         public void windowDeactivated(WindowEvent e) {
            // TODO Auto-generated method stub
            
         }
         
         @Override
         public void windowClosing(WindowEvent e) {
            frame.setVisible(false);
            if(serverSocket != null) {
               try {
                  serverSocket.close();
               } catch (IOException e1) {
                  e1.printStackTrace();
               }
            }
            System.exit(0);
            
         }
         
         @Override
         public void windowClosed(WindowEvent e) {
            // TODO Auto-generated method stub
            
         }
         
         @Override
         public void windowActivated(WindowEvent e) {
            // TODO Auto-generated method stub
            
         }
      });
   }
   
   
   public static void main(String[] args) {
      Server server = null;
      try {
         server = new Server(8888);
         server.serverStart();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}






