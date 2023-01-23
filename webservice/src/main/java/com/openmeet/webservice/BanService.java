package com.openmeet.webservice;

import com.openmeet.shared.data.ban.Ban;
import com.openmeet.shared.data.ban.BanDAO;
import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.webservice.proxies.BanProxyDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanService extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String operation = request.getParameter("operation");
        PrintWriter out = response.getWriter();

        //check if all the parameters are present
        if (!ResponseHelper.checkStringFields(operation)) {
            ResponseHelper.sendGenericError(out);
            logger.log(Level.SEVERE, "BanService:doPost() - Error: missing parameters - operation");
            return;
        }

        BanDAO banDAO = new BanDAO((DataSource) getServletContext().getAttribute("DataSource"));
        BanProxyDAO banProxyDAO = new BanProxyDAO(banDAO, request, out);

        switch (operation) {
            case DAO.DO_RETRIEVE_BY_CONDITION: {
                try {

                    banProxyDAO.doRetrieveByCondition(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_BY_KEY: {

                try {
                    banProxyDAO.doRetrieveByKey(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL: {
                try {
                    banProxyDAO.doRetrieveAll();
                } catch (SQLException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL_LIMIT: {
                try {
                    banProxyDAO.doRetrieveAll(0);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL_LIMIT_OFFSET: {
                try {
                    banProxyDAO.doRetrieveAll(0, 0);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE: {
                try {
                    banProxyDAO.doSave(new Ban());
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE_PARTIAL:
                E:
                {
                    try {
                        banProxyDAO.doSave(new HashMap<>());
                    } catch (SQLException | InvalidParameterException e) {
                        ResponseHelper.sendGenericError(out);
                        logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                        return;
                    }
                    break;
                }
            case DAO.DO_UPDATE: {
                try {
                    banProxyDAO.doUpdate(null, null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE_OR_UPDATE: {
                try {
                    banProxyDAO.doSaveOrUpdate(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_DELETE: {
                try {
                    banProxyDAO.doDelete(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "BanService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            default:
                ResponseHelper.sendGenericError(out);
                logger.log(Level.SEVERE, "BanService:doPost() - Error: unknown operation");
                break;
        }
    }

}
