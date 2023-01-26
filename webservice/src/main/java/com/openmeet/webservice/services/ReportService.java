package com.openmeet.webservice.services;

import com.openmeet.shared.data.report.Report;
import com.openmeet.shared.data.report.ReportDAO;
import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.webservice.exceptions.InvalidParameterException;
import com.openmeet.webservice.proxies.ReportProxyDAO;
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

public class ReportService extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String operation = request.getParameter("operation");
        PrintWriter out = response.getWriter();

        //check if all the parameters are present
        if (!ResponseHelper.checkStringFields(operation)) {
            ResponseHelper.sendGenericError(out);
            logger.log(Level.SEVERE, "ReportService:doPost() - Error: missing parameters - operation");
            return;
        }

        ReportDAO reportDAO = new ReportDAO((DataSource) getServletContext().getAttribute("DataSource"));
        ReportProxyDAO reportProxyDAO = new ReportProxyDAO(reportDAO, request, out);

        switch (operation) {
            case DAO.DO_RETRIEVE_BY_CONDITION: {
                try {
                    reportProxyDAO.doRetrieveByCondition(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_BY_KEY: {

                try {
                    reportProxyDAO.doRetrieveByKey(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL: {
                try {
                    reportProxyDAO.doRetrieveAll();
                } catch (SQLException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL_LIMIT: {
                try {
                    reportProxyDAO.doRetrieveAll(0);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL_LIMIT_OFFSET: {
                try {
                    reportProxyDAO.doRetrieveAll(0, 0);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE: {
                try {
                    reportProxyDAO.doSave(new Report());
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE_PARTIAL:
                E:
                {
                    try {
                        reportProxyDAO.doSave(new HashMap<>());
                    } catch (SQLException | InvalidParameterException e) {
                        ResponseHelper.sendGenericError(out);
                        logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                        return;
                    }
                    break;
                }
            case DAO.DO_UPDATE: {
                try {
                    reportProxyDAO.doUpdate(null, null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE_OR_UPDATE: {
                try {
                    reportProxyDAO.doSaveOrUpdate(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_DELETE: {
                try {
                    reportProxyDAO.doDelete(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "ReportService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            default:
                ResponseHelper.sendGenericError(out);
                logger.log(Level.SEVERE, "ReportService:doPost() - Error: unknown operation");
                break;
        }
    }

}
