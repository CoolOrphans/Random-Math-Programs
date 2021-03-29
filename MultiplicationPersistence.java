import java.util.Scanner;

public class MultiplicationPersistence {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		print("Number: ");
		String s = sc.nextLine();
		// 277777788888899 - largest multiplication persistence of a number
		print(s + "\n"); // displays number
		printPer(s); // displays the numbers it recursively multiplies to
		print("count: " + per(s) + "\n\n"); // displays the numbers it multiplies to

		print("Would you like to see the lowest number at each multiplication persistence count? (y/n): ");
		char c = sc.next().charAt(0);
		sc.close();
		if (c == 'y') {
			long min = 0;
			long max = Long.MAX_VALUE;
			int mingp = 0;
			perRange(min, max, mingp);
		}
	}

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

	public static void printPer(String s) {
		do {
			s = String.valueOf(mulDigits(s));
			print(s + "\n");
		} while (s.length() > 1);
	}

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
