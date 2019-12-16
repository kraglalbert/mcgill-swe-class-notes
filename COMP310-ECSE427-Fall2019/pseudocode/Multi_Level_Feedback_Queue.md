# Multi Level Feedback Queue

Exam implementation:

```c
/**
 * We have three cases to deal with:
 * 1. A new job arrives into the MLF queue
 * 2. A job's quantum (time slice) expires
 * 3. A job terminates before its quantum expires
 */
int max = MAX; // the highest queue number (lowest priority)

/* Case 1 */
job_arrival(j) {
    // add the job to the highest priority queue (queue 0)
    enqueue(j);
    if (running_job > 0) {
        preempt_save(running_job);
        running_job = j;
    }
}

/* Case 2 */
quantum_expiry(j) {
    // lower the job's priority
    if (j.queue_number < max_queue_number) {
        push_down(j);
    }
}

/* Case 3 */
job_sleep(j) {
    // increase the job's priority
    if (j.queue_number > 0) {
        push_up(j);
    }
}
```

