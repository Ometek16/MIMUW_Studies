#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#include "err.h"


int main(int argc, char* argv[])
{
    int n_children = 5;
    if (argc > 2)
        fatal("Expected zero or one arguments, got: %d.", argc - 1);
    if (argc == 2)
        n_children = atoi(argv[1]);

    printf("My pid is %d, my parent's pid is %d, my children are %d\n", getpid(), getppid(), n_children);

	if (n_children > 0) {
		char napis[5];
		sprintf(napis, "%d", n_children - 1);
		
		pid_t pid;
		ASSERT_SYS_OK(pid = fork());
		if (!pid) {
			ASSERT_SYS_OK(execl("./line", "line", napis, NULL));
		}
		else {
			ASSERT_SYS_OK(wait(NULL));
		}
	}

    printf("My pid is %d, exiting.\n", getpid());

    return 0;
}
