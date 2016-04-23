package lanqiao;

import java.util.Random;
import java.util.Scanner;

public class LuckyDraw {
	private static final int MAXCOUNT = 10;
	private static Scanner input = new Scanner(System.in);
	private static int loginUserTag = -1;
	private static int userCount = 0;
	private static Random random = new Random();
	private static String[] userNames = new String[MAXCOUNT];//ֻ���ڶ��￪���������������洢   �ַ����ڴ��ַ  ���ڴ�ռ䣬
	private static String[] userPasswords = new String[MAXCOUNT];//�洢�ַ������ڴ�ռ䲢û�г�ʼ����
	private static String[] userVipCodes = new String[MAXCOUNT];//����Ŀǰ�����usernames[i]=null;

	public static void main(String[] args) {
		int key = showMenu();
		do {
			switch (key) {
			case 1:// ע��
				register();
				break;
			case 2:// ��¼

				login();
				break;
			case 3:// �齱
				lottery();
				break;
			case 0:// �˳�
				return;
			default:
				System.out.print("����������������룺");
				key = input.nextInt();
				continue;
			}
			if (isContinue()) {
				key = showMenu();
			} else {
				System.out.println("ϵͳ�˳���ллʹ�ã�");
				return;
			}
		} while (true);
	}

	public static int showMenu() {
		System.out.println("*****��ӭ���뽱�͸���ϵͳ*****");
		System.out.println("\t1.ע��");
		System.out.println("\t2.��¼");
		System.out.println("\t3.�齱");
		System.out.println("\t0.�˳�");
		System.out.println("*****************************");
		System.out.print("��ѡ��˵���");
		return input.nextInt();
	}

	public static void register() {
		loginUserTag = -1;
		if (userCount == userNames.length) {
			System.out.println("�û������ﵽ���ޣ��޷�ע�ᣡ");
			return;
		}
		System.out.println("[���͸���ϵͳ > ע��]");
		System.out.println("����д������Ϣ");
		System.out.print("�û�����");
		String userName = input.next();
		while (isExistInArray(userNames, userName)) {
			System.out.print("�û����Ѵ��ڣ����������룺");
			userName = input.next();
		}
		System.out.print("���룺");
		String userPassword = input.next();
		String vipCode = getVipCode(userVipCodes);
		userNames[userCount] = userName;
		userPasswords[userCount] = userPassword;
		userVipCodes[userCount++] = vipCode;
		System.out.println("\nע��ɹ�����Ǻ����Ļ�Ա���ţ�");
		System.out.println("�û���\t����\t��Ա����");
		System.out.println(userName + "\t" + userPassword + "\t" + vipCode);
	}

	public static void login() {
		loginUserTag = -1;
		System.out.println("[���͸���ϵͳ > ��¼]");
		for (int i = 0; i < 3; i++) {
			System.out.print("�������û�����");
			String userName = input.next();
			System.out.print("���������룺");
			String userPassword = input.next();
			for (int j = 0; j < userCount; j++) {
				if (userNames[j].equals(userName)) {
					if (userPasswords[j].equals(userPassword)) {
						loginUserTag = j;
						break;
					} else {
						break;
					}
				}
			}
			if (loginUserTag == -1 && i == 2) {
				System.out.println("�û����������������������࣬ϵͳ�˳�!");
				System.exit(0);
			} else if (loginUserTag == -1) {
				System.out.println("�û����������������������" + (3 - i - 1) + "���ᣡ");
			} else {
				System.out.println("\n��ӭ����" + userNames[loginUserTag]);
				break;
			}
		}
	}

	public static void lottery() {
		if (loginUserTag == -1) {
			System.out.println("��Ǹ������û�е�¼�����ȵ�¼��");
			login();
		}
		System.out.println("[���͸���ϵͳ > �齱]");

		System.out.print("�������Ա���ţ�");
		if (!input.next().equals(userVipCodes[loginUserTag])) {
			System.out.println("�������뿨�����¼�˺ſ��Ų�ƥ�䣬�ѵ�¼�˺ŵǳ���");
			loginUserTag = -1;
			return;
		}
		boolean isLucky = false;
		System.out.print("��������Ϊ��");
		String[] luckyCodes = new String[5];
		String vipCode = null;
		for (int i = 0; i < 5; i++) {
			vipCode = getVipCode(luckyCodes);
			luckyCodes[i] = vipCode;
			if (userVipCodes[loginUserTag].equals(vipCode)) {
				isLucky = true;
			}
			System.out.print(vipCode + "   ");
		}
		if (isLucky) {
			System.out.println("\n��ϲ������Ϊ���յ����˻�Ա��");
		} else {
			System.out.println("\n��Ǹ����û�г������˻�Ա��");
		}
	}

	public static boolean isContinue() {
		System.out.print("�����𣿣�y/n��:");
		do {
			switch (input.next().toLowerCase()) {
			case "y":
				return true;
			case "n":
				return false;
			default:
				System.out.print("����������������룺");
				break;
			}
		} while (true);
	}

	public static boolean isExistInArray(String[] stringArray, String string) {
		boolean result = false;
		for (int i = 0; i < stringArray.length && 
				stringArray[i]!=null; i++) {
			if (string.equalsIgnoreCase(stringArray[i])) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static String getVipCode(String[] vipCodes) {
		String vipCode = null;

		do {
			vipCode = random.nextInt(9000) + 1000 + "";
		} while (isExistInArray(vipCodes, vipCode));
		return vipCode;
	}

}
