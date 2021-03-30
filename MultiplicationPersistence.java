import java.util.Scanner;

public class MultiplicationPersistence {
	public static void main(String[] args) {
		// https://www.youtube.com/watch?v=Wim9WJeDTHQ&ab_channel=Numberphile
		print("\"start\": lets you check the multiplication persistence of a number\n");
		print("\"range\": shows you the multiplication persistence of a range of numbers\n");
		print("\"exit\": ends the program\n");
		Scanner sc = new Scanner(System.in);
		print("Input: ");
		String inp = sc.nextLine();
		if (inp.equals("start")) {
			do {
				print("Number: ");
				String s = sc.nextLine();
				if (s.equals("exit")) {
					break;
				}
				// 277777788888899 - largest multiplication persistence of a number
				print(s + "\n"); // displays number
				printPer(s); // displays the numbers it recursively multiplies to
				print("count: " + per(s) + "\n\n"); // displays the numbers it multiplies to
			} while (true);
		} else if (inp.equals("range")) {
			char c = sc.next().charAt(0);
			sc.close();
			if (c == 'y') {
				long min = 0;
				long max = Long.MAX_VALUE;
				int mingp = 0;
				perRange(min, max, mingp);
			}
		}
	}

	// sums the digits in a number
	public static int sumOfDigits(String s) {
		int sum = 0;
		for (int i = 0; i < s.length(); i++) {
			sum += s.charAt(i) - 48;
		}
		return sum;
	}

	// counts how many times a digit appears in a number
	public static int[] digitCount(String s) {
		int[] d = new int[10];
		for (int i = 0; i < s.length(); i++) {
			int dig = s.charAt(i) - 48;
			d[dig]++;
		}
		return d;
	}

	// checks digit count between two numbers
	public static boolean sameDigitCount(String s1, String s2) {
		int[] d1 = digitCount(s1);
		int[] d2 = digitCount(s2);
		for (int i = 0; i < 10; i++) {
			if (d1[i] != d2[i]) {
				return false;
			}
		}
		return true;
	}

	// shows the lowest number at each multiplication persistence count starting at a minimum greatest persistence
	public static void perRange(long min, long max, int mingp) {
		long n = min;
		String s;
		int p; // persistence
		int gp = mingp; // greatest persistence so far
		do {
			s = String.valueOf(n);
			p = per(s);
			if (p > gp) {
				print(s + "\n");
				printPer(s);
				print("count: " + p + "\n\n");
				gp = p; // update greatest persistence
			}
			n++;
		} while (n < max);
	}

	// returns multiplication persistence which is the count
	public static int per(String s) {
		int c = 0;
		while (s.length() > 1) {
			s = String.valueOf(mulDigits(s));
			c++;
		}
		return c;
	}

	// prints the numbers a part of a number's multiplication persistence
	public static void printPer(String s) {
		do {
			s = String.valueOf(mulDigits(s));
			print(s + "\n");
		} while (s.length() > 1);
	}

	// multiplies the digits of a number
	public static long mulDigits(String s) {
		long r = s.charAt(0) - 48;
		for (int i = 1; i < s.length(); i++) {
			r *= s.charAt(i) - 48;
		}
		return r;
	}

	public static void print(Object o) {
		System.out.print(o.toString());
	}
}
