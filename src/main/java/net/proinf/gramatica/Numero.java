package net.proinf.gramatica;

/**
 * Número gramatical.
 *
 * <dl>
 * <dt>Referencias:</dt>
 * <dd><a href="http://es.wikipedia.org/wiki/N%C3%BAmero_gramatical">Número gramatical</a> - La Wikipedia</dd>
 * </dl>
 *
 * <p>Licencia: <a href="http://creativecommons.org/licenses/GPL/2.0/deed.es">Este software está sujeto a la CC-GNU GPL</a></p>
 * @author Francisco Cascales <fco@proinf.net>
 * @version 0.05, 24-dic-2007 - Inicio del proyecto
 * @version 0.07,  2-ene-2008 - Añadido muchas palabras singulares acabadas en "s"
 * @version 0.08,  3-ene-2008 - Tomar la terminación "-nés" como singular por ser gentilicio
 */
public enum Numero {
    singular, plural;

    public boolean esSingular() { return this == singular; }
    public boolean esPlural() { return this == plural; }

    /** Es singular aunque acabe en "s" */
    private final static String[] excepcionesSingulares = {
            // Números
            "dos","tres","seis","dieciséis",
            // Palabras simples:
            "acrópolis","adiós","albatros","alias","análisis","anís","antivirus",
            "arnés","atlas","autobús","barrabás",
            "bilis","brindis","burdeos","burqués","bus","campus","caos","chasis",
            "ciempiés","ciprés","compás","cosmos","crisis","croquis",
            "cutis","diabetes","dios","dosis","entremés","equis",
            "estrés","frontis","gris","iris","gas","génesis","guaperas",
            "interés","lapsus","lis",
            "más","marqués","mecenas","mes","mímesis",
            "oasis","obús","ósmosis","país","parchís","paréntesis","pelanas",
            "pelvis","plus","prótesis","psicosis","pubis","pus",
            "ras","res","sintaxis","síntesis","tenis","tesis","tos","revés",
            "vals","virus",
            // Días de la semana
            "lunes","martes","miércoles","jueves","viernes",
            // Palabras compuestas:
            "abrelatas","cascanueces","guardabosques","limpiaparabrisas",
            "parabrisas","parachoques","paraguas","pasamontañas",
            "rascacielos",
    };
    /** Es plural aunque no acabe en "s" */
    private final static String[] excepcionesPlurales = {
            //"kelvin",
    };

    /** Si acaba en <q>s</q> se considera plural. */
    public static Numero segunPalabra (Palabra palabra) {
        if (palabra.estaVacia()) return singular;

        for (String excepcion: excepcionesSingulares)
            if (palabra.es(excepcion)) return singular;

        for (String excepcion: excepcionesPlurales)
            if (palabra.es(excepcion)) return plural;

        if (palabra.acabaEn("nés")) return singular; // barcelonés, danés, japonés
        else if (palabra.ultimaLetra().es('s')) return plural;
        else return singular;
    }
};
