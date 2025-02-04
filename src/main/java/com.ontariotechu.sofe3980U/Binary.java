package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary
{
	private String number="0";  // string containing the binary value '0' or '1'
	/**
	 * A constructor that generates a binary object.
	 *
	 * @param number a String of the binary values. It should contain only zeros or ones with any length and order. otherwise, the value of "0" will be stored.   Trailing zeros will be excluded and empty string will be considered as zero.
	 */
	public Binary(String number) {
		if (number == null || number.isEmpty()) {
			this.number = "0"; // Default to "0" for null or empty input
			return;
		}

		// Validate the binary string (only '0' or '1' allowed)
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (ch != '0' && ch != '1') {
				this.number = "0"; // Default to "0" for invalid input
				return;
			}
		}

		// Remove leading zeros
		int beg;
		for (beg = 0; beg < number.length(); beg++) {
			if (number.charAt(beg) != '0') {
				break;
			}
		}

		// If all digits are '0', ensure number is "0"
		this.number = (beg == number.length()) ? "0" : number.substring(beg);

		// Ensure empty strings are replaced with "0"
		if (this.number.isEmpty()) {
			this.number = "0";
		}
	}
	/**
	 * Return the binary value of the variable
	 *
	 * @return the binary value in a string format.
	 */
	public String getValue()
	{
		return this.number;
	}
	/**
	 * Adding two binary variables. For more information, visit <a href="https://www.wikihow.com/Add-Binary-Numbers"> Add-Binary-Numbers </a>.
	 *
	 * @param num1 The first addend object
	 * @param num2 The second addend object
	 * @return A binary variable with a value of <i>num1+num2</i>.
	 */
	public static Binary add(Binary num1,Binary num2)
	{
		// the index of the first digit of each number
		int ind1=num1.number.length()-1;
		int ind2=num2.number.length()-1;
		//initial variable
		int carry=0;
		String num3="";  // the binary value of the sum
		while(ind1>=0 ||  ind2>=0 || carry!=0) // loop until all digits are processed
		{
			int sum=carry; // previous carry
			if(ind1>=0){ // if num1 has a digit to add
				sum += (num1.number.charAt(ind1)=='1')? 1:0; // convert the digit to int and add it to sum
				ind1--; // update ind1
			}
			if(ind2>=0){ // if num2 has a digit to add
				sum += (num2.number.charAt(ind2)=='1')? 1:0; // convert the digit to int and add it to sum
				ind2--; //update ind2
			}
			carry=sum/2; // the new carry
			sum=sum%2;  // the resultant digit
			num3 =( (sum==0)? "0":"1")+num3; //convert sum to string and append it to num3
		}
		Binary result=new Binary(num3);  // create a binary object with the calculated value.
		return result;

	}

	/**
	 * OR two binary variables. For more information, visit <a href="https://en.wikipedia.org/wiki/Bitwise_operation"> Bitwise_operations </a>.
	 *
	 * @param num1 The first OR object
	 * @param num2 The second OR object
	 * @return A binary variable with a value of <i>num1 OR num2</i>.
	 */
	public static Binary or(Binary num1, Binary num2) {
		// the index of the first digit of each number
		int ind1 = num1.number.length() - 1;
		int ind2 = num2.number.length() - 1;
		String num3 = "";  // the binary value of the result

		while (ind1 >= 0 || ind2 >= 0) { // loop until all digits are processed
			int bit1 = (ind1 >= 0) ? (num1.number.charAt(ind1) == '1' ? 1 : 0) : 0; // Get bit from num1 or 0 if out of bounds
			int bit2 = (ind2 >= 0) ? (num2.number.charAt(ind2) == '1' ? 1 : 0) : 0; // Get bit from num2 or 0 if out of bounds
			int orResult = bit1 | bit2; // Perform bitwise OR
			num3 = ((orResult == 0) ? "0" : "1") + num3; // Convert result to string and prepend to num3
			ind1--; // Update ind1
			ind2--; // Update ind2
		}

		Binary result = new Binary(num3); // Create a binary object with the calculated value
		return result;
	}

	/**
	 * Performs a bitwise AND operation between two binary numbers.
	 * For more information on binary operations, visit <a href="https://en.wikipedia.org/wiki/Bitwise_operation"> Bitwise_operations </a>.
	 *
	 * @param num1 The first binary number
	 * @param num2 The second binary number
	 * @return A binary variable with a value of <i>num1 AND num2</i>.
	 */
	public static Binary and(Binary num1, Binary num2) {
		// the index of the first digit of each number
		int ind1 = num1.number.length() - 1;
		int ind2 = num2.number.length() - 1;
		String num3 = "";  // the binary value of the result

		while (ind1 >= 0 || ind2 >= 0) { // loop until all digits are processed
			int bit1 = (ind1 >= 0) ? (num1.number.charAt(ind1) == '1' ? 1 : 0) : 0; // Get bit from num1 or 0 if out of bounds
			int bit2 = (ind2 >= 0) ? (num2.number.charAt(ind2) == '1' ? 1 : 0) : 0; // Get bit from num2 or 0 if out of bounds
			int andResult = bit1 & bit2; // Perform bitwise AND
			num3 = ((andResult == 0) ? "0" : "1") + num3; // Convert result to string and prepend to num3
			ind1--; // Update ind1
			ind2--; // Update ind2
		}

		Binary result = new Binary(num3); // Create a binary object with the calculated value
		return result;
	}

	/**
	 * Multiplies two binary numbers.
	 * For more information, visit <a href="https://en.wikipedia.org/wiki/Binary_multiplier"> Multiply-Binary-Numbers </a>.
	 *
	 * @param num1 The first binary number (multiplicand)
	 * @param num2 The second binary number (multiplier)
	 * @return A binary variable with a value of <i>num1 * num2</i>.
	 */
	public static Binary multiply(Binary num1, Binary num2) {
		Binary result = new Binary("0"); // Initialize result as "0"

//		if (num1 == 1)
//		{
//			return num2;
//		}
//
//		else if (num2 == 1)
//		{
//			return num1;
//		}

		int shift = 0; // Tracks the position of the multiplier's bit
		for (int i = num2.number.length() - 1; i >= 0; i--) { // Process each bit of num2
			if (num2.number.charAt(i) == '1') { // If the multiplier bit is 1
				String shiftedNum = num1.number + "0".repeat(shift); // Shift the multiplicand by `shift` bits
				Binary partialProduct = new Binary(shiftedNum); // Treat shiftedNum as a Binary object
				result = add(result, partialProduct); // Add the partial product to the result
			}
			shift++; // Increment the shift for the next bit
		}

		return result; // Return the final product
	}


}

