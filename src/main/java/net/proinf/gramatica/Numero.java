/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

/**
 * Número gramatical.
 * <p>
 * <dl>
 * <dt>Referencias:</dt>
 * <dd><a href="http://es.wikipedia.org/wiki/N%C3%BAmero_gramatical">Número gramatical</a> - La Wikipedia</dd>
 * </dl>
 */
public enum Numero {
    singular, plural;

    /**
     * Es singular aunque acabe en "s"
     */
    private final static String[] excepcionesSingulares = {
            // Números
            "dos", "tres", "seis", "dieciséis",
            // Palabras simples:
            "acrópolis", "adiós", "albatros", "alias", "análisis", "anís", "antivirus",
            "arnés", "atlas", "autobús", "barrabás",
            "bilis", "brindis", "burdeos", "burqués", "bus", "campus", "caos", "chasis",
            "ciempiés", "ciprés", "compás", "cosmos", "crisis", "croquis",
            "cutis", "diabetes", "dios", "dosis", "entremés", "equis",
            "estrés", "frontis", "gris", "iris", "gas", "génesis", "guaperas",
            "interés", "lapsus", "lis",
            "más", "marqués", "mecenas", "mes", "mímesis",
            "oasis", "obús", "ósmosis", "país", "parchís", "paréntesis", "pelanas",
            "pelvis", "plus", "prótesis", "psicosis", "pubis", "pus",
            "ras", "res", "sintaxis", "síntesis", "tenis", "tesis", "tos", "revés",
            "vals", "virus",
            // Días de la semana
            "lunes", "martes", "miércoles", "jueves", "viernes",
            // Palabras compuestas:
            "abrelatas", "cascanueces", "guardabosques", "limpiaparabrisas",
            "parabrisas", "parachoques", "paraguas", "pasamontañas",
            "rascacielos",
    };
    /**
     * Es plural aunque no acabe en "s"
     */
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private final static String[] excepcionesPlurales = {
            //"kelvin",
    };

    /**
     * Si acaba en <q>s</q> se considera plural.
     */
    public static Numero segunPalabra(Palabra palabra) {
        if (palabra.estaVacia()) return singular;

        for (String excepcion : excepcionesSingulares)
            if (palabra.es(excepcion)) return singular;

        for (String excepcion : excepcionesPlurales)
            if (palabra.es(excepcion)) return plural;

        if (palabra.acabaEn("nés")) return singular; // barcelonés, danés, japonés
        else if (palabra.ultimaLetra().es('s')) return plural;
        else return singular;
    }

    public boolean esSingular() {
        return this == singular;
    }

    public boolean esPlural() {
        return this == plural;
    }
}
