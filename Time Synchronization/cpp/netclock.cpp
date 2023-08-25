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
#include <stdlib.h>
#include "../../include/ipworks.h"

#include <time.h>
#include <sys/timeb.h>
#define LINE_LEN 100

int main(int argc, char *argv[])
{

	NetClock myClock;
	int ret_code = 0;

	if (ret_code = myClock.SetTimeServer("time.nist.gov")) goto done;
	
	printf("Getting time...");
	
	if (ret_code = myClock.GetTime()) goto done;

	time_t t;
	time(&t);

	printf("\tSystem date and time   : %s\tInternet date and time : %s\t", asctime(localtime(&t)), myClock.GetServerTime());

done:
	if (ret_code)     // Got an error.  The user is done.
	{
		printf("\nError: %d", ret_code);
		if (myClock.GetLastError())
		{
			printf(" \"%s\"\n", myClock.GetLastError());
		}
	}
	printf("\nPress ENTER to continue...");
	getchar();
	exit(ret_code);
	return 0;

}



