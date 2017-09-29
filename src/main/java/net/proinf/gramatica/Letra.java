/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

/**
 * Letra de una sílaba o palabra
 * <p>
 * <dt>Enlaces de referencia:</dt>
 * <dd><a href="http://wikilengua.fundeu.es/index.php/Acentuaci%C3%B3n">Acentuación</a>- Wikilengua</dd>
 * </dt>
 */
public class Letra {
    final static String VOCALES_ACENTUADAS = "áéíóú";
    final static String VOCALES_DIERESIS = "äëïöü";
    final static String VOCALES = "aeiou" + VOCALES_ACENTUADAS + VOCALES_DIERESIS;
    final static String VOCALES_FUERTES = "aeo" + "íú" + "áéó";
    final static String VOCALES_DEBILES = "iu";

    private final Letras letras;
    private final int posicion;

    Letra(Letras letras, int posicion) {
        this.letras = letras;
        this.posicion = posicion;
    }


    protected char getChar() {
        return letras.bafer.charAt(posicion);
    }

    protected void setChar(char ch) {
        letras.bafer.setCharAt(posicion, ch);
        letras.requiereActualizacion(letras);
    }

    public boolean esConsonante() {
        return !esVocal();
    }

    public boolean esVocal() {
        return VOCALES.indexOf(getChar()) >= 0;
    }

    public boolean esVocalFuerte() {
        return VOCALES_FUERTES.indexOf(getChar()) >= 0;
    }

    public boolean esVocalDebil() {
        return VOCALES_DEBILES.indexOf(getChar()) >= 0;
    }

    public boolean estaAcentuada() { // Tiene tilde
        return VOCALES_ACENTUADAS.indexOf(getChar()) >= 0;
    }

    public boolean esDigito() {
        return Character.isDigit(getChar());
    }

    public boolean esMayusculas() {
        return Character.isUpperCase(getChar());
    }

    public boolean esMinusculas() {
        return Character.isLowerCase(getChar());
    }

    public void aMayusculas() {
        setChar(Character.toUpperCase(getChar()));
    }

    public boolean ponerAcento() {
        int indiceVocal = VOCALES.indexOf(getChar());
        if (indiceVocal >= 0 && indiceVocal < 5) {
            char ch = VOCALES.charAt(5 + indiceVocal % 5);
            setChar(ch);
            return true;
        }
        return false;
    }

    public boolean quitarAcento() {
        int indiceVocal = VOCALES.indexOf(getChar());
        if (indiceVocal >= 5) {
            char ch = VOCALES.charAt(indiceVocal % 5);
            setChar(ch);
            return true;
        }
        return false;
    }

    public void cambiar(char ch) {
        setChar(ch);
    }

    public boolean es(Letra letra) {
        return getChar() == letra.getChar();
    }

    public boolean es(char ch) {
        return getChar() == ch;
    }

    public boolean es(char... caracteres) {
        char caracterPalabra = getChar();
        for (char caracter : caracteres)
            if (caracter == caracterPalabra)
                return true;
        return false;
    }
}
