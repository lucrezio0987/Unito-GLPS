void insert_skiplist_OLD(struct SkipList *list, void *item) {
    size_t h, i;
    struct Node *p = list->head;
    struct Node **update = (struct Node **) calloc(1, sizeof(struct Node *));;

    /* crea il nuovo nodo */
    struct Node *new_node = create_node(2,item);

    /* ricerca il punto di inserimento */
    for (h = list->max_level; h > 0; h--) {
      while (p->size>h && p->next[h-1] != NULL && list->compare(item, p->next[h-1]->item) > 0) 
        p = p->next[h-1];
      update[h-1] = p;
    }
    p = p->next[h];
    
    /* controlla se l'elemento è già presente */
    if (p != NULL && list->compare(item, p->item) == 0) return;

    /* aggiorna i puntatori */
    if (new_node->size > list->max_level) 
      list->max_level = new_node->size;
    for (i = 0; i < new_node->size; i++) {
      new_node->next[i] = update[i]->next[i];
      update[i]->next[i] = new_node;
    }

    free(update);
}

void insert_skiplist_x(struct SkipList *list, void *item){
  Node *New_Node = create_node(1, item);
  
  if( list->head == NULL) {
    list->head = New_Node;
    return;
  } 
  if(list->compare(item, list->head->item) < 0){
      New_Node->next[0] = list->head;
      list->head = New_Node;
      return;
  } 
  
  Node *Attuale = list->head;

  if(New_Node->size > list->max_level)
    list->max_level = New_Node->size;

  for(int k = list->max_level - 1; k >= 0; --k) {
    while (Attuale->next[k] != NULL || list->compare(item, Attuale->next[k]->item) >= 0)
      Attuale = Attuale->next[k];
    if (k <= New_Node->size-1) {
      New_Node->next[k] = Attuale->next[k];
      Attuale->next[k] = New_Node;
    }
  }
}

void insert_skiplist(struct SkipList *list, void *item) {
  Node *New_Node = create_node(random_level(list->max_height), item);
  
  if( list->head == NULL) {
    list->head = New_Node;
    return;
  } 
  if(list->compare(item, list->head->item) < 0){
      New_Node->next[0] = list->head;
      list->head = New_Node;
      return;
  } 
  
  Node *Attuale = list->head;

  if(New_Node->size > list->max_level)
    list->max_level = New_Node->size;

  //for(int k = list->max_level - 1; k >= 0; --k) {
  //  if(Attuale->next[k] == NULL || list->compare(item, Attuale->next[k]->item) < 0){
  //    New_Node->next[k] = Attuale->next[k];
  //    Attuale->next[k] = New_Node;
  //  } else 
  //    if(Attuale->next[k] == NULL || list->compare(item, Attuale->next[k]->item) < 0) {
  //      if(k < Attuale->size){
  //        New_Node->next[k] = Attuale->next[k];
  //        Attuale->next[k] = New_Node;
  //      } else {
  //        Attuale = Attuale->next[k];
  //        ++k;
  //      } 
  //    }
  //}

  for (int i = list->max_level - 1; i >= 0; --i) {
    while (Attuale->next[i] != NULL && list->compare(Attuale->next[i]->item, item) < 0)
      Attuale = Attuale->next[i];
    if (i < Attuale->size) {
      New_Node->next[i] = Attuale->next[i];
      Attuale->next[i] = New_Node;
    }
  }
}

const void* search_skiplist_OLD(struct SkipList *list, void *item) {
  if(list_is_empty(list) == 1) return NULL;

  size_t h;
  
  /* cerca l'elemento */
  struct Node *p = list->head;
  for (h = list->max_level; h > 0; h--) {
      while (p->next[h-1] != NULL && list->compare(item, p->next[h-1]->item) > 0) {
          p = p->next[h-1];
      }
  }
  p = p->next[0];
  /* restituisce il risultato */
  if (p != NULL && list->compare(item, p->item) == 0) {
      return p->item;
  } else {
      return NULL;
  }

}