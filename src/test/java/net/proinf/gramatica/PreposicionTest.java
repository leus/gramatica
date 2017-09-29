/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PreposicionTest {
    @org.junit.jupiter.api.Test
    void es() {
        assertTrue(Preposicion.es(new Palabra("Vía")));
    }

}