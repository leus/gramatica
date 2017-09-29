/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

/**
 * Una palabra gramatical.
 * <p>
 * <p><a href="http://es.wikipedia.org/wiki/Formaci%C3%B3n_del_plural_en_espa%C3%B1ol">Formación del plural en español</a>
 * - La Wikipedia </p>
 * <p>
 */
public class Palabra extends Letras<Palabra> {

    public final static int TODOS = Integer.MAX_VALUE;
    public final static int FALTAN_TODOS = Integer.MIN_VALUE;
    protected final static boolean NO = false;

    private final String original;
    private Genero genero;
    private Numero numero;
    private Silabas silabas;

    public Palabra() {
        this("");
    }

    public Palabra(String palabra) {
        super(palabra.toLowerCase());
        original = palabra;
        genero = null;
        numero = null;
        silabas = null;
    }

    @Override
    public String toString() {
        if (bafer.toString().equals(original)) return original;
        else return enMayusculasSegunPatron(original);
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object object) {
        return object != null && object.toString().equals(this.toString());
    }

    /**
     * Crea un duplicado de la palabra
     */
    public Palabra clonar() {
        return clonar(original);
    }

    /**
     * Crea un duplicado de la palabra pero cambiando el texto
     */
    protected Palabra clonar(String texto) {
        Palabra palabra = new Palabra(texto);
        palabra.genero = this.genero();
        palabra.numero = this.numero();
        return palabra;
    }

    /**
     * Convierte esta palabra a plural
     */
    protected Palabra procesarPlural() {
        this.numero = Numero.plural;
        if (estaVacia() || NO == hayVocales()) return this;

        if (ultimaLetra().esConsonante()) {
            if (ultimaLetra().es('z')) { // pez, hez, voz, maíz, nuez, jazz, raíz, matiz, idiotez
                return quitarUltimaLetra().agregar("ces"); // vez/veces, raíz/raíces
            } else if (ultimaVocal().estaAcentuada()) {
                ultimaVocal().quitarAcento();
                return agregar("es"); // camión/camiones, compás/compases, autobús/autobuses
            } else if (ultimaLetra().es('n')) { // certamen, clon, claxon, espécimen
                if (es("espécimen")) return cambiarPor("especímenes");
                if (silabas().acentuacion().esLlana()) silabas().penultimaSilaba().acentuar();
                return agregar("es"); // canon/cánones, examen/exámenes, imagen/imágenes, margen/márgenes
            } else if (ultimaLetra().es('s')) { // mes, dios, lapsus, análi   sis, gas
                if (silabas().numeroSilabas() == 1) return agregar("es"); // gas/gases, mes/meses, dios/dioses
                else return this; // génesis, paréntesis, mecenas
            } else if (ultimaLetra().es('x')) { // clímax, fax, relax, látex, tórax, réflex, dúplex, telefax
                if (es("fax")) return agregar("es");
            } else if (ultimaLetra().es('c')) { // bistec, coñac, pársec, bloc
                //return quitarUltimaLetra().agregar("ques"); // frac/fraques, bistec/bisteques
                return agregar("s");
            } else if (ultimaLetra().es('g')) { // zigzag, airbag, iceberg
                //return agregar("ues"); // zigzag/zigzagues, airbag/airbagues, iceberg/icebergues
                return agregar("s");
            } else {
                return agregar("es"); // tótem/tótemes, leal/leales, verdad/verdades, rey/reyes, reloj/relojes, red/redes
            }
        } else if (ultimaLetra().esVocal()) {

            if (ultimaLetra().es('í', 'ú')) {
                return agregar("es"); // tabú/tabúes, baladí/baladíes
            } else if (silabas().numeroSilabas() == 1 && NO == ultimaLetra().es('e')) {
                return agregar("es"); // a/aes, yo/yoes, no/noes, sí/síes, sol/soles
            } else if (NO == ultimaLetra().estaAcentuada() || ultimaLetra().es('á', 'é', 'ó')) {
                return agregar("s"); // casa/casas, fe/fes, papá/papás, plató/platós
            }
        }
        return this;
    }

    public void cambiarGenero(Genero genero) {
        if (genero != null) this.genero = genero;
    }

    public void cambiarNumero(Numero numero) {
        if (numero != null) this.numero = numero;
    }

    /**
     * Retorna el número de la palabra: <q>singular</q> o <q>plural</q>.
     */
    public Numero numero() {
        if (numero == null) numero = Numero.segunPalabra(this);
        return numero;
    }

    /**
     * Ejemplo: "árbol" &rarr; <q>masculino</q>
     */
    public Genero genero() {
        if (genero == null) genero = Genero.segunPalabra(this);
        return genero;
    }

    /**
     * Ejemplo: "áquila" &rarr; <q>femenino</q>
     */
    public Genero generoAntepuesto() {
        return Genero.antepuestoSegunPalabra(this);
    }

