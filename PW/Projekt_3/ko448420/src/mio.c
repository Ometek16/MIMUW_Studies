#include "mio.h"

#include <errno.h>
#include <stdint.h>
#include <stdlib.h>
#include <sys/epoll.h>
#include <unistd.h>

#include "debug.h"
#include "executor.h"
#include "waker.h"

// Maximum number of events to handle per epoll_wait call.
#define MAX_EVENTS 64

// najbrzydsze rozwianie swiata, ale działa???
typedef struct fd_waker_entry {
	int fd;
	Waker* waker;
	struct fd_waker_entry* next;
} fd_waker_entry;

struct Mio {
	int epoll_fd;
	Executor* executor;
	fd_waker_entry* head;
};

Mio* mio_create(Executor* executor) {
	int epoll_fd = epoll_create1(0);
	if (epoll_fd == -1) {
		return NULL;
	}

	Mio* mio = malloc(sizeof(Mio));
	if (mio == NULL) {
		close(epoll_fd);
		return NULL;
	}

	mio->epoll_fd = epoll_fd;
	mio->executor = executor;
	mio->head = NULL;
	return mio;
}

void mio_destroy(Mio* mio) {
	close(mio->epoll_fd);
	free(mio);
}

void mio_store_waker(Mio* mio, int fd, Waker* waker) {
	fd_waker_entry* entry = malloc(sizeof(fd_waker_entry));
	if (entry == NULL) {
		return;
	}
	entry->fd = fd;
	entry->waker = waker;
	// dodaj do LL
	entry->next = mio->head;
	mio->head = entry;
}

Waker* mio_get_waker(Mio* mio, int fd) {
	// znajdz w LL
	fd_waker_entry* entry = mio->head;
	while (entry) {
		if (entry->fd == fd) {
			return entry->waker;
		}
		entry = entry->next;
	}
	// błagam nie wykonaj sie XD
	return NULL;
}

void mio_remove_waker(Mio* mio, int fd) {
	// usun z LL
	fd_waker_entry** prev = &mio->head;
	fd_waker_entry* entry = mio->head;
	while (entry) {
		if (entry->fd == fd) {
			*prev = entry->next;
			free(entry);
			return;
		}
		prev = &entry->next;
		entry = entry->next;
	}
}

int mio_register(Mio* mio, int fd, uint32_t events, Waker waker) {
	debug("Registering (in Mio = %p) fd = %d\n", mio, fd);

	struct epoll_event ev;
	ev.events = events;

	// ugh... malloc
	Waker* waker_copy = malloc(sizeof(Waker));
	if (waker_copy == NULL) {
		return -1;
	}
	*waker_copy = waker;
	ev.data.ptr = waker_copy;

	if (epoll_ctl(mio->epoll_fd, EPOLL_CTL_ADD, fd, &ev) == -1) {
		free(waker_copy);
		return -1;
	}

	mio_store_waker(mio, fd, waker_copy);

	return 0;
}

int mio_unregister(Mio* mio, int fd) {
	debug("Unregistering (from Mio = %p) fd = %d\n", mio, fd);

	Waker* waker = mio_get_waker(mio, fd);
	if (waker == NULL) {
		errno = ENOENT;
		return -1;
	}

	if (epoll_ctl(mio->epoll_fd, EPOLL_CTL_DEL, fd, NULL) == -1) {
		return -1;
	}

	mio_remove_waker(mio, fd);
	free(waker);

	return 0;
}


void mio_poll(Mio* mio) {
	debug("Mio (%p) polling\n", mio);

	struct epoll_event events[MAX_EVENTS];

	int n_events = epoll_wait(mio->epoll_fd, events, MAX_EVENTS, -1);
	if (n_events == -1) {
		if (errno == EINTR) {
			return;
		}
		perror("epoll_wait failed");
		exit(1);
	}

	for (int i = 0; i < n_events; i++) {
		Waker* waker = (Waker*) events[i].data.ptr;
		waker_wake(waker);
	}
}
