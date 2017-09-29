/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/**
 * Sílabación de una palabra.
 *
 * <dl>
 * <dt>Referencias:</dt>
 * <dd><a href="http://es.wikipedia.org/wiki/Acentuaci%C3%B3n_del_idioma_espa%C3%B1ol">Acentuación del idioma español</a>
 *   - Roberto Véguez y Isabel Estrada</dd>
 * <dd><a href="http://es.wikipedia.org/wiki/Tilde">Tilde</a> - La Wikipedia</dd>
 * <dd><a href="http://www.texytipografia.com/archive/tipografia.pdf">Tipografía</a></dd>
 *
 * <dt>Tareas pendiente</dt>
 * <dd>Palabras de difícil separación silábica: contraataque, cohecho, parapsicólogo,
 *   clorhidrato, interrelacionar, cortauñas, subrayar
 * </dl>
 *
 * <p>Licencia: <a href="http://creativecommons.org/licenses/GPL/2.0/deed.es">Este software está sujeto a la CC-GNU GPL</a></p>
 * @author Francisco Cascales <fco@proinf.net>
 * @version 0.05, 24-dic-2007 - Inicio del proyecto
 * @version 0.07,  2-ene-2008 - La palabra se actualiza automáticamente al cambiar las silabas
 */
public class Silabas implements Iterable<Silabas.Silaba>, Observer {

    //////////////////////////////////////////////////
    // Campos

    private ArrayList<Silaba> list = new ArrayList<Silaba>();
    private Palabra palabra;

    //////////////////////////////////////////////////
    // Constantes

    private final static boolean NO = false;

    final static String[] DOS_CONSONANTES_INDIVISIBLES = {
            "bl","br","dr","cr","cl","fr","fl","gr","gl","pl","pr","tr","dr",
            "ch","ll","rr",
    };

    //////////////////////////////////////////////////
    // Iterador

    public Iterator<Silaba> iterator() { return new SilabasIterator(); }
    private class SilabasIterator implements java.util.Iterator<Silaba> {
        int posicion = 0;
        public boolean hasNext() { return posicion < list.size(); }
        public Silaba next() { return list.get(posicion++); }
        public void remove() { list.remove(posicion--); }
    }

    //////////////////////////////////////////////////
    // Constructor

    public Silabas(Palabra palabra) {
        this.palabra = palabra;
        if (palabra.hayVocales()) {
            procesar1_SepararVocalesDeConsonantes();
            procesar2_HiatosDiptongos();
            procesar3_ReglasSeparacionSilabica();
            procesar4_DeterminarSilabaTonica();
        }
        else {
            agregarProtosilaba (new Silaba(palabra.toString()));
            primeraSilaba().marcarTonica(); // y
        }
        for (Silaba silaba: this) silaba.addObserver(this);
    }

    //////////////////////////////////////////////////
    // Silabación

