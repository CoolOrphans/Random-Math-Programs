
public class MultiplicationPersistenceBase {
	// gives multiplication persistence of a number based on its base
	public static void main(String[] args) {
		// incomplete
		print(mulDigits("ABC", 16));
	}

	// converts decimal into a base that is at most base 16
	public static String convert(int d, int b) {
		String r = "";
		int m;
		if (b <= 10) {
			while (d > 0) {
				m = d % b;
				r = m + r;
				d = d / b;
			}
		} else if (b <= 16) {
			char c;
			while (d > 0) {
				m = d % b;
				if (m > 9) {
					if (m == 10) {
						c = 'A';
					} else if (m == 11) {
						c = 'B';
					} else if (m == 12) {
						c = 'C';
					} else if (m == 13) {
						c = 'D';
					} else if (m == 14) {
						c = 'E';
					} else {
						c = 'F';
					}
				} else {
					c = (char) (m + 48);
				}
				r = c + r;
				d = d / b;
			}
		} else {
			print("Convert doesn't support base " + b);
		}
		return r;
	}

	// converts decimal into a base that is at most base 16
	// returns a long instead of a string
	public static long convert2(int d, int b) {
		String r = "";
		int m;
		if (b <= 10) {
			while (d > 0) {
				m = d % b;
				r = m + r;
				d = d / b;
			}
		} else if (b <= 16) {
			char c;
			while (d > 0) {
				m = d % b;
				if (m > 9) {
					if (m == 10) {
						c = 'A';
					} else if (m == 11) {
						c = 'B';
					} else if (m == 12) {
						c = 'C';
					} else if (m == 13) {
						c = 'D';
					} else if (m == 14) {
						c = 'E';
					} else {
						c = 'F';
					}
				} else {
					c = (char) (m + 48);
				}
				r = c + r;
				d = d / b;
			}
		} else {
			print("Convert doesn't support base " + b);
		}
		return Long.valueOf(r);
	}

	// multiplies the digits of a number
	public static long mulDigits(String s, int base) {
		long r = 1;
		char c;
		for (int i = 0; i < s.length(); i++) {
			c = (char) (s.charAt(i) - 48);
			if (c > 9) { // letter
				c -= 7;
			}
			r *= c;
		}
		r = convert2((int) r, base);
		return r;
	}

	public static int per(String s, int base) {
		int c = 0;
		while (s.length() > 1) {
			s = String.valueOf(mulDigits(s, base));
			c++;
		}
		return c;
	}

	// shows the lowest number at each multiplication persistence count starting at
	// a minimum greatest persistence
	public static void perRange(long min, long max, int mingp, int base) {
		long n = min;
		String s;
		int p; // persistence
		int gp = mingp; // greatest persistence so far
		do {
			s = String.valueOf(n);
			p = per(s, base);
			if (p > gp) {
				print(s + "\n");
				printPer(s, base);
				print("count: " + p + "\n\n");
				gp = p; // update greatest persistence
			}
			n++;
		} while (n < max);
	}

	// prints the numbers a part of a number's multiplication persistence
	public static void printPer(String s, int base) {
		do {
			s = String.valueOf(mulDigits(s, base));
			print(s + "\n");
		} while (s.length() > 1);
	}

	public static void print(Object o) {
		System.out.print(o.toString());
	}
}
