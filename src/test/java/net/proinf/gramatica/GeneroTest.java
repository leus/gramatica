/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneroTest {
    @Test
    void esMasculino() {
        assertTrue(Genero.segunPalabra(new Palabra("hombre")).esMasculino());
        assertTrue(Genero.segunPalabra(new Palabra("avión")).esMasculino());
        assertTrue(Genero.segunPalabra(new Palabra("perro")).esMasculino());
        assertTrue(Genero.segunPalabra(new Palabra("calor")).esMasculino());
        assertTrue(Genero.segunPalabra(new Palabra("amigo")).esMasculino());
    }

    @Test
    void esFemenino() {
        assertTrue(Genero.segunPalabra(new Palabra("mujer")).esFemenino());
        assertTrue(Genero.segunPalabra(new Palabra("mesa")).esFemenino());
        assertTrue(Genero.segunPalabra(new Palabra("tierra")).esFemenino());
        assertTrue(Genero.segunPalabra(new Palabra("familia")).esFemenino());
        assertTrue(Genero.segunPalabra(new Palabra("cancióncita")).esFemenino());
    }

    @SuppressWarnings("EmptyMethod")
    @Test
    void esNeutro() {
        // assertTrue(Genero.segunPalabra(new Palabra("virgen")).esNeutro());
    }

    @Test
    void contrario() {
        assertTrue(Genero.segunPalabra(new Palabra("mujer")).contrario().esMasculino());
    }

    @Test
    void antepuestoSegunPalabra() {
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("azúcar")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("calor")).esMasculino());

        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("harina")).esFemenino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("concepción")).esFemenino());

        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("agua")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("arpa")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("haba")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("hacha")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("hada")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("águila")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("arma")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("alma")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("ánima")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("áncora,")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("ancla")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("aya")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("ama")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("asta")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("asa")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("haza")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("ágata")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("alga")).esMasculino());
        assertTrue(Genero.antepuestoSegunPalabra(new Palabra("haya")).esMasculino());
    }

}