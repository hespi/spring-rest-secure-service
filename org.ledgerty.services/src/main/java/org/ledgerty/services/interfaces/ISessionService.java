package org.ledgerty.services.interfaces;

import org.ledgerty.common.exceptions.InvalidParametersException;
import org.ledgerty.dao.User;
import org.ledgerty.services.security.LedgertySessionToken;
import org.ledgerty.services.exceptions.InvalidTokenException;

/**
 * Created by Héctor on 17/06/2017.
 */
public interface ISessionService {

    /**
     * Generates a user session ledgertySessionToken
     * @param userData User data
     * @return User session ledgertySessionToken
     * @throws InvalidParametersException When user data is null
     */
    LedgertySessionToken generateToken(User userData) throws InvalidParametersException;

    /**
     * Encripts the ledgertySessionToken
     * @param ledgertySessionToken
     * @return Encrypted ledgertySessionToken as string
     * @throws InvalidParametersException When ledgertySessionToken is null
     * @throws InvalidTokenException When ledgertySessionToken is invalid by date
     */
    String encryptToken(LedgertySessionToken ledgertySessionToken) throws InvalidParametersException;

    /**
     * Decrypts a ledgertySessionToken
     * @param encryptedToken
     * @return LedgertySessionToken information in plain
     * @throws InvalidParametersException When encrypted ledgertySessionToken is null or empty
     * @throws InvalidTokenException When decryption process fails or the content of the ledgertySessionToken is invalid
     */
    LedgertySessionToken decryptToken(String encryptedToken) throws InvalidParametersException, InvalidTokenException;
}
