/**
 * Copyright (C) 2011 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.cloudhopper.commons.charset;

// third party imports
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelauer
 */
public class TurkishLockingShiftCharsetTest {
    private static final Logger logger = LoggerFactory.getLogger(TurkishLockingShiftCharsetTest.class);

    @Test
    public void canRepresent() throws Exception {
        // nulls are always ok
        Assert.assertEquals(true, TurkishLockingShiftCharset.canRepresent(null));
        Assert.assertEquals(true, TurkishLockingShiftCharset.canRepresent(" "));
        Assert.assertEquals(true, TurkishLockingShiftCharset.canRepresent("\n\r"));
        Assert.assertEquals(true, TurkishLockingShiftCharset.canRepresent("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        Assert.assertEquals(true, TurkishLockingShiftCharset.canRepresent("Hello @ World"));
        Assert.assertEquals(true, TurkishLockingShiftCharset.canRepresent("{}[]$"));

        Assert.assertEquals(true, TurkishLockingShiftCharset.canRepresent("Aa Üü Ğğ iİ Şş Çç Öö ıI"));
        // euro currency symbol is good
        Assert.assertEquals(true, TurkishLockingShiftCharset.canRepresent("\u20ac"));
        // arabic char is not valid GSM char
        Assert.assertEquals(false, TurkishLockingShiftCharset.canRepresent("\u0623"));

        // Æ char from gsm alphabet replaced by turkish char
        Assert.assertEquals(false, TurkishLockingShiftCharset.canRepresent("Æ"));
    }
}
