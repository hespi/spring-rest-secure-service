package org.ledgerty.common;

import sun.misc.BASE64Encoder;

import java.util.UUID;

/**
 * Created by Héctor on 18/06/2017.
 */
public final class GuidUtils {

    public static String generateShortGuid() {
        return new BASE64Encoder().encode(UUID.randomUUID().toString().replaceAll("[=\\/\\+]","-").getBytes());
    }

}
