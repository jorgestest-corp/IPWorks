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
#define LINE_LEN 100

class MyMX : public MX
{
public:

	virtual int FireError(MXErrorEventParams *e)
	{
		printf( "%d: %s\n", e->ErrorCode, e->Description );
		return 0;
	}

	virtual int FireResponse(MXResponseEventParams *e)
	{
		if (e->StatusCode) printf( "%s\n",   e->Description );
		else printf( "%s --> %s\n", e->Domain, e->MailServer );
		return 0;
	}
};


int main(int argc, char * argv[])
{
	
	MyMX resolve;
	int ret_code = 0;

	if ( argc != 2 )
	{
		fprintf(stderr, "usage: mx email\n");
		fprintf(stderr, "\n");
		fprintf(stderr, "  email   the email address to resolve\n");
		fprintf(stderr, "\nExample: mx billg@microsoft.com\n\n");
		printf("Press enter to continue.");
		getchar();
	}
	else
	{
		resolve.SetTimeout(10);
		resolve.SetDNSServer("4.2.2.1");
		if (ret_code = resolve.Resolve(argv[1])) goto done;
	}

done:
	if (ret_code)
	{
		printf( "error %d(%s)\n", ret_code, resolve.GetLastError() );
	}  
  fprintf(stderr, "\npress <return> to continue...\n");
  getchar();  
	exit(ret_code);
	return 0;
}









