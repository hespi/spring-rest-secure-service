package org.ledgerty.business.common;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.ledgerty.exceptions.BaseException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Héctor on 05/06/2017.
 */
public final class ValidationUtils {

    /** METHODS **/

    public static <T extends BaseException> void validateNotNullOrEmpty(Class<T> exceptionType, Object object, String message) throws T {
        if (object == null) {
            throwGenericException(exceptionType, message);
        }

        if ((object instanceof Collection<?>) && ((Collection<?>)object).isEmpty()) {
            throwGenericException(exceptionType, message);
        }

        if (StringUtils.isEmpty(object.toString())) {
            throwGenericException(exceptionType, message);
        }
    }

    /** FUNCTIONS **/

    private static <T extends BaseException> void throwGenericException(Class<T> exceptionType, String message) throws T {
        try {
            BaseException exception = exceptionType.getConstructor(String.class).newInstance(message);
            throw exception;
        }
        catch (NoSuchMethodException ex) {}
        catch (InstantiationException ex) {}
        catch (IllegalAccessException ex) {}
        catch (InvocationTargetException ex) {}
    }

}
