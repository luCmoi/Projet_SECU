#ifndef TWEETORADIO_STRUCT_H
#define TWEETORADIO_STRUCT_H

/**
 * Mot clé Communication DIFFUSEUR
 */
#define REGI "REGI"
#define REOK "REOK\r\n"
#define RENO "RENO\r\n"
#define RUOK "RUOK\r\n"
#define IMOK "IMOK\r\n"


/**
 * Mot clé Communication CLIENT
 */
#define LIST "LIST"
#define LINB "LINB"
#define ITEM "ITEM"
#define END_LINE "\r\n"


/**
 * Taille des messages
 */
#define SIZE_ID 8
#define SIZE_IP 15
#define SIZE_PORT 4

#define SIZE_REGI 57
#define SIZE_REOK 6
#define SIZE_RENO 6
#define SIZE_RUOK 6
#define SIZE_IMOK 6
#define SIZE_LIST 4
#define SIZE_LINB 9
#define SIZE_ITEM 57
#define SIZE_NB_DIFF 2




#define DEFAULT_PORT 7897
/**
 *
 */
#define NOMBRE_MAX_DIFFUSEUR 10
typedef struct diffuseur_s{
    char id[SIZE_ID+1];
    char ip1[SIZE_IP+1];
    char ip2[SIZE_IP+1];
    char port1[SIZE_PORT+1];
    char port2[SIZE_PORT+1];
}diffuseur_t;

typedef struct list_diff_s{
    diffuseur_t **liste;
    int first;
    int nombre;
} list_diff_t;


#endif //TWEETORADIO_STRUCT_H