    /**
     * Ejemplo: "cádiz" &rarr; <q>cá-diz</q>
     */
    public Silabas silabas() {
        if (silabas == null) silabas = new Silabas(this);
        return silabas;
    }

    /**
     * Ejemplo: "árbol" &rarr; <q>masculino</q>
     */
    public Acentuacion acentuacion() {
        return silabas().acentuacion();
    }

    /**
     * <dl>
     * <dt>Referencias:</dt>
     * <dd>Wikipedia: <a href="http://es.wikipedia.org/wiki/Formaci%C3%B3n_del_plural_en_espa%C3%B1ol">Formación del plural en español</a></dd>
     * <dt>Ejemplos:</dt>
     * <dd>"camión" &rarr; "camiones"</dd>
     * <dd>"producto" &rarr; "productos"</dd>
     * <dd>"leal" &rarr; "leales"</dd>
     * </dl>
     */
    public Palabra enPlural() {
        return clonar().procesarPlural();
    }

    /** */
    public Palabra enMinusculas() {
        return clonar(bafer.toString().toLowerCase());
    }

    /** */
    public Palabra enMayusculas() {
        return clonar(bafer.toString().toUpperCase());
    }

    /**
     * Ej: "pepe" &rarr; "Pepe"
     */
    public Palabra enCapital() {
        String texto = bafer.toString();
        if (texto.length() <= 1) texto = texto.toUpperCase();
        else texto = texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
        return clonar(texto);
    }

    /**
     * Ej: "café" &rarr; "cafe"
     */
    public Palabra sinAcentos() {
        Palabra palabra = clonar();
        for (Letra letra : palabra) letra.quitarAcento();
        return palabra;
    }

    /**
     * <dl><dt>Ejemplos:</dt>
     * <dd>"coche"(0) &rarr; "ningún coche"</dd>
     * <dd>"moto"(1) &rarr; "una moto"</dd>
     * <dd>"coche"(1) &rarr; "un coche"</dd>
     * <dd>"coche"(2) &rarr; "2 coches"</dd>
     * <dd>"producto"(TODOS) &rarr; "todos los productos"</dd>
     * <dd>"persona"(-1) &rarr; "falta una persona"</dd>
     * <dd>"persona"(-2) &rarr; "faltan dos personas"</dd>
     * <dd>"teclado"(FALTAN_TODOS) &rarr; "faltan todos los teclados"</dd>
     * </dl>
     */
    public String cuantificar(int numeroElementos) {
        if (numeroElementos == Integer.MAX_VALUE) {
            if (generoAntepuesto().esFemenino()) return "todas las " + this.enPlural();
            else return "todos los " + this.enPlural();
        } else if (numeroElementos == 1) {
            if (generoAntepuesto().esFemenino()) return "una " + this;
            else return "un " + this;
        } else if (numeroElementos > 1) {
            String cantidad = String.valueOf(numeroElementos);
            return cantidad + " " + this.enPlural();
        } else if (numeroElementos == 0) {
            if (generoAntepuesto().esFemenino()) return "ninguna " + this;
            else return "ningún " + this;
        } else if (numeroElementos == Integer.MIN_VALUE) {
            if (generoAntepuesto().esFemenino()) return "faltan todas las " + this.enPlural();
            else return "faltan todos los " + this.enPlural();
        } else if (numeroElementos == -1) {
            if (generoAntepuesto().esFemenino()) return "falta una " + this;
            else return "falta un " + this;
        }
        String cantidad = String.valueOf(-numeroElementos);
        return "faltan " + cantidad + " " + this.enPlural();
    }

    /**
     * Ej: "categoría"(determinado) &rarr; "la categoría"
     */
    public String anteponerArticulo(Articulo articulo) {
        return articulo.agregarPalabra(this);
    }

    /**
     * Ej: "cliente"(indeterminado) &rarr; "de un cliente"
     */
    public String anteponerDe(Articulo articulo) {
        if (articulo.casoArticuloContracto(this)) return "del " + this;
        else return "de " + articulo.agregarPalabra(this);
    }

    /**
     * Ej: "cliente"(determinado) &rarr; "al cliente"
     */
    public String anteponerA(Articulo articulo) {
        if (articulo.casoArticuloContracto(this)) return "al " + this;
        else return "a " + articulo.agregarPalabra(this);
    }

    /**
     * Ej: "hola"("aAaA") &rarr; "hOlA",
     * "palabra"("AaA") &rarr; "PaLABRA"
     */
    public String enMayusculasSegunPatron(String patron) {
        StringBuilder destino = new StringBuilder(bafer);
        boolean enMayusculas = false;
        for (int indice = 0; indice < destino.length(); ++indice) {
            if (indice < patron.length()) {
                enMayusculas = Character.isUpperCase(patron.charAt(indice));
            }
            if (enMayusculas) {
                destino.setCharAt(indice, Character.toUpperCase(destino.charAt(indice)));
            }
        }
        return destino.toString();
    }
}


