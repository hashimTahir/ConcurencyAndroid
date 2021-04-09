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