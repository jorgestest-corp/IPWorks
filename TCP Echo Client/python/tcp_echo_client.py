# 
# IPWorks 2022 Python Edition - Sample Project
# 
# This sample project demonstrates the usage of IPWorks in a 
# simple, straightforward way. This is not intended to be a complete 
# application. Error handling and other checks are simplified for clarity.
# 
# Copyright (c) 2023 /n software inc. www.nsoftware.com
# 

import sys
import string
from ipworks import *

input = sys.hexversion<0x03000000 and raw_input or input


dataReceived = False

def fireError(e):
  print("ERROR: " %e.description)

def fireSSLServerAuthentication(e):
  e.accept = True

def fireDataIn(e):
  global dataReceived
  dataReceived = True
  print ("Received the data: " + bytes.decode(e.text))

print("*****************************************************************")
print("* This is a demo to show how to connect to a remote echo server *")
print("* to send data, and receive the echoed response.                *")
print("*****************************************************************")

if len(sys.argv) != 3:
  print("usage: tcp_echo_client.py server port")
  print("")
  print("  server  the address of the remote host")
  print("  port    the TCP port in the remote host")
  print("\r\nExample: tcp_echo_client.py localhost 777")
else:
  try:
    tcpclient = TCPClient()
    tcpclient.set_timeout(10)
    tcpclient.on_data_in = fireDataIn
    tcpclient.on_ssl_server_authentication = fireSSLServerAuthentication
    tcpclient.connect_to(sys.argv[1], int(sys.argv[2]))
    while True:
      command = int(input("Please input command: 1 [Send Data] or  2 [Exit]: "))
      if command == 1:
        send = input("Please enter data to send: ")
        tcpclient.send(bytes(send, 'utf-8'))    
        dataReceived = False
        print("Now waiting for response...")
        while dataReceived == False:
          tcpclient.do_events()
      else:
        tcpclient.disconnect()
        break
      
  except IPWorksError as e:
    print("ERROR: %s" % e.message)



