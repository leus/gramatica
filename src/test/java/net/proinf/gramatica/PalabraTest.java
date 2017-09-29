/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("EmptyMethod")
class PalabraTest {
    private Palabra palabra;


    @BeforeEach
    void setup() {
        palabra = new Palabra("palabrilla");
    }

    @Test
    void testToString() {
    }

    @Test
    void equals() {
    }

    @Test
    void clonar() {
    }

    @Test
    void clonar1() {
    }

    @Test
    void procesarPlural() {
    }

    @Test
    void cambiarGenero() {
    }

    @Test
    void cambiarNumero() {
    }

    @Test
    void numero() {
    }

    @Test
    void genero() {
    }

    @Test
    void generoAntepuesto() {
    }

    @Test
    void silabas() {
    }

    @Test
    void acentuacion() {
    }

    @Test
    void enPlural() {
    }

    @Test
    void enMinusculas() {
    }

    @Test
    void enMayusculas() {
    }

    @Test
    void enCapital() {
    }

    @Test
    void sinAcentos() {
    }

    @Test
    void cuantificar() {
        assertEquals("5 palabrillas", palabra.cuantificar(5));
    }

    @Test
    void anteponerArticulo() {
    }

    @Test
    void anteponerDe() {
    }

    @Test
    void anteponerA() {
    }

    @Test
    void enMayusculasSegunPatron() {
    }

}