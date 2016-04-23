package lanqiao;

import java.util.Random;
import java.util.Scanner;

public class LuckyDraw {
	private static final int MAXCOUNT = 10;
	private static Scanner input = new Scanner(System.in);
	private static int loginUserTag = -1;
	private static int userCount = 0;
	private static Random random = new Random();
	private static String[] userNames = new String[MAXCOUNT];//只是在堆里开辟了连续的用来存储   字符串内存地址  的内存空间，
	private static String[] userPasswords = new String[MAXCOUNT];//存储字符串的内存空间并没有初始化，
	private static String[] userVipCodes = new String[MAXCOUNT];//所以目前任意的usernames[i]=null;

	public static void main(String[] args) {
		int key = showMenu();
		do {
			switch (key) {
			case 1:// 注册
				register();
				break;
			case 2:// 登录

				login();
				break;
			case 3:// 抽奖
				lottery();
				break;
			case 0:// 退出
				return;
			default:
				System.out.print("输入错误，请重新输入：");
				key = input.nextInt();
				continue;
			}
			if (isContinue()) {
				key = showMenu();
			} else {
				System.out.println("系统退出，谢谢使用！");
				return;
			}
		} while (true);
	}

	public static int showMenu() {
		System.out.println("*****欢迎进入奖客富翁系统*****");
		System.out.println("\t1.注册");
		System.out.println("\t2.登录");
		System.out.println("\t3.抽奖");
		System.out.println("\t0.退出");
		System.out.println("*****************************");
		System.out.print("请选择菜单：");
		return input.nextInt();
	}

	public static void register() {
		loginUserTag = -1;
		if (userCount == userNames.length) {
			System.out.println("用户数量达到上限，无法注册！");
			return;
		}
		System.out.println("[奖客富翁系统 > 注册]");
		System.out.println("请填写个人信息");
		System.out.print("用户名：");
		String userName = input.next();
		while (isExistInArray(userNames, userName)) {
			System.out.print("用户名已存在，请重新输入：");
			userName = input.next();
		}
		System.out.print("密码：");
		String userPassword = input.next();
		String vipCode = getVipCode(userVipCodes);
		userNames[userCount] = userName;
		userPasswords[userCount] = userPassword;
		userVipCodes[userCount++] = vipCode;
		System.out.println("\n注册成功，请记好您的会员卡号！");
		System.out.println("用户名\t密码\t会员卡号");
		System.out.println(userName + "\t" + userPassword + "\t" + vipCode);
	}

	public static void login() {
		loginUserTag = -1;
		System.out.println("[奖客富翁系统 > 登录]");
		for (int i = 0; i < 3; i++) {
			System.out.print("请输入用户名：");
			String userName = input.next();
			System.out.print("请输入密码：");
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
				System.out.println("用户名或密码输入错误次数过多，系统退出!");
				System.exit(0);
			} else if (loginUserTag == -1) {
				System.out.println("用户名或密码输入错误，您还有" + (3 - i - 1) + "机会！");
			} else {
				System.out.println("\n欢迎您，" + userNames[loginUserTag]);
				break;
			}
		}
	}

	public static void lottery() {
		if (loginUserTag == -1) {
			System.out.println("抱歉，您好没有登录，请先登录！");
			login();
		}
		System.out.println("[奖客富翁系统 > 抽奖]");

		System.out.print("请输入会员卡号：");
		if (!input.next().equals(userVipCodes[loginUserTag])) {
			System.out.println("您的输入卡号与登录账号卡号不匹配，已登录账号登出！");
			loginUserTag = -1;
			return;
		}
		boolean isLucky = false;
		System.out.print("幸运数字为：");
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
			System.out.println("\n恭喜您，成为本日的幸运会员！");
		} else {
			System.out.println("\n抱歉，您没有抽中幸运会员！");
		}
	}

	public static boolean isContinue() {
		System.out.print("继续吗？（y/n）:");
		do {
			switch (input.next().toLowerCase()) {
			case "y":
				return true;
			case "n":
				return false;
			default:
				System.out.print("输入错误，请重新输入：");
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
