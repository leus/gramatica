/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

import java.util.Iterator;
import java.util.Observable;

/**
 * Un conjunto de letras forma una sílaba o una palabra
 */
public abstract class Letras<T extends Letras> extends Observable implements Iterable<Letra> {
    protected StringBuffer bafer = null;

    public Letras() {
        this("");
    }

    public Letras(String letras) {
        bafer = new StringBuffer(limpiar(letras));
    }

    private static String limpiar(String letras) {
        return letras.trim()/*.toLowerCase()*/;
    }

    public Iterator<Letra> iterator() {
        return new LetrasIterator();
    }

    protected void requiereActualizacion(Object objeto) {
        setChanged();
        notifyObservers(objeto);
    }

    @SuppressWarnings("unchecked")
    public T cambiarPor(String letras) {
        bafer = new StringBuffer(limpiar(letras));
        requiereActualizacion(this);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T agregar(String sufijo) {
        bafer.append(sufijo);
        requiereActualizacion(this);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T agregar(Letra letra) {
        bafer.append(letra.getChar());
        requiereActualizacion(this);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T quitarUltimaLetra() {
        bafer.deleteCharAt(bafer.length() - 1);
        requiereActualizacion(this);
        return (T) this;
    }

    public Letra letra(int posicion) {
        return new Letra(this, posicion);
    }

    public Letra primeraLetra() {
        return letra(0);
    }

    public Letra segundaLetra() {
        return letra(1);
    }

    public Letra penultimaLetra() {
        return letra(bafer.length() - 2);
    }

    public Letra ultimaLetra() {
        return letra(bafer.length() - 1);
    }

    public Letra ultimaVocal() {
        for (int posicion = bafer.length() - 1; posicion >= 0; --posicion) {
            Letra letra = letra(posicion);
            if (letra.esVocal()) return letra;
        }
        return null;
    }

    public boolean estaVacia() {
        return bafer.length() == 0;
    }

    @Override
    public String toString() {
        return bafer.toString();
    }

    public int numeroLetras() {
        return bafer.length();
    }

    public int numeroVocales() {
        int cuentaVocales = 0;
        for (Letra letra : this) if (letra.esVocal()) ++cuentaVocales;
        return cuentaVocales;
    }

    public boolean todoVocales() {
        for (Letra letra : this) if (letra.esConsonante()) return false;
        return true;
    }

    public boolean todoConsonantes() {
        for (Letra letra : this) if (letra.esVocal()) return false;
        return true;
    }

    public boolean hayVocales() {
        return numeroVocales() > 0;
    }

    public boolean tieneAcento() {
        for (Letra letra : this) if (letra.estaAcentuada()) return true;
        return false;
    }

    public boolean acabaEn(String sufijo) {
        return bafer.toString().endsWith(sufijo);
    }

    public boolean acabaEn(String... sufijos) {
        String letras = bafer.toString();
        for (String sufijo : sufijos) if (letras.endsWith(sufijo)) return true;
        return false;
    }

    public boolean empiezaPor(String prefijo) {
        return bafer.toString().startsWith(prefijo);
    }

    public boolean empiezaPor(String... prefijos) {
        String letras = bafer.toString();
        for (String prefijo : prefijos) if (letras.startsWith(prefijo)) return true;
        return false;
    }

    public boolean es(String letras) {
        return bafer.toString().equals(letras);
    }

    public boolean es(T letras) {
        return bafer.toString().equals(letras.bafer.toString());
    }

    public boolean es(String... lista) {
        for (String elemento : lista) if (elemento.equals(bafer.toString())) return true;
        return false;
    }

    private class LetrasIterator implements Iterator<Letra> {
        int posicion = 0;

        public boolean hasNext() {
            return posicion < bafer.length();
        }

        public Letra next() {
            return letra(posicion++);
        }

        public void remove() {
            bafer.deleteCharAt(posicion--);
        }
    }
}
