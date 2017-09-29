package net.proinf.gramatica;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Género gramatical.
 *
 * <dl>
 * <dt>Referencias:</dt>
 * <dd><a href="http://es.wikipedia.org/wiki/G%C3%A9nero_gramatical">Género gramatical</a> - La Wikipedia</dd>
 * <dd><a href="http://es.wikipedia.org/wiki/Gram%C3%A1tica_del_espa%C3%B1ol#Art.C3.ADculos">Grámatica del español</a> - La Wikipedia</dd>
 * </dl>
 *
 * <p>Licencia: <a href="http://creativecommons.org/licenses/GPL/2.0/deed.es">Este software está sujeto a la CC-GNU GPL</a></p>
 * @author Francisco Cascales <fco@proinf.net>
 * @version 0.05, 24-dic-2007 - Inicio del proyecto
 * @version 0.06, 31-dic-2007 - Modificado el sistema de terminaciones y excepciones
 * @version 0.07,  2-ene-2008 - Detección del género a aplicar de la palabra anterior <code>antepuestoSegunPalabra</code>
 * @version 0.08,  3-ene-2008 - Añadido muchas nuevas exepciones
 */
public enum Genero {
    neutro, masculino, femenino;

    public boolean esMasculino() { return this == masculino; }
    public boolean esFemenino() { return this == femenino; }
    public boolean esNeutro() { return this == neutro; }

    public Genero contrario() {
        if (esMasculino()) return femenino;
        else if (esFemenino()) return masculino;
        else return neutro;
    }

    ////////////////////////////////////////
    // INFORMACIÓN RECOPILADA

    /*
       Palabras masculinas o femeninas según el concepto:
         orden,
    */

