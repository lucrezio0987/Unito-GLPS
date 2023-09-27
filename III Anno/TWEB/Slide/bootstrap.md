## Boostrap
[cos'è] è un "frontend-framework" che permette di creare un ambiente protetto per lo sviluppo di CSS (anche per operazioni complicate).
 - è una _collezione di elementi_
 - viene inserito _diretatmetne in HTML_

 [Responsive design]: strutturato per gestire le pagine rispetto ai relativi viewport (es: descktop, smartphone, ...)

### Container
 - Boostrap è organizzato intorno al concetto di Container.
 [tipo]:
  1. __Standard__
  2.__Fluid__ (padding left-right: 0px)

  (Ogni elemento ha una struttura che descrive come esso si adatta ai vari schermi)

    .container - [...]

  > sm | md | lg | xl

  Il container (come gli altri oggetti) può avere un estensione che descrive la sua dimensione.

### Grid-system
 - Permette di organizzare una tabella, nello specifico è usata per dare ordine alla pagina.

        .row
            .col
            .col

 - si può definire anche uno span (se non viene specificato, viene gestito di default in maniera "sufficientemente efficacie"):

        .col - [ ... ] - [ 1:12 ]
  > sm | md | lg | xl


### Titoli
 - __h*__
 - __display-*__
 - __small-*__

 (sono presenti anche delle classi per gestire i formati tipografici dei testi)

### Colori
  - per text e bg esiste una _palet colori Standard_:

  > muted | primary | success | info | warning | danger | secondary | white | dark | body | light

### Tabelle
  - permette di creare tabelle:

            .table - [...]

  > striped | bordered | hover | dark | borderless | responsive
  > muted | primary | success | info | warning | danger | secondary | white | dark | body | light
  > sm | md | lg | xl

  (le classi sono combinabili, mettendo più .table-*)


 - può essere inserita anche un intestazione:
      <table class="table">
        <thead class="table-dark">
            <tr>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Email</th>

### Immagini
 - sono classi per gestire il formato e la visualizzazione delle immagini:

        .rounded-circle
        .img-thumbnail
        .float-start / .float-end   (allineamento immagini)
        .img-fluid                  (classe per rendere l'immagine responsive)
        .mx-auto + .d-block         (centra l'immagini)
    (mx: margin left-right)


### Alert
 - banner
 - richiede che prima venga chiamata la superclasse e poi la classe colore.

        .alert + .alert - [...]

  > muted | primary | success | info | warning | danger | secondary | white | dark | body | light

 - esiste anche una classe per i link

        .alert-link

 - si può inserire una chiusura:

        .alert-dismissible  + BOTTONE

    <div class="alert alert-success alert-dismissible">
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        <strong>Success!</strong> This alert box could indicate a successful or positive action.

 - allerte __Animate__

        .fade + .show


### Bottoni
 - può essere applicato alle classi: <a>, <button>,  <input>

   <button type="button" class="btn">Basic</button>

        .btn + .btn - [...] - [...] + btn - [...]

  > outline

  > muted | primary | success | info | warning | danger | secondary | white | dark | body | light

  > sm | md | lg | xl

- bottone grosso:

  <div class="d-grid gap-3">
    <button type="button" class="btn btn-primary btn-block">Full-Width Button</button>
    <button type="button" class="btn btn-primary btn-block">Full-Width Button</button>
    <button type="button" class="btn btn-primary btn-block">Full-Width Button</button>

        .d-grid
        .gap - [ 1:12 ]

 - attivo/disattivo

        .active / .disactive

 - spinner

  <button class="btn btn-primary">
    <span class="spinner-border spinner-border-sm"></span>



### Button grup
  - permette di rare ordine alla pagina.

  <div class="btn-group">
    <button type="button" class="btn btn-primary">Apple</button>
    <button type="button" class="btn btn-primary">Samsung</button>
    <button type="button" class="btn btn-primary">Sony</button>

    .btn-group - [...]

  > sm | md | lg | xl

 - Menù a tendina

 <div class="btn-group">
    <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown">Sony</button>
    <div class="dropdown-menu">
      <a class="dropdown-item" href="#">Tablet</a>

    .dropdown-toggle   +   .dropdown-menu   +   .dropdown-item

### Badge
 - non so cosa servano ma sono tipo i "tag"

        .badge + .bg - [...]

  > muted | primary | success | info | warning | danger | secondary | white | dark | body | light


 - bordi arrotondati (sottoclasse da aggiungere alla classe primaria badge):

        .rounded-pill

 - può essere inserito anche all'interno di altri oggetti:

    <button type="button" class="btn btn-primary">  Messages <span class="badge bg-danger">4</span>

### Progressbar
    [...]

### Spinners
    [...]

### Pagine
    [...]

### Modal
 - in alcuni ambienti non funzionano, perche "non sanno dove mettersi" (es: i-frame)






