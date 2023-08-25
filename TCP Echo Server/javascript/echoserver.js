/*
 * IPWorks 2022 JavaScript Edition - Sample Project
 *
 * This sample project demonstrates the usage of IPWorks in a 
 * simple, straightforward way. This is not intended to be a complete 
 * application. Error handling and other checks are simplified for clarity.
 *
 * Copyright (c) 2023 /n software inc. www.nsoftware.com
 */
 
const readline = require("readline");
const ipworks = require("@nsoftware/ipworks");

if(!ipworks) {
  console.error("Cannot find ipworks.");
  process.exit(1);
}
let rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

main();


async function main() {
  const argv = process.argv;
  if (argv.length !== 3) {
    console.log("Usage: node echoserver.js port");
    console.log("  port	  the TCP port in the local host where the component listens");
    console.log("Example: node echoserver.js 777");
    return;
  }

  const tcpserver = new ipworks.tcpserver();

  tcpserver.on("SSLClientAuthentication", function (e) {
    e.accept = true;
  }).on("Disconnected", function (e) {
    console.log("\nDisconnected " + e.description + " from " + e.connectionId);
  })
    .on("Connected", function (e) {
      let connections = tcpserver.getConnections();
      let connection = connections.get(e.connectionId);
      console.log(connection.getRemoteHost(e.connectionId) + " has connected.");
    })
    .on("DataIn", function (e) {
      let connections = tcpserver.getConnections();
      let connection = connections.get(e.connectionId);
      console.log("Echoing '" + e.text + "' to client " + connection.getRemoteHost() + ".");
      tcpserver.sendText(e.connectionId, e.text);
    })

  tcpserver.setLocalPort(argv[2]);
  await tcpserver.startListening()

  console.log("Started Listening.");

  while (true) {
    await tcpserver.doEvents(function (err) {
      if (err) {
        console.log(err);
        return;
      }
    });
  }
}



function prompt(promptName, label, punctuation, defaultVal)
{
  lastPrompt = promptName;
  lastDefault = defaultVal;
  process.stdout.write(`${label} [${defaultVal}] ${punctuation} `);
}
