#ifndef TWEETORADIO_COMMUNICATION_DIFFUSEUR_H
#define TWEETORADIO_COMMUNICATION_DIFFUSEUR_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/poll.h>
#include <fcntl.h>
#include <netinet/in.h>
#include <pthread.h>
#include <netinet/tcp.h>
#include "struct.h"


int add_to_list(list_diff_t *listDiffT, char* buff, int max, pthread_mutex_t *verrou);
void remove_from_list(list_diff_t *listDiffT, int el, pthread_mutex_t *verrou);
int ask_ruok(int desc_socket, list_diff_t *listDiffT, int place, struct pollfd *p);


#endif //TWEETORADIO_COMMUNICATION_DIFFUSEUR_H
