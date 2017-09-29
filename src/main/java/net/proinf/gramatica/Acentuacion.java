/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

/**
 * Acentos gramaticales.
 */
public enum Acentuacion {
    aguda, llana, esdrujula, sobreesdrujula;

    public boolean esAguda() {
        return this == aguda;
    } // oxítona

    public boolean esLlana() {
        return this == llana;
    } // paroxítonas o grave

    public boolean esEsdrujula() {
        return this == esdrujula;
    } // proparoxítona

    public boolean esSobreesdrujula() {
        return this == sobreesdrujula;
    } // proparoxítona


    @Override
    public String toString() {
        final String[] nombres = {"aguda", "llana", "esdrújula", "sobreesdrújula"};
        return nombres[ordinal()];
    }
}