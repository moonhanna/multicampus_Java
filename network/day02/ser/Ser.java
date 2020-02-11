package ser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Ser {

	public static void main(String[] args) throws Exception { // ������ ��������, ����ó���� �� ���� ó���������

		ArrayList<User> userlist = new ArrayList<User>();

		FileOutputStream fo = new FileOutputStream("user.ser");
		BufferedOutputStream bo = new BufferedOutputStream(fo);
		ObjectOutputStream oos = new ObjectOutputStream(bo);

		User user1 = new User("id01", "ȫ�浿", 27);
		User user2 = new User("id02", "��浿", 26);
		User user3 = new User("id03", "�̱浿", 28);
		User user4 = new User("id04", "�ֱ浿", 29);

		userlist.add(user1);
		userlist.add(user2);
		userlist.add(user3);
		userlist.add(user4);
		
		oos.writeObject(userlist);

		oos.close();

		// �о ����ϱ�
		FileInputStream fi = new FileInputStream("user.ser");
		BufferedInputStream bi = new BufferedInputStream(fi);
		ObjectInputStream ois = new ObjectInputStream(bi);
		
		ArrayList<User> inList = (ArrayList<User>) ois.readObject();
		
		System.out.println(inList);
		
		ois.close();

	}

}
