#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#include "err.h"


int main() {
	int n_children = 5;
	
	while (n_children >= 0) {
		printf("My pid is %d, my parent's pid is %d, my children are %d\n", getpid(), getppid(), n_children);
		n_children--;
		if (n_children > 0) {
			pid_t pid;
			ASSERT_SYS_OK(pid = fork());
			if (!pid) {
				continue;
			} else {
				// sleep(0.001);
				ASSERT_SYS_OK(wait(NULL));
				break;
			}
		}
	}

	printf("My pid is %d, exiting.\n", getpid());

	return 0;
}