    private final static Terminaciones terminacionesSingulares = new Terminaciones();
    static {

        terminacionesSingulares.agregar("umbre", femenino); // Ej: legumbre,certidumbre,pesadumbre
        terminacionesSingulares.agregar("triz", femenino); // Ej: actriz,emperatriz,instituriz,matriz
        terminacionesSingulares.agregar("icie", femenino); // Ej: superficie
        terminacionesSingulares.agregar("esa", femenino); // Ej: abadesa,empresa,fresa,mesa,remesa
        terminacionesSingulares.agregar("isa", femenino); // Ej: poetisa,prisa,camisa
        terminacionesSingulares.agregar("ína", femenino); // Ej: heroína, cafeína
        terminacionesSingulares.agregar("ión", femenino, "anfitrión","aluvión","avión","camión","centurión","embrión","gorrión","guión"); // Ej: nación,televisión,canción,prisión,ecuación,ilusión
        terminacionesSingulares.agregar("dad", femenino, "abad"); // Ej: verdad,cantidad,atrocidad,entidad,felicidad,claridad,unidad
        terminacionesSingulares.agregar("tad", femenino); // Ej: amistad,mitad
        terminacionesSingulares.agregar("tud", femenino); // Ej: lentitud
        terminacionesSingulares.agregar("ies", femenino); // Ej: mies
        terminacionesSingulares.agregar("ud", femenino, "alud"); // Ej: salud,juventud
        terminacionesSingulares.agregar("ez", femenino, "ajedrez","juez","pez","diez"); // Ej: pesadez,estupidez,sensatez,tez,vez,hez,tez
        terminacionesSingulares.agregar("is", femenino, "análisis","chasis","cutis","dieciséis","frontis","iris","parchís","paréntesis","pubis","seis","tenis"); // Ej: tesis,praxis,génesis,acrópolis
        //
        terminacionesSingulares.agregar("ma", masculino, "alma","ama","ánima","apotema","arma","cama","forma");  // Ej: fantasma,estigma,magma,apotegma,idioma,reuma
        terminacionesSingulares.agregar("o", masculino, "dinamo","mano","radio","seo"); // Ej: pelo,inodoro
        //
        terminacionesSingulares.agregar("a", femenino, "capicúa","día","futbolista","koala","mapa","poeta"); // Ej: casa,masa,taza
        terminacionesSingulares.agregar("z", femenino, "haz","lápiz","tamiz","avestruz","regaliz","maíz"); // Ej: perdiz,raíz,vez,tez,vejez,hoz,voz,faz,nuez,paz,luz
        //
        terminacionesSingulares.agregar("e", masculino, "base","clase","clave","constante","corriente","debacle","elipse","espiral","falange","fase","fe","fiebre","frase","frente","fuente","gente","hélice","higiene","ingle","leche","lente","liebre","llave","madre","masacre","muerte","nave","nieve","noche","nube","patente","peste","plebe","sangre","serie","serpiente","suerte","tele","tilde","torre","ubre","variable"); // Ej: volante,elefante,jinete,once,cofre,paje
        terminacionesSingulares.agregar("l", masculino, "cal","cárcel","col","diagonal","espiral","hiel","integral","miel","moral","sal","señal","versal"); // Ej: árbol,funeral,arancel,canal,caudal,mármol,mal,gel,móvil
        terminacionesSingulares.agregar("n", masculino, "crin","desazón","imagen","razón","sartén","sinrazón","virgen"); // Ej: cinturón,tirón,buzón,eslabón,flan
        terminacionesSingulares.agregar("r", masculino, "coliflor","flor","mujer","sor"); // Ej: olor,actor,mar,calamar,pintor,entrenador,radar,nenúfar
        terminacionesSingulares.agregar("s", masculino, "diabetes","res","tos","venus"); // Ej: virus,ciempiés,dos,tres,adiós,más,mes
        terminacionesSingulares.agregar("á", masculino, "mamá"); // Ej: sofá,maná,marajá
        terminacionesSingulares.agregar("é", masculino, "matiné"); // Ej: café,puré,carné,traspié,bebé,bidé
        terminacionesSingulares.agregar("í", masculino); // Ej: pedigrí,maniquí,jabalí,frenesí,rubí
        terminacionesSingulares.agregar("ó", masculino, "gogó"); // Ej: plató,rondó,yoyó
        terminacionesSingulares.agregar("ú", masculino); // Ej: tabú,vudú,menú
        //
        terminacionesSingulares.agregar(null, masculino, "ram","red","sed","web"); // Excepciones femeninas
        terminacionesSingulares.agregar(null, femenino, "rey","reloj","ardid","hábitat","robot"); // Excepciones masculinas
        //
    }
    private final static Terminaciones terminacionesPlurales = new Terminaciones();
    static {
        terminacionesPlurales.agregar("umbres", femenino);
        terminacionesPlurales.agregar("trices", femenino);
        terminacionesPlurales.agregar("icies", femenino);
        terminacionesPlurales.agregar("iones", femenino, "aviones","camiones");
        terminacionesPlurales.agregar("dades", femenino);
        terminacionesPlurales.agregar("tades", femenino);
        terminacionesPlurales.agregar("tudes", femenino);
        terminacionesPlurales.agregar("ieses", femenino);
        terminacionesPlurales.agregar("esas", femenino);
        terminacionesPlurales.agregar("isas", femenino);
        terminacionesPlurales.agregar("inas", femenino);
        terminacionesPlurales.agregar("udes", femenino, "aludes");
        terminacionesPlurales.agregar("eces", femenino, "dieces","haces","peces");
        terminacionesPlurales.agregar("ces", femenino, "avestruces","jueces","lápices","tamices");
        //
        terminacionesPlurales.agregar("mas", masculino, "almas","amas","ánimas","apotemas","armas","camas","formas");
        terminacionesPlurales.agregar("les", masculino, "cárceles","coles","espirales","integrales","mieles","sales","señales","variables","versales");
        terminacionesPlurales.agregar("nes", masculino, "crines","razones","sartenes");
        terminacionesPlurales.agregar("res", masculino, "flores","liebres","madres","mujeres","torres");
        terminacionesPlurales.agregar("ses", masculino, "bases","clases","elipses","frases","reses","toses");
        terminacionesPlurales.agregar("os", masculino, "dinamos","manos","radios","seos");
        //
        terminacionesPlurales.agregar("as", femenino, "días","koalas","mapas","poetas");
        //
        terminacionesPlurales.agregar("es", masculino, "claves","constantes","fes","fuentes","leches","llaves","muertes","naves","pestes","redes","series");
        terminacionesPlurales.agregar("ás", masculino, "mamás");
        terminacionesPlurales.agregar("és", masculino);
        terminacionesPlurales.agregar("ís", masculino);
        terminacionesPlurales.agregar("ó", masculino);
        terminacionesPlurales.agregar("ús", masculino);
        //
    }

    ////////////////////////////////////////
    // INTERFAZ

