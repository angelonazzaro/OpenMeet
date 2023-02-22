package com.openmeet.webservice.proxies;

import com.openmeet.shared.data.storage.DAO;
import jakarta.servlet.http.HttpServletRequest;

import java.io.PrintWriter;


/**
 * Proxy class for DAO. It is used to check parameters before calling the DAO methods.
 *
 * @param <T> Generic type.
 * @author Francesco Granozio
 */
public abstract class ProxyDAO<T> {

    protected final DAO<T> dao;
    protected final HttpServletRequest request;
    protected final PrintWriter out;


    public ProxyDAO(DAO<T> dao, HttpServletRequest request, PrintWriter out) {
        this.dao = dao;
        this.request = request;
        this.out = out;
    }
}
