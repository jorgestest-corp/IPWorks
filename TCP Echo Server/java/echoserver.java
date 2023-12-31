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

public class echoserver extends ConsoleDemo {

	private static Tcpserver tcpserver1;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage: echoserver port");
			System.out.println("");
			System.out.println("  port    the TCP port in the local host where the component listens");
			System.out.println("\r\nExample: echoserver 777");
		} else {
			try {
				tcpserver1 = new Tcpserver();
				System.out.println("*****************************************************************************************");
				System.out.println("* This demo shows how to set up an echo server on your computer. By default, the server *");
				System.out.println("* will operate in plaintext. If SSL is desired, set SSLCert and SSLStartMode.           *");
				System.out.println("*****************************************************************************************\n");

				tcpserver1.addTcpserverEventListener(new DefaultTcpserverEventListener() {
					public void SSLClientAuthentication(TcpserverSSLClientAuthenticationEvent arg0) {
						arg0.accept = true;
					}

					public void connected(TcpserverConnectedEvent e) {
						ConnectionMap connections = tcpserver1.getConnections();
						Connection connection = (Connection) connections.get(e.connectionId);
						System.out.println(connection.getRemoteHost() + " has connected.");
						System.out.print(">");
						try {
							connection.setEOL("\r\n");
						} catch (IPWorksException e1) {
						}
					}

					public void dataIn(TcpserverDataInEvent e) {
						try {
							ConnectionMap connections = tcpserver1.getConnections();
							Connection connection = (Connection) connections.get(e.connectionId);
							connection.setDataToSend(e.text);
							System.out.println("Echoing '" + new String(e.text) + "' to client " + connection.getRemoteHost() + ".");
							System.out.print(">");
						} catch (IPWorksException e1) {
							e1.printStackTrace();
						}
					}

					public void disconnected(TcpserverDisconnectedEvent e) {
						System.out.println("Disconnected " + e.description + " from " + e.connectionId + ".");
						System.out.print(">");
					}
				});

				tcpserver1.setLocalPort(Integer.parseInt(args[0]));
				tcpserver1.setListening(true);

				System.out.println("\r\nStarted Listening.");
				System.out.println("\r\nPlease input command: \r\n- 1 Send Data \r\n- 2 Exit");
				System.out.print(">");

				while (true) {
					if (System.in.available() > 0) {
						String command = String.valueOf(read());
						if ("1".equals(command)) {
							String text = prompt("Please input sending data");
							ConnectionMap connections = tcpserver1.getConnections();
							Object[] keys = connections.keySet().toArray();
							if (keys.length > 0) {
								for (int i = 0; i < keys.length; i++) {
									Connection connection = (Connection) connections.get(keys[i]);
									connection.setDataToSend(text);
								}
								System.out.println("Sending success.");
							} else {
								System.out.println("\r\nNo connected client.");
							}
							System.out.println("\r\nPlease input command: \r\n- 1 Send Data \r\n- 2 Exit");
							System.out.print(">");
						} else if ("2".equals(command)) {
							break;
						}
					}
				}
				tcpserver1.setListening(false);
				tcpserver1.shutdown();
				System.out.println(">Stopped Listening.");
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
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



