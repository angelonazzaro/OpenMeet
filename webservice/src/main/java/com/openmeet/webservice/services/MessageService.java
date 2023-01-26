package com.openmeet.webservice.services;

import com.openmeet.shared.data.message.Message;
import com.openmeet.shared.data.message.MessageDAO;
import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.webservice.exceptions.InvalidParameterException;
import com.openmeet.webservice.proxies.MessageProxyDAO;
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

public class MessageService extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String operation = request.getParameter("operation");
        PrintWriter out = response.getWriter();

        //check if all the parameters are present
        if (!ResponseHelper.checkStringFields(operation)) {
            ResponseHelper.sendGenericError(out);
            logger.log(Level.SEVERE, "MessageService:doPost() - Error: missing parameters - operation");
            return;
        }

        MessageDAO messageDAO = new MessageDAO((DataSource) getServletContext().getAttribute("DataSource"));
        MessageProxyDAO messageProxyDAO = new MessageProxyDAO(messageDAO, request, out);

        switch (operation) {
            case DAO.DO_RETRIEVE_BY_CONDITION: {
                try {

                    messageProxyDAO.doRetrieveByCondition(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_BY_KEY: {

                try {
                    messageProxyDAO.doRetrieveByKey(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL: {
                try {
                    messageProxyDAO.doRetrieveAll();
                } catch (SQLException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL_LIMIT: {
                try {
                    messageProxyDAO.doRetrieveAll(0);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_RETRIEVE_ALL_LIMIT_OFFSET: {
                try {
                    messageProxyDAO.doRetrieveAll(0, 0);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE: {
                try {
                    messageProxyDAO.doSave(new Message());
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE_PARTIAL:
                E:
                {
                    try {
                        messageProxyDAO.doSave(new HashMap<>());
                    } catch (SQLException | InvalidParameterException e) {
                        ResponseHelper.sendGenericError(out);
                        logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                        return;
                    }
                    break;
                }
            case DAO.DO_UPDATE: {
                try {
                    messageProxyDAO.doUpdate(null, null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_SAVE_OR_UPDATE: {
                try {
                    messageProxyDAO.doSaveOrUpdate(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            case DAO.DO_DELETE: {
                try {
                    messageProxyDAO.doDelete(null);
                } catch (SQLException | InvalidParameterException e) {
                    ResponseHelper.sendGenericError(out);
                    logger.log(Level.SEVERE, "MessageService:doPost() - Error: " + e.getMessage());
                    return;
                }
                break;
            }
            default:
                ResponseHelper.sendGenericError(out);
                logger.log(Level.SEVERE, "MessageService:doPost() - Error: unknown operation");
                break;
        }
    }

}
