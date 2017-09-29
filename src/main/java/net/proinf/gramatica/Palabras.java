/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Varias palabras
 */
public class Palabras implements Iterable<Palabra> {
    private final static boolean NO = false;
    private final ArrayList<Palabra> list = new ArrayList<>();


    public Palabras() {
    }

    public Palabras(String... palabras) {
        for (String palabra : palabras) procesarPalabras(palabra);
    }

    public Palabras(Palabra... palabras) {
        for (Palabra palabra : palabras) agregarPalabra(palabra);
    }

    public Palabras(Object... palabras) {
        for (Object objeto : palabras) {
            if (objeto instanceof Palabra) agregarPalabra((Palabra) objeto);
            else procesarPalabras(objeto.toString());
        }
    }

    public Iterator<Palabra> iterator() {
        return new PalabrasIterator();
    }

    public int numeroPalabras() {
        return list.size();
    }

    public boolean hayPalabras() {
        return list.size() > 0;
    }

    public Palabra primeraPalabra() {
        return list.get(0);
    }

    public Palabra ultimaPalabra() {
        return list.get(list.size() - 1);
    }

    public Palabra palabra(int posicion) {
        return list.get(posicion);
    }

    public void agregarPalabra(Palabra palabra) {
        if (NO == palabra.estaVacia()) list.add(palabra);
    }

    public void anteponerPalabra(Palabra palabra) {
        if (NO == palabra.estaVacia()) list.add(0, palabra);
    }

    public boolean borrarPalabra(Palabra palabra) {
        palabra = buscarPalabra(palabra);
        if (palabra.estaVacia()) return false;
        list.remove(palabra);
        return true;
    }

    public boolean agregarPalabraSinDuplicar(Palabra palabra) {
        if (NO == existePalabra(palabra)) {
            agregarPalabra(palabra);
            return true;
        }
        return false;
    }

    public Palabra buscarPalabra(Palabra estaPalabra) {
        for (Palabra palabra : this)
            if (palabra.es(estaPalabra)) return palabra;
        return new Palabra();
    }

    public boolean existePalabra(Palabra palabra) {
        return NO == buscarPalabra(palabra).estaVacia();
    }

    /**
     * Obtiene las palabras separadas por espacios o
     * por por su capitalización.
     * Ejemplos:
     * "uno dos tres" &rarr; "uno","dos","tres"
     * "UnoDosTres" &rarr; "uno","dos","tres"
     * "uno_dos_tres" &rarr; "uno","dos","tres"
     */
    private void procesarPalabras(String palabras) {
        palabras = palabras.trim().replace('_', ' ');
        for (int posicionInicial = 0, posicionEspacio;
             posicionInicial < palabras.length();
             posicionInicial = posicionEspacio + 1) {
            posicionEspacio = palabras.indexOf(' ', posicionInicial);
            if (posicionEspacio < 0) posicionEspacio = palabras.length();
            String palabra = palabras.substring(posicionInicial, posicionEspacio).trim();
            if (NO == palabra.equals("")) procesarPalabrasCapitales(palabra); //list.add(new Palabra(palabra));
        }
    }

    /**
     * Ej: "UnoDosTREsCUatroZ" &rarr; "uno","dos","tres","cuatro","z"
     */
    private void procesarPalabrasCapitales(String palabra) {
        StringBuffer bafer = new StringBuffer();
        boolean ultimaFueMinuscula = Character.isLowerCase(palabra.charAt(0));
        for (int i = 0; i < palabra.length(); ++i) {
            char letra = palabra.charAt(i);
            if (Character.isUpperCase(letra) && ultimaFueMinuscula) {
                list.add(new Palabra(bafer.toString()));
                bafer = new StringBuffer();
            }
            bafer.append(letra);
            ultimaFueMinuscula = Character.isLowerCase(letra);
        }
        list.add(new Palabra(bafer.toString()));
    }

