# Android Concurency

Android supports Java’s Executor framework which offers the following
classes for using a thread pool.

 ### Executor:
 An interface which has a execute method. It is designed to decouple task
  submission from running.

 ### Callable:
 An Interface similar to runnable but allow a result to be returned.

### Future:
Like a promise in JavaScript. It represents the result for an asynchronous task.

### ExecutorService:
An interface which extends Executor interface. It is used to manage
threads in the threads pool.

### ThreadPoolExecutor:
A class that implements ExecutorService which gives fine control on the
thread pool (Eg, core pool size, max pool size, keep alive time, etc.)

### ScheduledThreadPoolExecutor:
A class that extends ThreadPoolExecutor. It can schedule tasks after a
given delay or periodically.

### Executors:
A class that offers factory and utility methods for the aforementioned classes.

### ExecutorCompletionService:
A class that arranges submitted task to be placed on a queue for accessing results.


### There are two ways of creating threads – one by extending the Thread class
and other by creating a thread with a Runnable. However, one feature lacking in
Runnable is that it cannot make a thread return result when it terminates,
i.e. when run() completes. For supporting this feature, the Callable interface
is present in Java.

### Callable vs Runnable
 For implementing Runnable, the run() method needs to be implemented which does
 not return anything, while for a Callable, the call() method needs to be implemented
  which returns a result on completion.A thread can’t be created with a Callable,
  it can only be created with a Runnable.
 Another difference is that the call() method can throw an exception whereas run() cannot.

 ### Future
 When the call() method completes, answer must be stored in an object known to the
 main thread, so that the main thread can know about the result that the thread
 returned. How will the program store and obtain this result later? For this,
 a Future object can be used. Think of a Future as an object that holds the result –
it may not hold it right now, but it will do so in the future (once the Callable returns).
Thus, a Future is basically one way the main thread can keep track of the progress and
result from other threads.

### Observe that Callable and Future do two different things – Callable is
similar to Runnable,in that it encapsulates a task that is meant to run on
another thread, whereas a Future is used to store a result obtained from
a different thread. In fact, the Future can be made to work with Runnable
as well.

### To create the thread, a Runnable is required. To obtain the result, a Future
### is required.

The Java library has the concrete type FutureTask, which implements Runnable
and Future,combining both functionality conveniently.
A FutureTask can be created by providing its constructor with a Callable.
Then the FutureTask object is provided to the constructor of Thread to create
the Thread object.Thus, indirectly, the thread is created with a Callable.
#### For further emphasis,that there is no way to create the thread directly
#### with a Callable.


 #### A blocking queue is a queue that blocks when it is dequeued and the
 queue is empty, or when items are enqueued  to it and the queue is already full.
 A thread trying to dequeue from an empty queue is blocked until some other thread
 inserts an item into the queue. A thread trying to enqueue an item in a full queue
 is blocked until some other thread makes space in the queue, either by dequeuing
 one or more items or clearing the queue completely.