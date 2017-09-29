package net.proinf.gramatica;

/**
 * Acentos gramaticales.
 *
 * <p>Licencia: <a href="http://creativecommons.org/licenses/GPL/2.0/deed.es">Este software está sujeto a la CC-GNU GPL</a></p>
 * @author Francisco Cascales <fco@proinf.net>
 * @version 0.05, 24-dic-2007 - Inicio del proyecto
 */
public enum Acentuacion {
    aguda, llana, esdrujula, sobreesdrujula;

    public boolean esAguda() { return this == aguda; } // oxítona
    public boolean esLlana() { return this == llana; } // paroxítonas o grave
    public boolean esEsdrujula() { return this == esdrujula; } // proparoxítona
    public boolean esSobreesdrujula() { return this == sobreesdrujula; } // proparoxítona


    @Override public String toString() {
        final String[] nombres = {"aguda","llana","esdrújula","sobreesdrújula" };
        return nombres[ordinal()];
    }
}