package com.openmeet.shared.data.storage;

import javax.sql.DataSource;

/**
 * Class used to share the same instance of DataSource
 * among the DAOs.
 * */
public abstract class SQLDAO {

    protected final DataSource source;

    public SQLDAO(DataSource source) {
        this.source = source;
    }
}