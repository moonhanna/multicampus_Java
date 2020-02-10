package day01;

import java.awt.Frame;
import java.awt.List;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


class FrameApp{
	
	Frame f;
	List list1, list2;
	
	public FrameApp() {
		f = new Frame();
		list1 = new List();
		list2 = new List();
		
		f.add(list1, "East");
		f.add(list2, "West");
		f.setSize(500,800);
		f.setVisible(true);

		f.addWindowListener(new WindowListener() {
			
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
				f.setVisible(false);
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
	
	public void setData() {
		
		AddList al = new AddList();
		al.start();

	}
	
}

class AddList extends Thread{
	
	FrameApp fa = new FrameApp();
	
	@Override
	public void run() {
		
		for(int i = 1; i <= 50; i++)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			fa.list1.add("list 1 : " + i);
			fa.list2.add("list 2 : " + i);
		}
		
	}
	
}

public class T3 {

	public static void main(String[] args) {
		
		FrameApp f = new FrameApp();
		f.setData();
	}

}
