import java.util.Scanner;

public class BigDecimal {
	// if want to make even better i have the numbers stored in a text file instead
	// of storing the number in a string which has a limited length
	// FUNCTIONS SHOULD RETURN BIGDECIMAL INSTEAD OF STRING
	String d1;
	boolean negative;

	// CLASS TEST FUNCTION
	// MY CALCULATOR VS DESMOS -> 1-0 (undefined result)
	// MY CALCULATOR VS https://www.calculator.net/big-number-calculator.html -> 1-0 (input too small)
	// MY CACULATOR VS https://goodcalculators.com/big-number-calculator/ -> 0-1 (faster than mine)
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		print("x1: ");
		String inp1 = sc.nextLine();
		print("x2: ");
		String inp2 = sc.nextLine();
		BigDecimal x1 = new BigDecimal(inp1);
		BigDecimal x2 = new BigDecimal(inp2);
//		println("x1: " + x1.toString());
//		println("x2: " + x2.toString());
		println("add: " + x1.add(x2));
		println("mul: " + x1.mult(x2));
		
		sc.close();

	}

	// limited by string length
	public BigDecimal(String s) {
		if (s.charAt(0) == '-') { // negative
			negative = true;
			d1 = s.substring(1);
		} else {
			negative = false;
			d1 = s;
		}
	}

	public String add(BigDecimal d2) {
		if ((negative && !d2.isNegative()) || (!negative && d2.isNegative())) { // subtraction
			return sub(d2);
		}
		String r = "";
		int d1l = d1.length();
		int d2l = d2.length();
		int comp = d1l - d2l;

		int x1;
		int x2;
		int x3;
		String x3s;
		int x3sl;
		int carry = 0;
		String zs = "";
		int i2 = d2l;
		int j2 = i2 - 9;
		int i1 = d1l;
		int j1 = i1 - 9;
		// *************************************************** //
		if (comp > 0) { // d1 > d2
			for (; j2 >= 0; i2 -= 9) { // won't go under 0
				x1 = Integer.valueOf(d1.substring(j1, i1)); // could substitute (j + comp, i + comp) but I think this is
															// faster
				x2 = Integer.valueOf(d2.substring(j2, i2));
				x3 = x1 + x2 + carry;
				x3s = String.valueOf(x3);
				x3sl = x3s.length();
				if (x3sl > 9) {
					carry = x3s.charAt(0) - 48;
					r = x3s.substring(1) + r;
				} else { // pad zeros (if 9, then no zeros will be padded)
					carry = 0;
					zs = "";
					for (int k = x3sl; k < 9; k++) {
						zs += '0';
					}
					r = zs + x3s + r;
				}
				j2 -= 9;
				j1 -= 9;
				i1 -= 9;
			}
			// do rest of d2's digits
			x2 = Integer.valueOf(d2.substring(0, i2));
			if (j1 < 0) {
				x1 = Integer.valueOf(d1.substring(0, i1));
			} else {
				x1 = Integer.valueOf(d1.substring(j1, i1));
			}
			x3 = x1 + x2 + carry;
			x3s = String.valueOf(x3);
			x3sl = x3s.length();
			if (x3sl > 9) {
				carry = x3s.charAt(0) - 48;
				r = x3s.substring(1) + r;
			} else { // pad zeros (if 9, then no zeros will be padded)
				carry = 0;
				zs = "";
				for (int k = x3sl; k < 9; k++) {
					zs += '0';
				}
				r = zs + x3s + r;
			}
			i1 -= 9;
			j1 -= 9;
			for (; j1 >= 0; i1 -= 9) { // append the rest of the digits of greater decimal
				x1 = Integer.valueOf(d1.substring(j1, i1));
				x3 = x1 + carry;
				x3s = String.valueOf(x3);
				x3sl = x3s.length();
				if (x3sl > 9) {
					carry = x3s.charAt(0) - 48;
					r = x3s.substring(1) + r;
				} else {
					carry = 0;
					zs = "";
					for (int k = x3sl; k < 9; k++) {
						zs += '0';
					}
					r = zs + x3s + r;
				}
				j1 -= 9;
			}
			if (i1 > 0) {
				x1 = Integer.valueOf(d1.substring(0, i1));
				x3 = x1 + carry;
				r = x3 + r;
			}
			// *************************************************** //
		} else if (comp < 0) { // d2 > d1
			for (; j1 >= 0; i1 -= 9) { // won't go under 0
				x1 = Integer.valueOf(d1.substring(j1, i1)); // could substitute (j + comp, i + comp) but I think this is
															// faster
				x2 = Integer.valueOf(d2.substring(j2, i2));
				x3 = x1 + x2 + carry;
				x3s = String.valueOf(x3);
				x3sl = x3s.length();
				if (x3sl > 9) {
					carry = x3s.charAt(0) - 48;
					r = x3s.substring(1) + r;
				} else { // pad zeros (if 9, then no zeros will be padded)
					carry = 0;
					zs = "";
					for (int k = x3sl; k < 9; k++) {
						zs += '0';
					}
					r = zs + x3s + r;
				}
				j1 -= 9;
				j2 -= 9;
				i2 -= 9;
			}
			// do rest of d1's digits
			if (i1 > 0) {
				x1 = Integer.valueOf(d1.substring(0, i1));
			} else {
				x1 = 0;
			}
			if (j2 < 0) {
				x2 = Integer.valueOf(d2.substring(0, i2));
			} else {
				x2 = Integer.valueOf(d2.substring(j2, i2));
			}
			x3 = x1 + x2 + carry;
			x3s = String.valueOf(x3);
			x3sl = x3s.length();
			if (x3sl > 9) {
				carry = x3s.charAt(0) - 48;
				r = x3s.substring(1) + r;
			} else { // pad zeros (if 9, then no zeros will be padded)
				carry = 0;
				zs = "";
				for (int k = x3sl; k < 9; k++) {
					zs += '0';
				}
				r = zs + x3s + r;
			}
			i2 -= 9;
			j2 -= 9;
			for (; j2 >= 0; i2 -= 9) { // append the rest of the digits of greater decimal
				x2 = Integer.valueOf(d2.substring(j2, i2));
				x3 = x2 + carry;
				x3s = String.valueOf(x3);
				x3sl = x3s.length();
				if (x3sl > 9) {
					carry = x3s.charAt(0) - 48;
					r = x3s.substring(1) + r;
				} else {
					carry = 0;
					zs = "";
					for (int k = x3sl; k < 9; k++) {
						zs += '0';
					}
					r = zs + x3s + r;
				}
				j2 -= 9;
			}
			if (i2 > 0) {
				x2 = Integer.valueOf(d2.substring(0, i2));
				x3 = x2 + carry;
				r = x3 + r;
			}
			// *************************************************** //
		} else { // d2l = d1l, doesn't mean they're the same value, just lengths same
			for (; j1 >= 0; i1 -= 9) { // won't go under 0
				x1 = Integer.valueOf(d1.substring(j1, i1));
				x2 = Integer.valueOf(d2.substring(j1, i1));
				x3 = x1 + x2 + carry;
				x3s = String.valueOf(x3);
				x3sl = x3s.length();
				if (x3sl > 9) {
					carry = x3s.charAt(0) - 48;
					r = x3s.substring(1) + r;
				} else { // pad zeros (if 9, then no zeros will be padded)
					carry = 0;
					zs = "";
					for (int k = x3sl; k < 9; k++) {
						zs += '0';
					}
					r = zs + x3s + r;
				}
				j1 -= 9;
			}
			if (i1 > 0) {
				x1 = Integer.valueOf(d1.substring(0, i1));
				x2 = Integer.valueOf(d2.substring(0, i1));
			} else {
				x1 = 0;
				x2 = 0;
			}
			x3 = x1 + x2 + carry;
			x3s = String.valueOf(x3);
			r = x3s + r;
		}
		r = removeLeadingZeros(r);
		if (negative) { // both are negative so essentially addition but sign is negative at the end
			r = '-' + r;
		}
		return r;
	}

	public String sub(BigDecimal d2) {
		return "";
	}

	public String mult(BigDecimal d2) {
		// strategy: loop through the digits in d2 (smaller one) and multiply d1 (bigger
		// one) by that digit and
		// store the result in array and add the results up in the array with .add
		// feature
		// https://gyazo.com/4ab9b6a718177db2a0b526ed9b2ae1c2
		// substring.len will be 8. and if x3s.len > 8 (x3s.len = 9) then carry =
		// x3s.charat(0)
		// carry will be added to result

		// better visualization: https://gyazo.com/dd3fc1419153c5472d11b12f7c61cd89
		// the carry isn't added to the result string (except for at the end)
		// if digit is 0 then go next, if digit is 1, then x3s is just d2 (smaller one)
		// remember to append appropriate amount of 0's to the end of result string
		// result strings will be stored in string array and then added after d2's
		// digits have been multiplied to d1

		int d1l = d1.length();
		int d2l = d2.length();
		int comp = d1l - d2l;
		// *************************************************** //
		if (comp > 0) { // d1l > d2l
			int digit;
			String[] prods = new String[d2l];
			String prod;
			int i1;
			int j1;
			int x1;
			int x3;
			int x3l;
			String x3s;
			int carry;
			String zs;
			int prodi = 0; // prods index
			for (int i2 = d2l - 1; i2 >= 0; i2--) {
				digit = d2.digitAt(i2);
				if (digit == 0) {
					prods[prodi] = "0";
					prodi++;
				} else if (digit == 1) {
					prod = d1;
					prod = appendZeros(prod, prodi);
					prods[prodi] = prod;
					prodi++;
				} else {
					prod = "";
					j1 = d1l - 8;
					carry = 0; // reset carry
					// get one product
					for (i1 = d1l; j1 >= 0; i1 -= 8) {
						x1 = Integer.valueOf(d1.substring(j1, i1));
						// println(digit + "-x1: " + x1);
						x3 = x1 * digit + carry;
						// println(digit + "-x3: " + x3);
						x3s = String.valueOf(x3);
						x3l = x3s.length();
						if (x3l > 8) {
							carry = x3s.charAt(0) - 48;
							prod = x3s.substring(1) + prod;
						} else { // pad zeros (if 9, then no zeros will be padded)
							carry = 0;
							zs = "";
							for (int k = x3l; k < 8; k++) {
								zs += '0';
							}
							prod = zs + x3s + prod;
						}
						// println("carry: " + carry);
						j1 -= 8;
					}
					// println("***outer***");
					if (i1 > 0) {
						x1 = Integer.valueOf(d1.substring(0, i1));
					} else {
						x1 = 0;
					}
					// println(digit + "-x1: " + x1);
					x3 = x1 * digit + carry;
					// println(digit + "-x3: " + x3);
					prod = String.valueOf(x3) + prod;
					prod = appendZeros(prod, prodi);
					// println(digit + "-prod: " + prod);
					prods[prodi] = prod; // add the product to later be summed
					prodi++; // increment prod index
				}
			}
			// print(prods, prodi);
			return sumBigDecimals(prods, prodi);
			// *************************************************** //
		} else { // d2l >= d1l

		}

		return "";
	}

	public static void print(String[] s, int l) {
		for (int i = 0; i < l; i++) {
			println(s[i]);
		}
	}

	public String sumBigDecimals(String[] s, int l) {
		BigDecimal r = new BigDecimal(s[0]);
		for (int i = 1; i < l; i++) {
			r = new BigDecimal(r.add(new BigDecimal(s[i])));
		}
		return r.toString();
	}

	public String appendZeros(String s, int z) {
		String zs = "";
		for (int i = 0; i < z; i++) {
			zs += '0';
		}
		return s + zs;
	}

	public String div(BigDecimal d2) { // div should have mod result
		return "";
	}

	public String mod(BigDecimal d2) {
		return "";
	}
	
	public boolean isNegative() {
		return negative;
	}

	public String removeLeadingZeros(String s) {
		int i = 0;
		while (s.charAt(i) == '0') {
			i++;
		}
		return s.substring(i);
	}

	public int length() {
		return d1.length();
	}

	public String substring(int beginIndex, int endIndex) {
		return d1.substring(beginIndex, endIndex);
	}

	public int digitAt(int index) {
		return d1.charAt(index) - 48;
	}

	public String toString() {
		if (negative) {
			return '-' + d1;
		} else {
			return d1;
		}
	}

	public static String smartPrint(String s) {
		String r = "";
		int start = 0;
		if (s.charAt(0) == '-') {
			start++;
		}
		int i = s.length();
		int j = i - 9;
		for (; j >= start; i -= 9) {
			r = s.substring(j, i) + ' ' + r;
			j -= 9;
		}
		return s.substring(start, i) + ' ' + r;
	}

	// CLASS TEST FUNCTIONS
	public static void print(Object o) {
		System.out.print(o.toString());
	}

	public static void println(Object o) {
		System.out.println(o.toString());
	}
}
