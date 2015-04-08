#ifndef TWEETORADIO_STRUCT_H
#define TWEETORADIO_STRUCT_H

/**
 * Mot clé Communication DIFFUSEUR
 */
#define REGI "REGI"
#define REOK "REOK"
#define RENO "RENO"
#define RUOK "RUOK"
#define IMOK "IMOK"


/**
 * Mot clé Communication CLIENT
 */
#define LIST "LIST"
#define LINB "LINB"
#define ITEM "ITEM"

/**
 * Taille des messages
 */
#define SIZE_ID 8
#define SIZE_IP 15
#define SIZE_PORT 4

#define SIZE_REGI 55
#define SIZE_REOK 4
#define SIZE_RENO 4
#define SIZE_RUOK 4
#define SIZE_IMOK 4
#define SIZE_LIST 4
#define SIZE_LINB 4
#define SIZE_ITEM 4
#define SIZE_NB_DIFF 2




#define DEFAULT_PORT 7895
/**
 *
 */
#define NOMBRE_MAX_DIFFUSEUR 15
typedef struct diffuseur_s{
    char id[SIZE_ID+1];
    char ip1[SIZE_IP+1];
    char ip2[SIZE_IP+1];
    char port1[SIZE_PORT+1];
    char port2[SIZE_PORT+1];
    struct diffuseur_s *next;

}diffuseur_t;

extern diffuseur_t *head;

#endif //TWEETORADIO_STRUCT_H