    /**
     * Primera fase: Creación de las protosílabas
     * Ej: "esdrujula" → e-sdr-u-j-u-l-a, "canción" → c-a-nc-ió-n
     */
    private void procesar1_SepararVocalesDeConsonantes() {

        Silaba protosilaba = new Silaba("");
        boolean ultimaFueVocal = palabra.primeraLetra().esVocal();
        for (Letra letra: palabra) {
            if ( (letra.esVocal() && NO == ultimaFueVocal)
                    || (letra.esConsonante() && ultimaFueVocal) )
            {
                agregarProtosilaba(protosilaba);
                protosilaba = new Silaba("");
            }
            protosilaba.agregar(letra);
            ultimaFueVocal = letra.esVocal();
        }
        agregarProtosilaba(protosilaba);
    }
    /**
     * Segunda fase: Detectar hiatos en las protosílabas vocales
     * Ej: p-oe-t-a → p-o-e-t-a,
     */
    private void procesar2_HiatosDiptongos() {
        for (Silaba silaba: this) {
            if (silaba.todoVocales()) {
                if (silaba.numeroVocales() == 2) {
                    boolean esHiato = silaba.primeraLetra().esVocalFuerte()
                            && silaba.segundaLetra().esVocalFuerte();
                    if (esHiato) silaba.partirPorMedio(); // k-o/a-l-a, p-o/e-t-a
                }
            }
        }
        // Forman hiato los verbos terminados en -uir, así como los derivados
        if (palabra.acabaEn("uir"))
            penultimaSilaba().partirPorMedio(); // i-nstr-u/i-r
        else if (palabra.acabaEn("uido","uida"))
            antepenultimaSilaba().partirPorMedio(); // i-nstr-u/i-d-a
        else if (palabra.acabaEn("uidos","uidas"))
            antepenultimaSilaba().anterior().partirPorMedio(); // c-o-nstr-u/i-d-o-s
    }
    /**
     * Tercera fase: Juntar las protosílabas consonantes con las vocales
     * Ej: c-a-nc-ió-n → can-ción,  p-o-e-t-a → po-e-ta
     */
    private void procesar3_ReglasSeparacionSilabica() {
        if (numeroSilabas() <= 1) return;

        if (primeraSilaba().todoConsonantes())
            primeraSilaba().juntarConSiguiente(); // m↔a-n-o
        if (ultimaSilaba().todoConsonantes())
            penultimaSilaba().juntarConSiguiente(); // t-e-l-ó↔n

        for (Silaba silaba: this) {
            if (silaba.todoConsonantes()
                    && silaba != primeraSilaba() && silaba != ultimaSilaba())
            {
                if (silaba.numeroLetras() == 1) {
                    silaba.juntarConSiguiente();  // s-e-c↔a
                }
                else if (silaba.numeroLetras() == 2) {
                    if (silaba.es(DOS_CONSONANTES_INDIVISIBLES))
                        silaba.juntarConSiguiente(); // a-tr↔a-e-n, p-e-rr↔o
                    else
                        silaba.juntarAdyacentesPartiendoPorMedio(); // g-i↔m/n↔a-s-io
                }
                else if (silaba.acabaEn(DOS_CONSONANTES_INDIVISIBLES)) {
                    silaba.juntarAdyacentesPartiendoDesdeFinal(2); // tr-a↔ns/gr↔e-s-ió-n
                }
                else if (silaba.numeroLetras() == 3) {
                    if (silaba.acabaEn("l", "r"))
                        silaba.juntarAdyacentesPartiendoDesde(1); // e-n↔tr-e-g-a, e-s↔pr-o-n-c-e-d-a
                    else
                        silaba.juntarAdyacentesPartiendoDesde(2); // i↔ns/t↔i-t-u-c-ió-n, c-o↔ns/t↔i-t-u-c-ió-n
                }
                else if (silaba.numeroLetras() == 4) {
                    silaba.juntarAdyacentesPartiendoPorMedio(); // tr-a↔ns/gr↔e-d-i-r, i↔ns/tr↔u-cc-ió-n
                }
            }
        }
    }
    /**
     * Cuarta fase: Detectar la sílaba tónica
     * Ej: po-e-ta → 2ª, es-drú-ju-la → 2ª
     */
    private void procesar4_DeterminarSilabaTonica() {
        for (Silaba silaba: this) {
            if (silaba.tieneAcento()) {
                silaba.marcarTonica(); // café, árbol, murciélago
                return;
            }
        }
        // A partir de aquí la palabra no tiene acentos (tildes)

        if (numeroSilabas() == 1) {
            primeraSilaba().marcarTonica(); // sol
        }
        else if (NO == ultimaSilaba().ultimaLetra().esVocal()
                &&  NO == ultimaSilaba().ultimaLetra().es('n','s'))
        {
            ultimaSilaba().marcarTonica(); // pastel
        }
        else if (ultimaSilaba().ultimaLetra().es('y')) {
            ultimaSilaba().marcarTonica(); // Uruguay
        }
        else if (numeroSilabas() >= 2) {
            penultimaSilaba().marcarTonica(); // amigo
            return;
        }
    }

    //////////////////////////////////////////////////
    // Propiedades de lectura

    public Silaba silaba(int posicion) { return list.get(posicion); }
    public int numeroSilabas() { return list.size(); }
    public boolean haySilabas() { return list.size() > 0; }

    public Silaba primeraSilaba() { return list.get(0); }
    public Silaba segundaSilaba() { return list.get(1); }
    public Silaba ultimaSilaba() { return list.get(list.size()-1); }
    public Silaba penultimaSilaba() { return list.get(list.size()-2); }
    public Silaba antepenultimaSilaba() { return list.get(list.size()-3); }

    /** "camión" &rarr; <q>aguda</q> */
    public Acentuacion acentuacion () {
        if (numeroSilabas() >= 1)
            if (ultimaSilaba().esTonica())
                return Acentuacion.aguda;
        if (numeroSilabas() >= 2)
            if (penultimaSilaba().esTonica())
                return Acentuacion.llana;
        if (numeroSilabas() >= 3)
            if (antepenultimaSilaba().esTonica())
                return Acentuacion.esdrujula;

        return Acentuacion.sobreesdrujula;
    }

    //////////////////////////////////////////////////
    // Propiedades de escritura

    /*
     public void cambiarAcentuacion(Acentuacion acentuacion) {
        if (acentuacion == acentuacion()) return;
        Silaba silabaTonica = null;
        for (Silaba silaba: this) silaba.quitarAcento();
        switch (acentuacion) {
            case aguda:
                if (numeroSilabas() >= 1) {
                    silabaTonica = ultimaSilaba();
                    if (palabra.ultimaLetra().esVocal() || palabra.ultimaLetra().es('n','s') )
                        silabaTonica.acentuar();
                }
                break;
            case llana:
                if (numeroSilabas() >= 2) {
                    silabaTonica = penultimaSilaba();
                    if (NO == palabra.ultimaLetra().esVocal() && NO == palabra.ultimaLetra().es('n','s') )
                        silabaTonica.acentuar();
                }
                break;
            case esdrujula:
                if (numeroSilabas() >= 3) {
                    silabaTonica = antepenultimaSilaba();
                    silabaTonica.acentuar();
                }
                break;
            case sobreesdrujula:
                if (numeroSilabas() >= 4) {
                    silabaTonica = antepenultimaSilaba().anterior();
                    silabaTonica.acentuar();
                }
                break;
        }

        if (silabaTonica != null) {
            procesar4_DeterminarSilabaTonica();
            actualizarPalabraReferenciada();
        }
    }*/

