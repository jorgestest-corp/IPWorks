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


def fireSSLServerAuthentication(e):
  if e.accept: return
  print("Subject %s\nIssuer %s"%(e.cert_subject, e.cert_issuer))
  print("The following problems have been determined for this certificate: %s"%e.status)
  print("Do you want to accept this certificate? Y/N")
  selection = sys.stdin.read(1)
  if (selection == 'Y' or selection == 'y'): e.accept = 1

def fireTransfer(e): print(e.text)
def fireError(e): print("Error: %s\n"%e.description)

def ensureArg(argument, prompt, index):
  if len(argument) <= index:
    while len(argument) <= index:
      argument.append(None)
    argument[index] = input(prompt)

http = HTTP()

http.on_error = fireError
http.on_ssl_server_authentication = fireSSLServerAuthentication
http.on_transfer = fireTransfer
http.set_follow_redirects(True)

ensureArg(sys.argv, "URL: ", 1)
try:
  http.get(sys.argv[1])
except IPWorksHttpError as e: 
  print(e)
  
  




