/*
 * IPWorks 2022 C++ Edition - Sample Project
 *
 * This sample project demonstrates the usage of IPWorks in a 
 * simple, straightforward way. This is not intended to be a complete 
 * application. Error handling and other checks are simplified for clarity.
 *
 * Copyright (c) 2023 /n software inc. www.nsoftware.com
 */


#include <stdio.h>
#include <string.h>
#include "../../include/ipworks.h"
#define LINE_LEN 100

int main(int argc, char *argv[])
{
	if (argc != 2) {

		fprintf(stderr, "usage: whois domain\n\n");
		fprintf(stderr, "  domain   the name of the domain to get information about\n");
		fprintf(stderr, "\nExample: whois google.com\n\n");
		printf("Press enter to continue.");
		getchar();

	}
	else{
		
		Whois whois;
		int ret_code = 0;

		whois.SetServer("whois.internic.net");
		ret_code = whois.Query(argv[1]);

		if (!ret_code)
		{

			printf(whois.GetDomainInfo());
			printf("This information provided by: %s.\n\n", whois.GetServer());

		}
		else
		{
			printf("error: %d (%s).\n", ret_code, whois.GetLastError());
		}

		printf("Press ENTER to continue...");
		getchar();
		printf("\n");
		return 0;
	}
	
}








