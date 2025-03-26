#include "executor.h"

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "debug.h"
#include "future.h"
#include "mio.h"
#include "waker.h"

/**
 * @brief Structure to represent the current-thread executor.
 */
struct Executor {
	size_t queue_max_size;
	size_t queue_start;
	size_t queue_size;
	int tasks;
	Mio* mio;
	Future* queue[];
};


Executor* executor_create(size_t max_queue_size) {
	Executor* executor = malloc(sizeof(Executor) + max_queue_size * sizeof(Future*));
	if (executor == NULL) {
		return NULL;
	}

	executor->mio = mio_create(executor);
	if (executor->mio == NULL) {
		free(executor);
		return NULL;
	}

	executor->queue_max_size = max_queue_size;
	executor->queue_start = 0;
	executor->queue_size = 0;
	executor->tasks = 0;
	return executor;
}

void waker_wake(Waker* waker) {
	Executor* executor = (Executor*) waker->executor;
	executor->tasks--; // replace the ghost with the actual task
	Future* fut = waker->future;
	executor_spawn(executor, fut);
}

void executor_spawn(Executor* executor, Future* fut) { // add to the end of the queue
	int idx = (executor->queue_start + executor->queue_size) % executor->queue_max_size;
	executor->queue[idx] = fut;
	executor->queue_size++;
	executor->tasks++;
	fut->is_active = true;
}

void executor_run(Executor* executor) {
	while (executor->tasks > 0) {
		if (executor->queue_size == 0) {
			mio_poll(executor->mio);
		}

		// pop from the front of the queue
		Future* fut = executor->queue[executor->queue_start];
		executor->queue_start = (executor->queue_start + 1) % executor->queue_max_size;
		executor->queue_size--;
		executor->tasks--;

		Waker waker = { executor, fut };
		FutureState state = fut->progress(fut, executor->mio, waker);
		if (state == FUTURE_PENDING) {
			// Add a ghost.
			executor->tasks++;
		}
		else {
			// The future is completed or failed.
			fut->is_active = false;
		}
	}
}

void executor_destroy(Executor* executor) {
	mio_destroy(executor->mio);
	free(executor);
}
