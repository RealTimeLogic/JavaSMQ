# Java and Android SMQ Client Library and Example

[SMQ](https://realtimelogic.com/products/simplemq/), based on the publish - subscribe pattern, provides features similar to other pub/sub protocols such as MQTT. However, SMQ extends the pub/sub pattern with additional features such as one-to-one messaging and sender's address, features typically required in device management.

**The Java SMQ client library is designed for standard Java and Android.**

Note that the SMQ protocol supports both non secure and secure (TLS) connections, however, the Java SMQ client library is designed to operate only over a secure TLS connection. The TLS connection used by Java SMQ is managed by the Java platform's TLS functionality.


To get started, we offer a basic Java Swing application that connects to a public test broker and lets you control LEDs via a user-friendly Swing UI. Check out the following animated GIF to see the application in action:

![Java SMQ Example](doc/javasmq.gif)
# Compiling the Included Java Swing Example

This example is designed for [Swing](https://en.wikipedia.org/wiki/Swing_(Java)) and. An Android example is also provided (see below).

You may include the SMQ Java code in your Java build, but do not include the file RTL/SMQ/AndroidSMQ.java since this file is designed for Android.

As shown in the above image, you can run the example application from the command line with these simple commands:

```
git clone https://github.com/RealTimeLogic/JavaSMQ.git
cd JavaSMQ
javac LedSMQ.java
java LedSMQ
```

The Swing example connects to the [public SMQ test broker](https://simplemq.com/m2m-led/). You may control any of the connected devices, or you can run a simulated device by using the [online device C example](https://repl.it/@RTL/SMQ-LED-Demo). Note that the Swing example also opens a browser window and redirects the browser to the LED example's HTML5 UI. This makes it easy for you to see how the Swing UI example can be synchronized in real time with the HTML5 UI example.

See the [SMQ LED example's tutorial](https://makoserver.net/articles/Browser-to-Device-LED-Control-using-SimpleMQ) for more information on how this example works.

# Compiling for Android

You may include the SMQ java code in your Android build, but do not include the file RTL/SMQ/SwingSMQ.java since this file is designed for Swing.

A ready-to-use Android example can be downloaded from the [SMQ source code home page](https://realtimelogic.com/products/simplemq/src/) . A pre compiled example is available on [Google Play](https://play.google.com/store/apps/details?id=demo.smq_android).

# SMQ Library Source Code

The SMQ Java library can be found in RTL/SMQ. Depending on what environment you are building for, delete RTL/SMQ/AndroidSMQ.java or RTL/SMQ/SwingSMQ.java.

# Example Source Code

* LedSMQ.java: the Swing LED example.
* org/json: the JSON library used by LedSMQ.java.
* eu/hansolo/steelseries: provides LED UI for LedSMQ.java.

**Note:** the Java SMQ library is not using any of the above and you should only include RTL/SMQ in your own build.

# License

The source code is released under the **Eclipse Public License - V 2.0**: [https://www.eclipse.org/legal/epl-v20.html](https://www.eclipse.org/legal/epl-v20.html)

You may compile a Program licensed under the EPL without modification and commercially license the result in accordance with the [terms of the EPL](https://www.eclipse.org/legal/epl-2.0/faq.php).

This Source Code may also be made available under the following Secondary Licenses when the conditions for such availability set forth in the Eclipse Public License, v. 2.0 are satisfied: **GNU General Public License, version 2**.