    /**
     * Retorna el género de la palabra.
     * <dl>
     * <dt>Referencias:</dt>
     * <dd><a href="http://es.wikibooks.org/wiki/Espa%C3%B1ol/Morfolog%C3%ADa/La_palabra/El_sustantivo/El_g%C3%A9nero">Español/Morfología/La palabra/El sustantivo/El género</a> - Wikilibros</dd>
     * <dt>Ejemplos:</dt>
     * <dd>"árbol" &rarr; <q>masculino</q></dd>
     * <dd>"camisa" &rarr; <q>femenino</q></dd>
     * </dl>
     */
    public static Genero segunPalabra(Palabra palabra) {
        if (palabra.estaVacia()) return neutro;
        else if (palabra.primeraLetra().esDigito()) return masculino;
        else if (palabra.numeroLetras() == 1) return femenino;

        Terminaciones terminaciones = terminacionesSingulares;
        if (palabra.numero().esPlural()) terminaciones = terminacionesPlurales;

        for (Terminacion terminacion: terminaciones) {
            if (terminacion.esExcepcion(palabra))
                return terminacion.genero.contrario();
            else if (terminacion.de(palabra))
                return terminacion.genero;
        }
        return masculino; // rey, reloj, ardid, hábitat, robot
    }

    /**
     * Retorna el género a usar delante de la palabra.
     * <p>Si tenemos el siguiente caso:</p>
     * <ul>
     * <li>Una palabra femenina en singular que empieza por <q>a</q> o <q>ha</q> tónica
     *     como: <q>agua</q>, <q>hada</q>, <q>águila</q>, <q>arma</q>,
     *     <q>alma</q>, <q>ánima</q>, <q>áncora</q>, <q>ancla</q>, etc.
     * </li>
     * <li>Una palabra que le precede que debe concordar en género pero que acaba por <q>a</q>
     *     como: <q>la</q>, <q>una</q>, <q>ninguna</q>, <q>aquella</q> &hellip;
     * </li>
     * </ul>
     * <p>entonces la palabra que le precede se toma en su versión masculina:
     *   <q>el</q>, <q>un</q>, <q>ningún</q>, <q>aquel</q>, etc.</p>
     *
     * <p>Ejemplos: <q>el agua</q>, <q>un hada</q>, <q>ningún águila</q>,
     *     <q>del agua</q>, <q>al águila</q>, <q>aquel ancla</q>, etc.</p>
     */
    public static Genero antepuestoSegunPalabra (Palabra palabra) {
        // Ej: agua, arpa, haba, hacha, hada, águila, arma, alma, ánima, áncora,
        //     ancla, aya, ama, asta, asa, haza, ágata, alga, haya
        //
        boolean usarMasculinoEnVezFemenino =
                palabra.genero().esFemenino()
                        && palabra.numero().esSingular()
                        && palabra.empiezaPor("a","ha","á","há")
                        && palabra.silabas().primeraSilaba().esTonica();
        if (usarMasculinoEnVezFemenino) return masculino;
        else return palabra.genero();
    }

    //////////////////////////////////////////////////
    // CLASES INTERNAS

    private static class Terminacion {
        String terminacion;
        Genero genero;
        String[] excepciones;

        Terminacion(String terminacion, Genero genero, String... excepciones) {
            this.terminacion = terminacion;
            this.genero = genero;
            this.excepciones = excepciones;
        }
        boolean esExcepcion (Palabra palabra) {
            for (String excepcion: excepciones)
                if (palabra.es(excepcion)) return true;
            return false;
        }
        boolean de (Palabra palabra) {
            if (terminacion == null) return false;
            else return palabra.acabaEn(terminacion);
        }
    }
    private static class Terminaciones implements Iterable<Genero.Terminacion> {
        private ArrayList<Terminacion> list = new ArrayList<Terminacion>();
        // Iterador
        public Iterator<Genero.Terminacion> iterator() { return new TerminacionesIterator(); }
        private class TerminacionesIterator implements java.util.Iterator<Terminacion> {
            int posicion = 0;
            public boolean hasNext() { return posicion < list.size(); }
            public Terminacion next() { return list.get(posicion++); }
            public void remove() { list.remove(posicion--); }
        }
        void agregar (String terminacion, Genero genero, String... excepciones) {
            list.add(new Terminacion(terminacion,genero,excepciones));
        }
        /*void ordenar() {
            //java.util.Arrays.sort(T[] a, Comparator <? super T> c) ...
        }*/
    }
};