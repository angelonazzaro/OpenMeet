package com.openmeet.shared.data.storage;

import java.util.HashMap;

/**
 * Implemented by each Bean class.
 * It allows the Bean Object to be handled as an HashMap
 * which simplifies the update and the insert of the
 * Bean's attributes while querying.
 *
 * @author Francesco Granozio
 * @author Angelo Nazzaro
 * */
public interface IEntity {

    HashMap<String, ?> toHashMap();
}
