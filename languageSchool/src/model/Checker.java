package model;

import java.util.regex.Pattern;

public class Checker {
	public static boolean isValidName (String n)
	{
		String regs="^[A-Z][a-z]*( [A-Z][a-z]{1,})*$";
			return Pattern.matches(regs, n);
	}
	
	public static boolean isValidPhone (String p)
	{
		String regs = "^09[0-9]{8,10}$";
		return Pattern.matches(regs, p);
	}
	public static boolean isValidCourseID (String c)
	{
		String regs = "^C[0-9]{3}$";
		return Pattern.matches(regs, c);
	}
	public static boolean isValidTeacherID (String t)
	{
		String regs = "^T[0-9]{3}$";
		return Pattern.matches(regs, t);
	}
	public static boolean isValidStudent (String s)
	{
		String regs = "^S[0-9]{3}$";
		return Pattern.matches(regs, s);
	}
}
