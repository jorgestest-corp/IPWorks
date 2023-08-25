/*
 * IPWorks 2022 Java Edition - Sample Project
 *
 * This sample project demonstrates the usage of IPWorks in a 
 * simple, straightforward way. This is not intended to be a complete 
 * application. Error handling and other checks are simplified for clarity.
 *
 * Copyright (c) 2023 /n software inc. www.nsoftware.com
 */

import java.io.*;
import ipworks.*;


public class whois extends ConsoleDemo {
	
	public static void main(String[] args) {
		
		if(args.length!=1) {
			
			System.out.println("usage: whois domain");
			System.out.println("");
			System.out.println("  domain the name of the domain to get information about");			
			System.out.println("\r\nExample: whois google.com");
		
		} else {
			
			Whois whois1 = new Whois();
		    try {
		    	
		    	whois1.setServer("whois.internic.net");
		    	whois1.query(args[0]); // domain name

		    	System.out.println(whois1.getDomainInfo());
		    	System.out.println("This information provided by: " + whois1.getServer());
		    	
		    }catch (IPWorksException e) {
		    		displayError(e);
		    }			
		}    	  	  
	}
}

class ConsoleDemo {
  private static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

  static String input() {
    try {
      return bf.readLine();
    } catch (IOException ioe) {
      return "";
    }
  }
  static char read() {
    return input().charAt(0);
  }

  static String prompt(String label) {
    return prompt(label, ":");
  }
  static String prompt(String label, String punctuation) {
    System.out.print(label + punctuation + " ");
    return input();
  }

  static String prompt(String label, String punctuation, String defaultVal)
  {
	System.out.print(label + " [" + defaultVal + "] " + punctuation + " ");
	String response = input();
	if(response.equals(""))
		return defaultVal;
	else
		return response;
  }

  static char ask(String label) {
    return ask(label, "?");
  }
  static char ask(String label, String punctuation) {
    return ask(label, punctuation, "(y/n)");
  }
  static char ask(String label, String punctuation, String answers) {
    System.out.print(label + punctuation + " " + answers + " ");
    return Character.toLowerCase(read());
  }

  static void displayError(Exception e) {
    System.out.print("Error");
    if (e instanceof IPWorksException) {
      System.out.print(" (" + ((IPWorksException) e).getCode() + ")");
    }
    System.out.println(": " + e.getMessage());
    e.printStackTrace();
  }
}



