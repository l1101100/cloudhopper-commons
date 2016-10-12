package com.cloudhopper.commons.charset;

/*
 * #%L
 * ch-commons-charset
 * %%
 * Copyright (C) 2012 - 2014 Cloudhopper by Twitter
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


// third party imports

import com.cloudhopper.commons.util.HexUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelauer
 */
public class TurkishSingleShiftCharsetTest {
    private static final Logger logger = LoggerFactory.getLogger(TurkishSingleShiftCharsetTest.class);

    @Test
    public void canRepresent() throws Exception {
        // nulls are always ok
        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent(null));
        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent(" "));
        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent("\n\r"));
        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent("Hello @ World"));
        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent("{}[]$"));

        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent("Aa Üü Ğğ iİ Şş Çç Öö ıI"));
        // euro currency symbol is good
        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent("\u20ac"));
        // arabic char is not valid GSM char
        Assert.assertEquals(false, TurkishSingleShiftCharset.canRepresent("\u0623"));

        // gsm char Æ
        Assert.assertEquals(true, TurkishSingleShiftCharset.canRepresent("Æ"));
    }

    @Test
    public void encodingTest() throws Exception {
        byte[] textBytes = CharsetUtil.encode("Test smpp ğışİ", CharsetUtil.CHARSET_TURKISH_SINGLE_SHIFT);
        byte[] udh = HexUtil.toByteArray("03240101");

        byte[] textBytesWithUdh = new byte[textBytes.length + udh.length];

        // udh
        System.arraycopy(udh, 0, textBytesWithUdh, 0, udh.length);

        // message payload
        System.arraycopy(textBytes, 0, textBytesWithUdh, udh.length, textBytes.length);


        byte[] expected = HexUtil.toByteArray("032401015465737420736D7070201B671B691B731B49");

        Assert.assertArrayEquals(expected, textBytesWithUdh);

    }
}
