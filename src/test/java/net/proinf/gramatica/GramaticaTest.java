/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GramaticaTest {
    @Test
    void plural() {
        assertEquals("aviones", Gramatica.plural("avión"));
    }

    @Test
    void silabas() {
        assertEquals("ca-mión", Gramatica.silabas("camión"));
        assertEquals("a-crós-ti-co", Gramatica.silabas("acróstico"));
        assertEquals("a-tra-en", Gramatica.silabas("atraen"));
        assertEquals("ins-ti-tu-ción", Gramatica.silabas("institución"));
        assertEquals("co-he-cho", Gramatica.silabas("cohecho"));
        assertEquals("clor-hi-dra-to", Gramatica.silabas("clorhidrato"));
    }

    @Disabled("Palabras aún no soportadas")
    @Test
    void silabasDificiles() {
        assertEquals("con-traa-ta-que", Gramatica.silabas("contraataque"));
        assertEquals("pa-ra-psi-có-lo-go", Gramatica.silabas("parapsicólogo"));
        assertEquals("in-ter-re-la-cio-nar", Gramatica.silabas("interrelacionar"));
        assertEquals("cor-ta-u-ñas", Gramatica.silabas("cortauñas"));
        assertEquals("sub-ra-yar", Gramatica.silabas("subrayar"));
    }

    @Test
    void acentuacion() {
        assertEquals("aguda", Gramatica.acentuacion("camión"));
    }

    @Test
    void genero() {
        assertEquals("femenino", Gramatica.genero("mesa"));
    }

    @Test
    void numero() {
        assertEquals("singular", Gramatica.numero("mesa"));
        assertEquals("plural", Gramatica.numero("aviones"));
    }

    @Test
    void cuantificar() {
        assertEquals("4 paletas", Gramatica.cuantificar(4, "paleta"));
        assertEquals("una paleta", Gramatica.cuantificar(1, "paleta"));
    }

    @Test
    void capitalizar() {
        assertEquals("En un rincón", Gramatica.capitalizar("en un rincón"));
    }

    @Test
    void enumerar() {
        assertEquals("una silla y una paleta", Gramatica.enumerar(Articulo.indeterminado,
                "silla", "paleta"));
    }

    @Test
    void enumerarConcepto() {
        assertEquals("los productos: a, b y c",
                Gramatica.enumerarConcepto("producto", "a", "b", "c"));
    }

    @Test
    void articulo() {
        assertEquals("el avión", Gramatica.articulo(Articulo.determinado, "avión"));
        assertEquals("un avión", Gramatica.articulo(Articulo.indeterminado, "avión"));
        assertEquals("los aviones", Gramatica.articulo(Articulo.determinado, "aviones"));
        assertEquals("unos aviones", Gramatica.articulo(Articulo.indeterminado, "aviones"));
    }

    @Test
    void unirPluralesCapitalizando() {
        assertEquals("CamelCase", Gramatica.unirPluralesCapitalizando("camel", "case"));
    }

    @Test
    void unirPluralesGuionando() {
        assertEquals("not_camel_case", Gramatica.unirPluralesGuionando("not", "camel", "case"));
    }

}