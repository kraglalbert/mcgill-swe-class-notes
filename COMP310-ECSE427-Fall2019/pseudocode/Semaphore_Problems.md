# Semaphore Problems

### Producer-Consumer Problem

Bounded-buffer variation:

```c
sem_t mutex = 1; // protects critical section
sem_t num_empty = N; // counts empty buffer slots
sem_t num_full = 0; // counts filled buffer slots

producer() {
    int item = 1; // any arbitrary value
    
    while (TRUE) {
        item = produce_item();
        wait(&num_empty); // wait for an empty slot to be available
        wait(&mutex); // wait for exclusive access to buffer
        insert_item(item);
        signal(&mutex); // give up exclusive access
        signal(&num_full); // increment number of filled buffer slots
    }
}

consumer() {
    int item;
    
    while (TRUE) {
		wait(&num_full); // wait for at least 1 item to be in the buffer
        wait(&mutex); // wait for exclusive access to buffer
        item = remove_item();
        signal(&mutex); // give up exclusive access
        signal(&num_empty); // increment number of empty buffer slots
        consume_item(item);
    }
}
```

### Readers-Writers Problem

In this variation, the writers will starve.

```c
sem_t rw_mutex = 1; // mutex to ensure reading and writing does not 
					// happen at the same time
sem_t mutex = 1; // mutex to ensure exclusive access to read_count
int read_count = 0; // keeps track of how many readers are currently reading

write() {
    int item = 1;
    
	wait(&rw_mutex);
    write(item);
    signal(&rw_mutex);
}

read() {
    int item;
    
    wait(&mutex); // get exclusive access to read_count variable
    read_count++;
    if (read_count == 1) {
        wait(&rw_mutex); // make writers wait for readers to finish
    }
    signal(&mutex);
    
    item = read();
    
    wait(&mutex); // get exclusive access to read_count variable
    read_count--;
    if (read_count == 0) {
        signal(&rw_mutex); // all readers finished
    }
    signal(&mutex);
    
}
```

### Dining Philosophers Problem

Semaphore solution:

```c
sem_t chopsticks[5];

while (TRUE) {
    wait(chopsticks[i]);
    wait(chopsticks[(i+1) % 5]);
    
    /* eat */
    
    signal(chopsticks[i]);
    signal(chopsticks[(i+1) % 5]);
    
    /* think about philosophical things */
}
```

