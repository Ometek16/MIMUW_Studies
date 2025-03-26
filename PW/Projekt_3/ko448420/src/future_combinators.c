#include "future_combinators.h"
#include <stdlib.h>

#include "future.h"
#include "waker.h"

// Funkcja obsługująca postęp ThenFuture
static FutureState then_future_progress(Future* base, Mio* mio, Waker waker) {
	ThenFuture* self = (ThenFuture*) base;

	// 1. Jeśli fut1 jeszcze się nie zakończyło, próbujemy je wykonać
	if (!self->fut1_completed) {
		FutureState state = self->fut1->progress(self->fut1, mio, waker);

		if (state == FUTURE_FAILURE) {
			base->errcode = THEN_FUTURE_ERR_FUT1_FAILED;
			return FUTURE_FAILURE;
		}

		if (state == FUTURE_COMPLETED) {
			self->fut1_completed = true;
			self->fut2->arg = self->fut1->ok; // Przekazanie wyniku fut1 do fut2
		}
		else {
			return FUTURE_PENDING; // fut1 nadal oczekuje
		}
	}

	// 2. Jeśli fut1 zakończone, wykonujemy fut2
	FutureState state = self->fut2->progress(self->fut2, mio, waker);

	if (state == FUTURE_FAILURE) {
		base->errcode = THEN_FUTURE_ERR_FUT2_FAILED;
		return FUTURE_FAILURE;
	}

	if (state == FUTURE_COMPLETED) {
		base->ok = self->fut2->ok; // Wynik końcowy pochodzi z fut2
		return FUTURE_COMPLETED;
	}

	return FUTURE_PENDING; // fut2 nadal oczekuje
}

// Funkcja tworząca ThenFuture
ThenFuture future_then(Future* fut1, Future* fut2) {
	ThenFuture then_future = {
		.base = future_create(then_future_progress), // Przypisanie funkcji progress
		.fut1 = fut1,
		.fut2 = fut2,
		.fut1_completed = false
	};
	return then_future;
}


static FutureState join_future_progress(Future* base, Mio* mio, Waker waker) {
	JoinFuture* self = (JoinFuture*) base;

	// 1. Jeśli fut1 jeszcze się nie zakończyło, próbujemy je wykonać
	if (!self->fut1_completed) {
		FutureState state = self->fut1->progress(self->fut1, mio, waker);

		if (state == FUTURE_FAILURE) {
			base->errcode |= JOIN_FUTURE_ERR_FUT1_FAILED;
		}

		self->fut1_completed = true;
	}

	// 2. Jeśli fut2 jeszcze się nie zakończyło, próbujemy je wykonać
	if (!self->fut2_completed) {
		FutureState state = self->fut2->progress(self->fut2, mio, waker);

		if (state == FUTURE_FAILURE) {
			base->errcode |= JOIN_FUTURE_ERR_FUT2_FAILED;
		}

		self->fut2_completed = true;
	}

	// 3. Jeśli oba futury zakończone, zwracamy wynik
	if (self->fut1_completed && self->fut2_completed) {
		if (base->errcode) {
			return FUTURE_FAILURE;
		}

		return FUTURE_COMPLETED;
	}

	return FUTURE_PENDING;
}


JoinFuture future_join(Future* fut1, Future* fut2) {
	return (JoinFuture) {
		.base = future_create(join_future_progress),
			.fut1 = fut1,
			.fut2 = fut2,
			.fut1_completed = false,
			.fut2_completed = false
	};
}

static FutureState select_future_progress(Future* base, Mio* mio, Waker waker) {
	SelectFuture* self = (SelectFuture*) base;

	// 1. Jeśli fut1 jeszcze się nie zakończyło, próbujemy je wykonać
	if (self->which_completed == SELECT_COMPLETED_NONE || self->which_completed == SELECT_FAILED_FUT2) {
		FutureState state = self->fut1->progress(self->fut1, mio, waker);

		if (state == FUTURE_FAILURE) {
			if (self->which_completed == SELECT_FAILED_FUT2) {
				self->which_completed = SELECT_FAILED_BOTH;
			}
			else {
				self->which_completed = SELECT_FAILED_FUT1;
			}
		}
		else if (state == FUTURE_COMPLETED) {
			self->which_completed = SELECT_COMPLETED_FUT1;
		}
	}

	// 2. Jeśli fut2 jeszcze się nie zakończyło, próbujemy je wykonać
	if (self->which_completed == SELECT_COMPLETED_NONE || self->which_completed == SELECT_FAILED_FUT1) {
		FutureState state = self->fut2->progress(self->fut2, mio, waker);

		if (state == FUTURE_FAILURE) {
			if (self->which_completed == SELECT_FAILED_FUT1) {
				self->which_completed = SELECT_FAILED_BOTH;
			}
			else {
				self->which_completed = SELECT_FAILED_FUT2;
			}
		}
		else if (state == FUTURE_COMPLETED) {
			self->which_completed = SELECT_COMPLETED_FUT2;
		}
	}

	// 3. Jeśli oba futury zakończone, zwracamy wynik
	if (self->which_completed == SELECT_COMPLETED_FUT1) {
		base->ok = self->fut1->ok;
		return FUTURE_COMPLETED;
	}

	if (self->which_completed == SELECT_COMPLETED_FUT2) {
		base->ok = self->fut2->ok;
		return FUTURE_COMPLETED;
	}

	if (self->which_completed == SELECT_FAILED_BOTH) {
		return FUTURE_FAILURE;
	}

	return FUTURE_PENDING;
}

SelectFuture future_select(Future* fut1, Future* fut2) {
	return (SelectFuture) {
		.base = future_create(select_future_progress),
			.fut1 = fut1,
			.fut2 = fut2,
			.which_completed = SELECT_COMPLETED_NONE
	};
}
