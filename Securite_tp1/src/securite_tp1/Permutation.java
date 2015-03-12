public class Permutation implements Code {


    @Override
    public char chiffre(char c, int cle) {
        int fin = (int)c;
        if((int)c+cle<=122){
            return (char)((int)c+cle);
        }
        else{
            int tmp = 0;
            while(fin+tmp<=122){
                tmp +=1;
            }
            fin += tmp;
            tmp = cle - tmp;
            fin -= 26;
            fin += tmp;
        }
        return (char)fin;
    }

    @Override
    public String chiffre(String s, String cle) {
        char[] tab = new char[26];
        String result = "";
        for(int i = 0;i < cle.length();i++){
            tab[i] = cle.charAt(i);
        }
        for(int i = 0;i < s.length();i++){
            switch (s.charAt(i)) {
                case 'a':  result += tab[0];
                    break;
                case 'b':  result += tab[1];
                    break;
                case 'c':  result += tab[2];
                    break;
                case 'd':  result += tab[3];
                    break;
                case 'e':  result += tab[4];
                    break;
                case 'f':  result += tab[5];
                    break;
                case 'g':  result += tab[6];
                    break;
                case 'h':  result += tab[7];
                    break;
                case 'i':  result += tab[8];
                    break;
                case 'j':  result += tab[9];
                    break;
                case 'k':  result += tab[10];
                    break;
                case 'l':  result += tab[11];
                    break;
                case 'm':  result += tab[12];
                    break;
                case 'n':  result += tab[13];
                    break;
                case 'o':  result += tab[14];
                    break;
                case 'p':  result += tab[15];
                    break;
                case 'q':  result += tab[16];
                    break;
                case 'r':  result += tab[17];
                    break;
                case 's':  result += tab[18];
                    break;
                case 't':  result += tab[19];
                    break;
                case 'u':  result += tab[20];
                    break;
                case 'v':  result += tab[21];
                    break;
                case 'w':  result += tab[22];
                    break;
                case 'x':  result += tab[23];
                    break;
                case 'y':  result += tab[24];
                    break;
                case 'z':  result += tab[25];
                    break;
                default: result+= " ";
                    break;
            }
        }
        System.out.println("resultat : " + result);
        return result;
    }

    @Override
    public char dechiffre(char c, int cle) {
        int fin = (int)c;
        if((int)c-cle >= 97){
            return (char)((int)c-cle);
        }
        else{
            int tmp = 0;
            while(fin-tmp >= 97){
                tmp += 1;
            }
            fin -= tmp;
            tmp = cle - tmp;
            fin += 26;
            fin -= tmp;
        }
        return (char)fin;
    }

    @Override
    public String dechiffre(String s, String cle) {
        int[] tab = new int[26];
        boolean espace = true;
        String result = "";
        for(int i = 0;i < s.length();i++){
            for(int j = 0;j < cle.length();j++){
                if(s.charAt(i) == cle.charAt(j)){
                    switch(j){
                        case 0: result+='a';
                            break;
                        case 1: result+='b';
                            break;
                        case 2: result+='c';
                            break;
                        case 3: result+='d';
                            break;
                        case 4: result+='e';
                            break;
                        case 5: result+='f';
                            break;
                        case 6: result+='g';
                            break;
                        case 7: result+='h';
                            break;
                        case 8: result+='i';
                            break;
                        case 9: result+='j';
                            break;
                        case 10: result+='k';
                            break;
                        case 11: result+='l';
                            break;
                        case 12: result+='m';
                            break;
                        case 13: result+='n';
                            break;
                        case 14: result+='o';
                            break;
                        case 15: result+='p';
                            break;
                        case 16: result+='q';
                            break;
                        case 17: result+='r';
                            break;
                        case 18: result+='s';
                            break;
                        case 19: result+='t';
                            break;
                        case 20: result+='u';
                            break;
                        case 21: result+='v';
                            break;
                        case 22: result+='w';
                            break;
                        case 23: result+='x';
                            break;
                        case 24: result+='y';
                            break;
                        case 25: result+='z';
                            break;
                        default: result+=' ';
                            break;
                    }
                    break;
                }
            }
            if(s.charAt(i) == ' '){
                result+=' ';
            }
        }
        System.out.println("resultat : " + result);
        return result;
    }

    @Override
    public String decrypt(String s) {
        int[] tab = new int[26];
        char[] tab2 = new char[26];
        tab2[0] = 'a';
        tab2[1] = 'b';
        tab2[2] = 'c';
        tab2[3] = 'd';
        tab2[4] = 'e';
        tab2[5] = 'f';
        tab2[6] = 'g';
        tab2[7] = 'h';
        tab2[8] = 'i';
        tab2[9] = 'j';
        tab2[10] = 'k';
        tab2[11] = 'l';
        tab2[12] = 'm';
        tab2[13] = 'n';
        tab2[14] = 'o';
        tab2[15] = 'p';
        tab2[16] = 'q';
        tab2[17] = 'r';
        tab2[18] = 's';
        tab2[19] = 't';
        tab2[20] = 'u';
        tab2[21] = 'v';
        tab2[22] = 'w';
        tab2[23] = 'x';
        tab2[24] = 'y';
        tab2[25] = 'z';
        for(int i = 0;i < s.length();i++){
            switch (s.charAt(i)) {
                case 'a':  tab[0] += 1;
                    break;
                case 'b':  tab[1] += 1;
                    break;
                case 'c':  tab[2] += 1;
                    break;
                case 'd':  tab[3] += 1;
                    break;
                case 'e':   tab[4] += 1;
                    break;
                case 'f':   tab[5] += 1;
                    break;
                case 'g':   tab[6] += 1;
                    break;
                case 'h':   tab[7] += 1;
                    break;
                case 'i':   tab[8] += 1;
                    break;
                case 'j':   tab[9] += 1;
                    break;
                case 'k':   tab[10] += 1;
                    break;
                case 'l':   tab[11] += 1;
                    break;
                case 'm':   tab[12] += 1;
                    break;
                case 'n':   tab[13] += 1;
                    break;
                case 'o':   tab[14] += 1;
                    break;
                case 'p':   tab[15] += 1;
                    break;
                case 'q':   tab[16] += 1;
                    break;
                case 'r':   tab[17] += 1;
                    break;
                case 's':   tab[18] += 1;
                    break;
                case 't':   tab[19] += 1;
                    break;
                case 'u':   tab[20] += 1;
                    break;
                case 'v':   tab[21] += 1;
                    break;
                case 'w':   tab[22] += 1;
                    break;
                case 'x':   tab[23] += 1;
                    break;
                case 'y':   tab[24] += 1;
                    break;
                case 'z':   tab[25] += 1;
                    break;
                default:
                    break;
            }
        }
        triBulleDecroissant(tab,tab2);
        for(int i = 0;i < tab.length; i++){
            System.out.println("case : " + tab[i] + " ; " + tab2[i]);
        }
        return null;
    }




    public static void triBulleDecroissant(int tableau[],char tableau2[]) {
        int longueur = tableau.length;
        int tampon = 0;
        char tampon2 = ' ';
        boolean permut;

        do {
            // hypothèse : le tableau est trié
            permut = false;
            for (int i = 0; i < longueur - 1; i++) {
                // Teste si 2 éléments successifs sont dans le bon ordre ou non
                if (tableau[i] < tableau[i + 1]) {
                    // s'ils ne le sont pas, on échange leurs positions
                    tampon = tableau[i];
                    tampon2 = tableau2[i];
                    tableau[i] = tableau[i + 1];
                    tableau2[i] = tableau2[i + 1];
                    tableau[i + 1] = tampon;
                    tableau2[i+1] = tampon2;
                    permut = true;
                }
            }
        } while (permut);
    }
}
