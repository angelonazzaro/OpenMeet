package com.openmeet.shared.data.storage;

import java.util.HashMap;

/**
 * Implemented by each Bean class.
 * It allows the Bean Object to be handled as an HashMap
 * which simplifies the update and the insert of the
 * Bean's attributes while querying.
 * */
public interface IEntity {

    HashMap<String, ?> toHashMap();
}
