package org.ledgerty.business.interfaces;

import org.ledgerty.common.database.GenericEntityManager;

/**
 * Created by Héctor on 11/06/2017.
 */
public interface IBaseManager<T> {

    /** DEPENDENCIES **/

    GenericEntityManager<T> getEntityManager();


}
