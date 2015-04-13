#ifndef TWEETORADIO_COMMUNICATION_CLIENT_H
#define TWEETORADIO_COMMUNICATION_CLIENT_H


#include <sys/socket.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#include "struct.h"

void list_diff_to_client(int desc, list_diff_t *listDiffT, int max, pthread_mutex_t *verrou);
#endif //TWEETORADIO_COMMUNICATION_CLIENT_H
