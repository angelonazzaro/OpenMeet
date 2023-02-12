package com.openmeet.shared.data.storage;

import javax.sql.DataSource;

/**
 * Class used for sharing the same instance of DataSource
 * among the DAOs.
 *
 * @author Francesco Granozio
 * @author Angelo Nazzaro
 * */
public abstract class SQLDAO {

    protected final DataSource source;

    public SQLDAO(DataSource source) {
        this.source = source;
    }
}