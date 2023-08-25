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

def fireSearchResult(e):
  print("Result DN: " + e.dn)

if len(sys.argv) != 3:
  print("usage: ldap_search.py server search")
  print("")
  print("  server  the name or address of the LDAP server")
  print("  search  the parameters to search for")
  print("\r\nExample: ldap_search.py ldap.umich.edu cn=* ")
else:
  try:
    ldap = LDAP()
    ldap.on_search_result = fireSearchResult

    ldap.set_server_name(sys.argv[1])
    ldap.set_dn("dc=umich, dc=edu")

    ldap.bind()
    if ldap.get_result_code() == 0:
      print("Connected!")
      ldap.set_timeout(10)
      ldap.search(sys.argv[2])
    else:
      print("Bind Failed: " + str(ldap.get_result_code()) + " : " + ldap.get_result_description())

  except IPWorksError as e:
    print("ERROR %s" %e.message)  




