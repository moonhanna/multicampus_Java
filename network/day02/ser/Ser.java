package ser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Ser {

	public static void main(String[] args) throws Exception { // 지금은 던졌지만, 예외처리는 꼭 각각 처리해줘야함

		ArrayList<User> userlist = new ArrayList<User>();

		FileOutputStream fo = new FileOutputStream("user.ser");
		BufferedOutputStream bo = new BufferedOutputStream(fo);
		ObjectOutputStream oos = new ObjectOutputStream(bo);

		User user1 = new User("id01", "홍길동", 27);
		User user2 = new User("id02", "김길동", 26);
		User user3 = new User("id03", "이길동", 28);
		User user4 = new User("id04", "최길동", 29);

		userlist.add(user1);
		userlist.add(user2);
		userlist.add(user3);
		userlist.add(user4);
		
		oos.writeObject(userlist);

		oos.close();

		// 읽어서 출력하기
		FileInputStream fi = new FileInputStream("user.ser");
		BufferedInputStream bi = new BufferedInputStream(fi);
		ObjectInputStream ois = new ObjectInputStream(bi);
		
		ArrayList<User> inList = (ArrayList<User>) ois.readObject();
		
		System.out.println(inList);
		
		ois.close();

	}

}