    /**
     * Enumera las palabras del concepto indicado
     * <dl><dt>Ejemplos:</dt>
     * <dd>"a","b","c"("elemento") &rarr; "los elementos: a, b y c"</dd>
     * <dd>"blanca","negra"("oveja") &rarr; "las ovejas blanca y negra"</dd>
     * <dd>"Pepe","Ana","Sara"("cliente") &rarr; "los clientes: Pepe, Ana y Sara"</dd>
     * <dd>"lácteo"("categoría") &rarr; "la categoría lácteo"</dd>
     * <dd>""("producto") &rarr; "ningún producto"</dd>
     * </dl>
     */
    public String enumerarConcepto(Palabra concepto) {
        StringBuilder bafer = new StringBuilder();
        if (numeroPalabras() == 0) {
            if (concepto.generoAntepuesto().esFemenino()) bafer.append("ninguna");
            else bafer.append("ningún");
            bafer.append(" ");
            bafer.append(concepto);
        } else {
            if (numeroPalabras() != 1) concepto = concepto.enPlural();
            bafer.append(concepto.anteponerArticulo(Articulo.determinado));
            if (numeroPalabras() >= 3) bafer.append(":");
            bafer.append(" ");
            bafer.append(enumerar(null));
        }
        return bafer.toString();
    }

    /**
     * Enumera las palabas separándolas con comas,
     * excepto entre las dos últimas palabras que se separan con la conjunción <q>y</q>
     * <dl><dt>Ejemplos:</dt>
     * <dd>"a","b","c","c" &rarr; "la a, la b, la c y la d"</dd>
     * <dd>"mercurio","plomo" &rarr; "el mercurio y el plomo"</dd>
     * <dd>"galleta" &rarr; "la galleta"</dd>
     * </dl>
     */
    public String enumerar(Articulo articulo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Palabra palabra : this) {
            if (palabra != primeraPalabra()) {
                if (palabra == ultimaPalabra()) stringBuilder.append(" y ");
                else stringBuilder.append(", ");
            }
            if (articulo == null) stringBuilder.append(palabra);
            else stringBuilder.append(palabra.anteponerArticulo(articulo));
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return unir(" ", null);
    }

    /**
     * Ej: "curso","clase" &rarr; "CursosClases"
     */
    public String unirPluralesCapitalizando() {
        return unir("", (palabras, palabra) -> palabra.enPlural().sinAcentos().enCapital().toString());
    }

    /**
     * Ej: "curso","clase" &rarr; "cursos_clases"
     */
    public String unirPluralesGuionando() {
        return unir("_", (palabras, palabra) -> palabra.enPlural().sinAcentos().enMinusculas().toString());
    }

    /**
     * Ej: "a","b","c" unir(".",null) &rarr; "a.b.c"
     */
    public String unir(String elementoUnion, Maquillador maquillador) {
        StringBuilder bafer = new StringBuilder();
        String texto;
        if (hayPalabras()) {
            for (int indice = 0; indice < list.size(); ++indice) {
                if (indice > 0) bafer.append(elementoUnion);
                Palabra palabra = list.get(indice);
                if (maquillador == null) texto = palabra.toString();
                else texto = maquillador.maquillar(this, palabra);
                bafer.append(texto);
            }
        }
        return bafer.toString();
    }

    /**
     * Array de String
     */
    public String[] lista() {
        String[] lista = new String[numeroPalabras()];
        for (int i = 0; i < lista.length; ++i) lista[i] = palabra(i).toString();
        return lista;
    }

    public interface Maquillador {
        String maquillar(Palabras palabras, Palabra palabra);
    }

    static class Letras extends net.proinf.gramatica.Letras<Letras> {
    }

    private class PalabrasIterator implements java.util.Iterator<Palabra> {
        int posicion = 0;

        public boolean hasNext() {
            return posicion < list.size();
        }

        public Palabra next() {
            return list.get(posicion++);
        }

        public void remove() {
            list.remove(posicion--);
        }
    }
}
