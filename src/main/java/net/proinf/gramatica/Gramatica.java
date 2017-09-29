/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

/**
 * Gramática española: acentuación, sílabas, plural, género, artículos
 * <p>
 * <dl>
 * <dt>Ejemplos de uso:</dt>
 * <dd><code>Gramatica.plural("camión")</code> &rarr; <samp>"camiones"</samp></dd>
 * <dd><code>Gramatica.silabas("camión")</code> &rarr; <samp>"ca-mión"</samp></dd>
 * <dd><code>Gramatica.acentuacion("camión")</code> &rarr; <samp>"aguda"</samp></dd>
 * <dd><code>Gramatica.genero("camión")</code> &rarr; <samp>"masculino"</samp></dd>
 * <p>
 * <dt>Enlaces de referencia:</dt>
 * <dd><a href="http://es.wikipedia.org/wiki/Idioma_espa%C3%B1ol">Idioma español</a> - La Wikipedia</dd>
 * <p>
 * </dl>
 */

public class Gramatica {

    private Gramatica() {
    } // No instanciable

    /**
     * Ej: "camión" &rarr; "camiones"
     */
    public static String plural(String palabra) {
        return new Palabra(palabra).enPlural().toString();
    }

    /**
     * Ej: "camión" &rarr; "ca-mión"
     */
    public static String silabas(String palabra) {
        return new Silabas(new Palabra(palabra)).toString();
    }

    /**
     * Ej: "camión" &rarr; "aguda"
     */
    public static String acentuacion(String palabra) {
        return new Silabas(new Palabra(palabra)).acentuacion().toString();
    }

    /**
     * Ej: "camión" &rarr; "masculino"
     */
    public static String genero(String palabra) {
        return new Palabra(palabra).genero().name();
    }

    /**
     * Ej: "clientes" &rarr; "plural"
     */
    public static String numero(String palabra) {
        return new Palabra(palabra).numero().name();
    }

    /**
     * Ej: 0,"comentario" &rarr; "ningún comentario"<br />4,"persona" &rarr; "4 personas"
     */
    public static String cuantificar(int numeroElementos, String palabra) {
        return new Palabra(palabra).cuantificar(numeroElementos);
    }

    /**
     * Ej: "en un Lugar" &rarr; "En un lugar"
     */
    public static String capitalizar(String texto) {
        if (texto.length() <= 1) return texto.toUpperCase();
        else return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    /**
     * Ej: "a","b","c" &rarr; "la a, la b y la c"
     */
    public static String enumerar(Articulo articulo, String... palabras) {
        return new Palabras(palabras).enumerar(articulo);
    }

    /**
     * Ej: producto,"a","b","c" &rarr; "los productos: a, b y c"
     */
    public static String enumerarConcepto(String concepto, String... palabras) {
        return new Palabras(palabras).enumerarConcepto(new Palabra(concepto));
    }

    /**
     * Ej: "visitas",indeterminado &rarr; "unas visitas"
     */
    public static String articulo(Articulo articulo, String palabra) {
        return articulo.agregarPalabra(new Palabra(palabra));
    }

    /**
     * Ej: "factura","detalle"  &rarr; "FacturasDetalles"
     */
    public static String unirPluralesCapitalizando(String... palabras) {
        return new Palabras(palabras).unirPluralesCapitalizando();
    }

    /**
     * Ej: "factura","detalle"  &rarr; "facturas_detalles"
     */
    public static String unirPluralesGuionando(String... palabras) {
        return new Palabras(palabras).unirPluralesGuionando();
    }
}
