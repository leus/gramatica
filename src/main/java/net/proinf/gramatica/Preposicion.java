/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

/**
 * Preposición
 *
 * <p>Licencia: <a href="http://creativecommons.org/licenses/GPL/2.0/deed.es">Este software está sujeto a la CC-GNU GPL</a></p>
 *
 * @author Francisco Cascales <fco@proinf.net>
 * @version 0.07,  1-ene-2008 - Inicio del proyecto
 */
public enum Preposicion {
    a, allende, ante, aquende, bajo, cabe, con, contra, de, desde, durante,
    en, entre, excepto, hacia, hasta, mediante, para, por, pro,
    según, sin, so, sobre, tras, versus, vía;

    /** Indica si se trata de una preposición */
    public static boolean es(Palabra palabra){
        for(Preposicion preposicion: values())
            if (preposicion.name().equals(palabra.toString()))
                return true;
        return false;
    }
}