    public boolean quitarSilaba(Silaba silaba) {
        if (list.remove(silaba)) {
            actualizarPalabraReferenciada();
            return true;
        }
        return false;
    }
    /*
    public void agregarSilaba(String silaba) {
        if (silaba != null && !silaba.equals("")) {
            agregarProtosilaba(new Silaba(silaba));
            actualizarPalabraReferenciada();
        }
    }*/

    //////////////////////////////////////////////////
    // Representación

    @Override public String toString() {
        return unirSilabas("-");
    }
    public String unirSilabas() {
        return unirSilabas("");
    }
    public String unirSilabas(String separador) {
        StringBuffer resultado = new StringBuffer();
        if (haySilabas()) {
            for (Silaba silaba: this) {
                resultado.append(silaba.toString());
                if (silaba != ultimaSilaba()) resultado.append(separador);
            }
        }
        return resultado.toString();
        /*if (size() > 0) {
            int indice = 0;
            resultado.append(get(indice++).toString());
            while(indice < size()) {
                resultado.append("-");
                resultado.append(get(indice++).toString());
            }
        }*/
    }

    //////////////////////////////////////////////////
    // Métodos auxiliares

    public void update(Observable o, Object arg) {
        actualizarPalabraReferenciada();
    }

    private void agregarProtosilaba(Silaba silaba) { list.add(silaba); }
    private void actualizarPalabraReferenciada() { palabra.cambiarPor(unirSilabas()); }

    private void procesarSilabaTonica(Silaba silabaTonica) {
        for (Silaba silaba: this) {
            silaba.tonica = silaba == silabaTonica;
        }
    }

    //////////////////////////////////////////////////
    // SUBCLASE

    public class Silaba extends Letras<Silaba> {
        private boolean tonica;

        private Silaba (String silaba) {
            super(silaba);
            this.tonica = false;
        }

        //////////////////////////////////////////////
        // Interfaz

        public boolean esTonica() { return tonica; }
        public boolean esAtona() { return !tonica; }

        /** Poner la tilde */
        public Silaba acentuar() {
            if (hayVocales()) ultimaVocal().ponerAcento();
            procesarSilabaTonica(this);
            requiereActualizacion(this);
            return this;
        }

        /** quitar la tilde */
        public void quitarAcento() {
            for (Letra letra: this) letra.quitarAcento();
            procesarSilabaTonica(null);
            requiereActualizacion(this);
        }

        //////////////////////////////////////////////
        // Métodos para la silabación

        private void marcarAtona() { tonica = false; }
        private void marcarTonica() { tonica = true; }

        /** Ej: ab-CD-ef-gh → ab-cd-EF-gh */
        private Silaba siguiente() { return list.get(list.indexOf(this)+1); }
        private Silaba anterior() { return list.get(list.indexOf(this)-1); }

        /** Ej: ab-CD-ef-gh → ab-CDEF-gh */
        private Silaba juntarConSiguiente() {
            Silaba silabaSiguiente = siguiente();
            this.agregar(silabaSiguiente.toString());
            list.remove(silabaSiguiente);
            return this;
        }
        /** Ej: abc-DEF-ghi(1) → abc-D-ef-ghi */
        private Silaba partir(int posicion) {
            if (posicion < 0) posicion = bafer.length() + posicion;
            Silaba silabaSiguiente = new Silaba(bafer.substring(posicion, bafer.length()));
            bafer.delete(posicion, bafer.length());
            list.add(list.indexOf(this) + 1, silabaSiguiente);
            return this;
        }
        /** Ej: abc-DE-fg → abc-D-e-fg */
        private Silaba partirPorMedio() {
            return partir(bafer.length()/2);
        }
        /** Ej: abc-DEF-ghi-jk(1) → ABCD-efghi-jk */
        private Silaba juntarAdyacentesPartiendoDesde(int posicion) {
            return partir(posicion)
                    .anterior().juntarConSiguiente()
                    .siguiente().juntarConSiguiente();
        }
        /** abc-DEFG-hi-jk → ABCDE-fghi-jk */
        private Silaba juntarAdyacentesPartiendoPorMedio() {
            return juntarAdyacentesPartiendoDesde(bafer.length()/2);
        }
        /** abc-DEF-ghi-jk(1) → ABCDE-fghi-jk */
        private Silaba juntarAdyacentesPartiendoDesdeFinal(int posicion) {
            return juntarAdyacentesPartiendoDesde(-posicion);
        }
    }

    //////////////////////////////////////////////////

}
