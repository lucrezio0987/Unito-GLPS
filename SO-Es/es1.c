#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char **leggi_lista(FILE *file, int *nof_elements) {
  char **lista=malloc(sizeof(char*)*(*nof_elements));
  char temp[30];
  int temp_n;
  for(int i=0; i<(*nof_elements); ++i){
    fgets(temp, 30, file);
    temp_n=strlen(temp);
    temp[temp_n-1]='\0';
    lista[i]=malloc(sizeof(char)*(temp_n));
    strcpy(lista[i],temp);
  }
  return lista;
}
void stampa_lista(char **mio_ar, int n_elems){
  for(int i=0; i<n_elems; ++i)
    printf("%s\n", mio_ar[i]);
  return;
}
int conta(FILE *file) {
  int i=0;
  char temp[30];
  while(fgets(temp, 30, file)!=NULL) ++i;
  return i;
}

int main(){
  FILE *fl;
  int n_elementi;
  char **lista;

  fl=fopen("file.txt", "r");
  n_elementi=conta(fl);
  fclose(fl);

  lista=leggi_lista(fopen("file","r"), &n_elementi);

  stampa_lista(lista, n_elementi);

  exit(0);
}
