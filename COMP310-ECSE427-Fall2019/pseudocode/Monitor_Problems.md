# Monitor Problems

### Monitor Implementation with Semaphores

Functions in a monitor:

```c
sem_t mutex = 1;
sem_t next = 0;
int next_count = 0;

/* replace every function F with this */
wait(&mutex);
/* body of F */

if (next) {
    signal(&next);
} else {
    signal(&mutex);
}
```

Condition variables:

```c
sem_t x_sem = 0;
int x_count = 0;

wait() {
    x_count++;
    if (next_count > 0) {
        signal(&next);
    } else {
        signal(&mutex);
    }
    wait(&x_sem);
    x_count--;
}

signal() {
    if (next_count > 0) {
        next_count++;
        signal(&x_sem);
        wait(&next);
        next_count--;
    }
}
```

### Bounded-Buffer Problem

```c
monitor ProducerConsumer {
    cond_var full;
    cond_var empty;
    int count = 0;
	
    /* Monitor functions */
    void produce(int item) {
        if (count == N) wait(&full);      // buffer is full, block
        put_item(item);                   // put item in buffer
        count++;                          // increment count of full slots
        if (count == 1) signal(&empty);   // if buffer was empty, wake consumer
    }

    int remove() {
        if (count == 0) wait(&empty);     // if buffer is empty, block
        int item = remove_item();         // remove item from buffer
        count--;                          // decrement count of full slots
        if (count == N-1) signal(&full);  // if buffer was full, wake producer
        return item;
    }
}

/* Producer and Consumer processes */
Producer() {
    while (TRUE) {
        int item;
        item = make_item();        	       
        ProducerConsumer.produce(item);
    }
}

Consumer() {
    while (TRUE) {
        int item;
        item = ProducerConsumer.consume();
        consume_item(item);
    }
}
```

### Dining Philosophers Problem

Monitor solution:

```c
monitor DiningPhilosophers {
    enum { THINKING, HUNGRY, EATING } state[5];
    cond_var philosophers[5];
    
    /**
     * Each philosopher calls the functions in the following order:
     *
     * DiningPhilosophers.pickup(i);
     *   EAT
     * DiningPhilosophers.putdown(i);
     */
    void pickup(int i) {
		state[i] = HUNGRY;
        test(i);
        if (state[i] != EATING) {
            philosophers[i].wait();
        }
    }
    
    void putdown(int i) {
        state[i] = THINKING;
        // test left and right neightbours
        test((i-1) % 5);
        test((i+1) % 5);
    }
    
    void test(int i) {
        if ((state[(i-1) % 5] != EATING) && 
            (state[i] == HUNGRY) && 
            (state[(i+1) % 5] != EATING)) 
        {
            state[i] = EATING;
            philosophers[i].signal();
        }
    }
    
    void initialization_code() {
        for (int i = 0; i < 5; i++) {
            state[i] = THINKING;
        }
    }
}
```
