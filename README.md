Gramática: número, género y acentuación
=======================================

Funciones en *Java* que implementan reglas gramaticales. Sirven para pasar una palabra a plural, descomponer una palabra en sílabas, o indicar si una palabra es aguda, llana, esdrújula o sobreesdrújula.

Ejemplos de uso:

`Gramatica.plural("camión")` → `"camiones"`

`Gramatica.silabas("paella")` → `"pa-e-lla"`

`Gramatica.acentuacion("águila")` → `"esdrújula"`

`Gramatica.genero("verdad")` → `"femenino"`


Código Java
-----------

-   Clases (Códificado en UTF-8) :
    -   Métodos estáticos genéricos: [Gramatica.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Gramatica.java)
    -   Las enumeraciones: [Genero.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Genero.java), [Acentuacion.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Acentuacion.java), [Numero.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Numero.java), [Articulo.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Articulo.java)
    -   Letras de una sílaba o palabra: [Letra.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Letra.java) y [Letras.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Letras.java)
    -   División silábica de una palabra: [Silabas.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Silabas.java)
    -   Número y género de las palabras: [Palabra.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Palabra.java)
    -   Lista de palabras: [Palabras.java](https://proinf.net/applets/Gramatica/src/net/proinf/gramatica/Palabras.java)

Enlaces de referencia
---------------------

-   [La acentuación](http://f01.middlebury.edu/SP210A/gramatica/acentu-index.html) - Roberto Véguez y Isabel Estrada
-   [Formación del plural en español](http://es.wikipedia.org/wiki/Formaci%C3%B3n_del_plural_en_espa%C3%B1ol) - La Wikipedia
-   [Español/Morfología/La palabra/El sustantivo/El género](http://es.wikibooks.org/wiki/Espa%C3%B1ol/Morfolog%C3%ADa/La_palabra/El_sustantivo/El_g%C3%A9nero) - Wikilibros

Autor
-----
 Francisco Cascales - https://proinf.net/
 
 Licencia
 --------
 
 Este proyecto es distribuido usando la [Licencia Mozilla 2.0](http://mozilla.org/MPL/2.0/).
 