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

def fireHop(e):
  print((str(e.hop_number) + ")").ljust(5) + e.host_address.ljust(20) + str(e.duration))
  #If a hop times out the e.Duration of the fireHop event will be -1

# This demo prints out the path IP packets take from your machine to a specified domain

domain=input("Enter a domain (www.google.com): ")
if domain == "" : domain = "www.google.com"
trace = TraceRoute()
trace.on_hop = fireHop
trace.set_hop_limit(10)

try:
  print("Hop".ljust(5) + "Hop Address".ljust(20) + "Hop Time (ms)")
  trace.trace_to(domain)

except IPWorksTracerouteError as e:
  print("ERROR %s" %e.message)

