/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

/**
 * El abedario
 *
 * <p>Licencia: <a href="http://creativecommons.org/licenses/GPL/2.0/deed.es">Este software está sujeto a la CC-GNU GPL</a></p>
 * @version 0.07,  1-ene-2008 - Inicio del proyecto
 */
public enum Abecedario {
    a, b, c, d, e, f, g, h, i,
    j, k, l, m, n, ñ, o, p, q,
    r, s, t, u, v, w, x, y, z;

    private final static double frecuencias [] = {
            11.96,  0.92,  2,92, // abc
            6.87, 16.78,  0.52, // def
            0.73,  0.89,  4.15, // ghi
            0.30,  0.01,  8.37, // jkl
            2.12,  7.01,  0.29, // mnñ
            8.69,  2.77,  1.53, // opq
            4.94,  7.88,  3.31, // rst
            4.80,  0.39,  0.01, // uvw
            0.06,  1.54,  0.15  // xyz
    };

    /**
     * Frecuencia del uso de las letras
     * <a href="http://es.wikipedia.org/wiki/Ortograf%C3%ADa_del_espa%C3%B1ol">Ortografía del español</a>
     */
    public double frecuenciaUso () {
        return frecuencias[this.ordinal()];
    }

}