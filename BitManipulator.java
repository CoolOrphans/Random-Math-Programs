import java.util.Scanner;

public class BitManipulator {

	public static int default_base = 10;

	// manipulates bits of a binary sequence
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		print("Number: ");
		String s = sc.nextLine(); // user's number
		boolean negative = false;
		if (s.charAt(0) == '-') {
			negative = true;
			s = s.substring(1); // update number without negative
		}
		print("Base: ");
		int base = sc.nextInt(); // user's base
		sc.nextLine();
		print("Padding: ");
		String paddings = sc.nextLine(); // user's padding
		int padding = 0;
		if (paddings.length() != 0) { // [ENTER] = keep number as is
			padding = Integer.valueOf(paddings);
		}
		int check = isValidNumber(s, base);
		if (check != 0) {
			sc.close();
			if (check == -1) { // invalid base
				println("Base not in range [1, 16]");
			} else { // either characters exist outside of base or characters just invalid
				println("The digits in the number are either outside of its respective base or are invalid");
			}
			return;
		}
		if (base == 2) { // s is already binary
			if (padding < s.length()) {
				padding = s.length();
				if (negative) {
					padding++;
				}
			}
			if (negative) {
				s = negativeSignExtend(twosComplement(s), padding);
			} else {
				s = signExtend(s, padding);
			}
		} else {
			s = binary(s, base);
			if (padding < s.length()) {
				padding = s.length();
				if (negative) {
					padding++;
				}
			}
			if (negative) {
				s = negativeSignExtend(twosComplement(s), padding);
			} else {
				s = signExtend(s, padding);
			}
		}

		// HARD-CODED VALUES
		int shift = 2; // bits to shift
		int z = 16; // extend
		int baseN = 12; // base-n want to show

		newline();
		String space = "";
		int sep = padding + 15;
		String p1 = "Binary: " + s;
		space = makeSpaces(sep - p1.length());
		println(p1 + space + "Binary (Signed): " + signedBinToBin(s));
		String p2 = "Hexadecimal: " + binToHex(s);
		space = makeSpaces(sep - p2.length());
		println(p2 + space + "Hexadecimal (Signed): " + signedBinToHex(s));
		String p3 = "Octal: " + binToOctal(s);
		space = makeSpaces(sep - p3.length());
		println(p3 + space + "Octal (Signed): " + signedBinToOctal(s));
		String p4 = "Decimal: " + binToDec(s);
		space = makeSpaces(sep - p4.length());
		println(p4 + space + "Decimal (Signed): " + signedBinToDec(s));
		String p5 = "Base-" + baseN + ": " + binToBaseN(s, baseN);
		space = makeSpaces(sep - p5.length());
		println(p5 + space + "Base-" + baseN + " (Signed): " + signedBinToBaseN(s, baseN));
		newline();

