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


if len(sys.argv) != 2:
  print("usage: ping.py host")
  print("")
  print("  host  the IP address (IP number in dotted internet format) or Domain Name of the remote host")
  print("\r\nExample: ping.py www.google.com")
else:
  ping = Ping()

  try:
    ping.set_remote_host(sys.argv[1])
    print("\nPinging %s.\n" %(sys.argv[1]))
    for i in range(1,5):
      ping.set_timeout(15)
      ping.ping_host(sys.argv[1])
      if ping.get_response_time() == 0:
        print("Reply from %s: bytes=%i time=0ms" %(ping.get_remote_host(),ping.get_packet_size()))
      else:
        print("Reply from %s: bytes=%i time=%sms" %(ping.get_remote_host(),ping.get_packet_size(),ping.get_response_time()))

  except IPWorksError as e:
    print("ERROR %s" %e.message)



