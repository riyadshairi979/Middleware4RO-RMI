Middleware4RemoteObjects
========================
In this project, two types of remote objects: BasicMathClass and AdvancedMathClass are designed and implemented. Here, BasicMathClass has two basic methods: the magicAdd() method takes two input parameters and returns the difference between the two values; similarly the magicSubtract() method takes two input parameters and returns the sum of the two values. For the AdvancedMathClass, it has another two methods: the magicFindMin() method takes three values as parameters and returns the largest value; and the magicFindMax() method takes three values as parameters and returns the smallest values. Moreover, each object have a counter, which records the number of operations it has performed, and a corresponding method to read this counter. Since the same object is simultaneously accessible from different threads, proper synchronization is applied when updating the counter.
The server side creates two objects of BasicMathClass and two objects of AdvancedMathClass. The server supports multiple threads, where each thread is responsible for handling requests from one client.
On the client side, each client generates 1000 requests, where each request randomly choose one of the objects and one method of the selected object, as well as the corresponding required parameters. At the end, the client reports the number of operations performed by each object.

Install
-------
1. In client project, modify binder host in Client.java
2. In Server project, modify binder host in Server.java
3. Compile and run the binder, server and client respectively