		println(onesComplement(s) + ": One's Complement");
		println(twosComplement(s) + ": Two's Complement");
		println(leftShift(s, shift) + ": Left Shift by " + shift + " Bits");
		println(logicalRightShift(s, shift) + ": Logical Right Shift by " + shift + " Bits");
		println(arithmeticRightShift(s, shift) + ": Arithmetic Right Shift by " + shift + " Bits");
		newline();
		if (z >= padding) {
			println(zeroExtend(s, z) + ": " + z + "-Bit Zero Extend");
			println(signExtend(s, z) + ": " + z + "-Bit Sign Extend");
		}
		sc.close();
	}

	// creates spaces for an even output
	public static String makeSpaces(int n) {
		String r = "";
		for (int i = 0; i < n; i++) {
			r += " ";
		}
		return r;
	}

	// pads the binary string with 1's
	public static String negativeSignExtend(String s, int n) {
		String r = "";
		for (int i = 0; i < n - s.length(); i++) {
			r += "1";
		}
		return r + s;
	}

	// converts base n number to its binary sequence where number takes form
	// s = "number_base"
	public static String binary(String s) {
		// s = "1030_10" where 1030 is the number and 10 is the base
		int c = s.indexOf("_");
		int b;
		if (c == -1) { // no "_" so assumed to be default base
			b = default_base;
		} else {
			b = Integer.valueOf(s.substring(c + 1)); // base
			s = s.substring(0, c); // number
		}
		if (b == 16) { // hex
			return hexToBin(s);
		} else if (b == 8) { // octal
			return octalToBin(s);
		} else { // rest of the bases
			return decToBin(decimal(s, b));
		}
	}

	// converts number to its binary sequence
	// s = "number" b = "base"
	public static String binary(String s, int b) {
		String r;
		if (b == 16) {
			r = hexToBin(s);
		} else if (b == 8) {
			r = octalToBin(s);
		} else { // rest of the bases
			r = decToBin(decimal(s, b));
		}
		return r;
	}

	// converts signed binary to binary
	public static String signedBinToBin(String s) {
		if (s.charAt(0) == '0') { // positive
			return s;
		} else { // negative
			return '-' + twosComplement(s);
		}
	}

	// converts the signed binary into decimal
	public static int signedBinToDec(String s) {
		if (s.charAt(0) == '0') { // positive
			return binToDec(s);
		} else { // negative
			return -binToDec(twosComplement(s));
		}
	}

	// converts the signed binary to hexadecimal
	public static String signedBinToHex(String s) {
		if (s.charAt(0) == '0') { // positive
			return binToHex(s);
		} else {
			return '-' + binToHex(twosComplement(s));
		}
	}

	// converts the signed binary into octal
	public static String signedBinToOctal(String s) {
		if (s.charAt(0) == '0') { // positive
			return binToOctal(s);
		} else { // negative
			return '-' + binToOctal(twosComplement(s));
		}
	}

	// takes a base-n number and converts to a base-b number
	public static String baseN(String s, int n, int b) { // b = desired base to convert to
		return decToBaseN(decimal(s, n), b);
	}

	// converts decimal to desired base-b
	public static String decToBaseN(int d, int b) { // b = desired base
		String r = "";
		boolean negative = false;
		if (d < 0) {
			negative = true;
			d = -d;
		}
		if (b <= 10) {
			while (d > 0) {
				r = d % b + r;
				d = d / b;
			}
		} else { // base greater than 10
			int m;
			while (d > 0) {
				m = d % b;
				if (m > 9) {
					switch (m) {
					case 10:
						r = 'A' + r;
						break;
					case 11:
						r = 'B' + r;
						break;
					case 12:
						r = 'C' + r;
						break;
					case 13:
						r = 'D' + r;
						break;
					case 14:
						r = 'E' + r;
						break;
					case 15:
						r = 'F' + r;
						break;
					}
				} else {
					r = m + r;
				}
				d = d / b;
			}
		}
		if (negative) {
			r = '-' + r;
		}
		return r;
	}

	// converts binary to hexadecimal
	public static String binToHex(String s) {
		String r = "";
		int j = s.length() - 4;
		int i;
		for (i = j + 4; j >= 0; i -= 4) {
			switch (s.substring(j, i)) {
			case "0000":
				r = '0' + r;
				break;
			case "0001":
				r = '1' + r;
				break;
			case "0010":
				r = '2' + r;
				break;
			case "0011":
				r = '3' + r;
				break;
			case "0100":
				r = '4' + r;
				break;
			case "0101":
				r = '5' + r;
				break;
			case "0110":
				r = '6' + r;
				break;
			case "0111":
				r = '7' + r;
				break;
			case "1000":
				r = '8' + r;
				break;
			case "1001":
				r = '9' + r;
				break;
			case "1010":
				r = 'A' + r;
				break;
			case "1011":
				r = 'B' + r;
				break;
			case "1100":
				r = 'C' + r;
				break;
			case "1101":
				r = 'D' + r;
				break;
			case "1110":
				r = 'E' + r;
				break;
			case "1111":
				r = 'F' + r;
				break;
			}
			j -= 4;
		}
		switch (s.substring(0, i)) { // 1-3 chars
		case "001": // fall through statements
		case "01":
		case "1":
			r = '1' + r;
			break;
		case "010":
		case "10":
			r = '2' + r;
			break;
		case "011":
		case "11":
			r = '3' + r;
			break;
		case "100":
			r = '4' + r;
			break;
		case "101":
			r = '5' + r;
			break;
		case "110":
			r = '6' + r;
			break;
		case "111":
			r = '7' + r;
			break;
		}
		return r;
	}

	// converts binary to octal
	public static String binToOctal(String s) {
		String r = "";
		int j = s.length() - 3;
		int i;
		for (i = j + 3; j >= 0; i -= 3) {
			switch (s.substring(j, i)) {
			case "000":
				r = '0' + r;
				break;
			case "001":
				r = '1' + r;
				break;
			case "010":
				r = '2' + r;
				break;
			case "011":
				r = '3' + r;
				break;
			case "100":
				r = '4' + r;
				break;
			case "101":
				r = '5' + r;
				break;
			case "110":
				r = '6' + r;
				break;
			case "111":
				r = '7' + r;
				break;
			}
			j -= 3;
		}
		switch (s.substring(0, i)) { // either 1 or 2 chars
		case "01": // fall through statements
		case "1":
			r = '1' + r;
			break;
		case "10":
			r = '2' + r;
			break;
		case "11":
			r = '3' + r;
			break;
		}
		return r;
	}

	// converts binary to decimal
	public static int binToDec(String s) {
		int n = 0;
		int j = s.length() - 1;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '1') { // usually it's 1*2^p
				n += pow(2, j);
			}
			j--;
		}
		return n;
	}

	// converts binary to desired base-b
	public static String binToBaseN(String s, int b) { // useless function
		return baseN(s, 2, b);
	}

	// converts signed binary to desired base-b
	public static String signedBinToBaseN(String s, int b) {
		return decToBaseN(signedBinToDec(s), b);
	}

	// converts hexadecimal to binary
	public static String hexToBin(String s) {
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '1':
				r += "0001";
				break;
			case '2':
				r += "0010";
				break;
			case '3':
				r += "0011";
				break;
			case '4':
				r += "0100";
				break;
			case '5':
				r += "0101";
				break;
			case '6':
				r += "0110";
				break;
			case '7':
				r += "0111";
				break;
			case '8':
				r += "1000";
				break;
			case '9':
				r += "1001";
				break;
			case 'A':
				r += "1010";
				break;
			case 'B':
				r += "1011";
				break;
			case 'C':
				r += "1100";
				break;
			case 'D':
				r += "1101";
				break;
			case 'E':
				r += "1110";
				break;
			case 'F':
				r += "1111";
				break;
			}
		}
		return removeLeadingZeros(r);
	}

	// converts octal to binary
	public static String octalToBin(String s) {
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '1':
				r += "001";
				break;
			case '2':
				r += "010";
				break;
			case '3':
				r += "011";
				break;
			case '4':
				r += "100";
				break;
			case '5':
				r += "101";
				break;
			case '6':
				r += "110";
				break;
			case '7':
				r += "111";
				break;
			}
		}
		return removeLeadingZeros(r);
	}

	// converts decimal to binary
	public static String decToBin(int n) {
		String r = "";
		while (n > 0) {
			r = (n % 2) + r;
			n /= 2;
		}
		return r;
	}

	// converts base-n number to decimal
	public static int decimal(String s, int b) {
		int n = 0;
		int c;
		if (b <= 10) {
			int k = 0;
			for (int i = s.length() - 1; i >= 0; i--) {
				n += (s.charAt(i) - 48) * pow(b, k);
				k++;
			}
		} else { // base 11-16: A, B, C, D, E, F
			int k = 0;
			for (int i = s.length() - 1; i >= 0; i--) {
				c = s.charAt(i);
				c -= 48;
				if (c > 9) { // letter
					c -= 7;
				}
				n += c * pow(b, k);
				k++;
			}
		}
		return n;
	}

	// pads a number with n zeros
	public static String zeroExtend(String s, int n) {
		String p = "";
		for (int i = 0; i < n - s.length(); i++) {
			p += "0";
		}
		return p + s;
	}

	// sign extend binary string which is just padding but keeping the sign
	public static String signExtend(String s, int n) {
		String p = "";
		if (s.charAt(0) == '0') {
			for (int i = 0; i < n - s.length(); i++) {
				p += "0";
			}
		} else {
			for (int i = 0; i < n - s.length(); i++) {
				p += "1";
			}
		}
		return p + s;
	}

	// shifts binary string left by n bits
	public static String leftShift(String s, int n) {
		String p = "";
		for (int i = 0; i < n; i++) {
			p += '0';
		}
		return s.substring(n, s.length()) + p;
	}

	// shifts binary string right by n bits
	public static String logicalRightShift(String s, int n) {
		String p = "";
		for (int i = 0; i < n; i++) {
			p += '0';
		}
		return p + s.substring(0, s.length() - n);
	}

	// keeps the sign
	public static String arithmeticRightShift(String s, int n) {
		String p = "";
		if (s.charAt(0) == '0') {
			for (int i = 0; i < n; i++) {
				p += '0';
			}
		} else {
			for (int i = 0; i < n; i++) {
				p += '1';
			}
		}
		return p + s.substring(0, s.length() - n);
	}

	// takes two's complement of string
	public static String twosComplement(String s) {
		s = onesComplement(s);
		int len = s.length() - 1;
		if (s.charAt(len) == '0') {
			return s.substring(0, len) + '1';
		} else {
			s = s.substring(0, len) + '0';
		}
		for (int i = len - 1; i >= 0; i--) { // start at the end
			if (s.charAt(i) == '0') { // can only be 0 or 1 since passed onesComplement without any errors
				return s.substring(0, i) + '1' + s.substring(i + 1);
			} else {
				s = s.substring(0, i) + '0' + s.substring(i + 1);
			}
		}
		return s;
	}

	// takes one's complement of a string
	public static String onesComplement(String s) {
		char c = s.charAt(0);
		if (c == '0') {
			s = '1' + s.substring(1);
		} else {
			s = '0' + s.substring(1);
		}
		for (int i = 1; i < s.length(); i++) {
			c = s.charAt(i);
			if (c == '0') {
				s = s.substring(0, i) + '1' + s.substring(i + 1);
			} else {
				s = s.substring(0, i) + '0' + s.substring(i + 1);
			}
		}
		return s;
	}

	// removes leading 0's of a string -> good for hex and octal to bin
	public static String removeLeadingZeros(String s) {
		int i = 0;
		while (s.charAt(i) == '0') {
			i++;
		}
		return s.substring(i);
	}

	// checks if a number is valid with its base
	public static int isValidNumber(String s, int b) {
		if (b < 0 || b > 16) { // not a good base
			return -1;
		}
		// 48-57: numbers
		// 65-70: letters
		if (b == 1) { // only allowed 1
			int i = 0;
			if (s.charAt(i) == '-') {
				i++;
			}
			for (; i < s.length(); i++) {
				if (s.charAt(i) != 49) {
					return -2;
				}
			}
		} else if (b <= 10) { // only numbers allowed
			int max = 48 + b - 1; // max height of numbers allowed
			int i = 0;
			if (s.charAt(i) == '-') {
				i++;
			}
			for (; i < s.length(); i++) {
				if (s.charAt(i) < 48 || s.charAt(i) > max) {
					return -2;
				}
			}
		} else { // letters allowed
			int max = 65 + b - 11; // max height of letters allowed
			int i = 0;
			if (s.charAt(i) == '-') {
				i++;
			}
			for (; i < s.length(); i++) {
				if (s.charAt(i) < 48 || (s.charAt(i) > 57 && (s.charAt(i) < 65 || s.charAt(i) > max))) {
					return -2;
				}
			}
		}
		return 0;
	}

	// checks if string is a binary string (not used)
	public static boolean isBinary(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!(c == '0' || c == '1')) {
				return false;
			}
		}
		return true;
	}

	// returns a^b for integers where b is positive
	public static int pow(int a, int b) { // doesn't compute 0^0 correctly which is undefined
		int r = 1;
		for (int i = 0; i < b; i++) {
			r *= a;
		}
		return r;
	}

	public static void print(Object o) {
		System.out.print(o.toString());
	}

	public static void println(Object o) {
		System.out.println(o.toString());
	}

	public static void newline() {
		System.out.println();
	}
}
