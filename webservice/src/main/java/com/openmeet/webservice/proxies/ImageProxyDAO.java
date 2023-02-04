package com.openmeet.webservice.proxies;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmeet.shared.data.image.Image;
import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericProxyDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.webservice.exceptions.InvalidParameterException;
import jakarta.servlet.http.HttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class ImageProxyDAO extends ProxyDAO<Image> implements DAO<Image> {

    public ImageProxyDAO(DAO<Image> dao, HttpServletRequest request, PrintWriter out) {
        super(dao, request, out);
    }

    @Override
    public List<Image> doRetrieveByCondition(String condition) throws SQLException {

        condition = request.getParameter("condition");

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveByCondition() - condition: " + condition);

        List<Image> images = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveByCondition() - images: " + images);

        return images;

    }

    public List<Image> doRetrieveByCondition(String condition, int row_count) throws SQLException {

        condition = request.getParameter("condition");
        row_count = Integer.parseInt(request.getParameter("row_count"));

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        if (row_count <= 0) {
            throw new InvalidParameterException("Rows_count parameter must be greater than 0");
        }

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveByCondition() - condition: " + condition + " LIMIT " + row_count);

        List<Image> images = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, 0, row_count, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveByCondition() - bans: " + images);

        return images;

    }

    @Override
    public List<Image> doRetrieveByCondition(String condition, int offset, int row_count) throws SQLException {

        condition = request.getParameter("condition");
        offset = Integer.parseInt(request.getParameter("offset"));
        row_count = Integer.parseInt(request.getParameter("row_count"));

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        if (offset < 0) {
            throw new InvalidParameterException("Offset parameter cannot contain a negative value");
        }

        if (row_count <= 0) {
            throw new InvalidParameterException("Rows_count parameter must be greater than 0");
        }

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveByCondition() - condition: " + condition + " LIMIT " + offset + ", " + row_count);

        List<Image> images = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, offset, row_count, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveByCondition() - images: " + images);

        return images;

    }

    @Override
    public Image doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        key = request.getParameter("key");

        if (!ResponseHelper.checkStringFields(key)) {
            throw new InvalidParameterException("Missing parameters - key");
        }

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveByKey() - key: " + key);

        Image image = GenericProxyDAO.genericProxyDoRetrieveByKey(key, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveByKey() - image: " + image);

        return image;

    }

    @Override
    public List<Image> doRetrieveAll() throws SQLException {

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveAll()");

        List<Image> images = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE", dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveAll() - images: " + images);

        return images;

    }

    @Override
    public List<Image> doRetrieveAll(int row_count) throws SQLException {

        row_count = Integer.parseInt(request.getParameter("row_count"));

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveAll() - row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }

        List<Image> images = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE LIMIT " + row_count, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveAll() - images: " + images);

        return images;

    }

    @Override
    public List<Image> doRetrieveAll(int offset, int row_count) throws SQLException {

        row_count = Integer.parseInt(request.getParameter("row_count"));
        offset = Integer.parseInt(request.getParameter("offset"));

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveAll() - offset: " + offset + ", row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }
        if (offset < 0) {
            throw new InvalidParameterException("Invalid parameters - offset");
        }

        List<Image> images = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doRetrieveAll() - images: " + images);

        return images;

    }

    @Override
    public boolean doSave(Image obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("image"), Image.class);

        logger.log(Level.INFO, "ImageProxyDAO:doSave() - obj: " + obj);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSave(obj, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);

        logger.log(Level.INFO, "ImageProxyDAO:doSave() - values: " + values);

        byte[] meeterData = Base64.getDecoder().decode((String) values.get("meeterId"));
        int meeterId = 0;

        for (byte b: meeterData) {
            meeterId = (meeterId << 8) + (b & 0xFF);
        }

        String basePath = request.getServletContext().getRealPath("/");
        String meeterUploadPath = "uploads" + File.separator + "meeters" + File.separator +  meeterId;
        File uploadPath = new File(basePath + File.separator + meeterUploadPath);

        if (!uploadPath.exists()) {
            if (!uploadPath.mkdirs()) {
                ResponseHelper.sendGenericError(out);
                return false;
            }
        }

        String filename = meeterUploadPath + File.separator + String.valueOf(System.currentTimeMillis()) + ".png";

        Image image = new Image();
        byte[] photoData = Base64.getDecoder().decode((String) values.get("photoByteArray"));

        ByteArrayInputStream bis = new ByteArrayInputStream(photoData);

        try {
            BufferedImage bImage = ImageIO.read(bis);
            if (ImageIO.write(bImage, "png", new File(basePath + File.separator + filename))) {
                image.setPath(filename);
                image.setMeeterId(1);
            } else {
                ResponseHelper.sendGenericError(out);
                return false;
            }
        } catch (IOException e) {
            ResponseHelper.sendGenericError(out);
            return false;
        }

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSave(image, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);
        condition = request.getParameter("condition");

        logger.log(Level.INFO, "ImageProxyDAO:doUpdate() - values: " + values + ", condition: " + condition);

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        boolean isSuccessful = GenericProxyDAO.genericProxyDoUpdate(values, condition, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doSaveOrUpdate(Image obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("image"), Image.class);

        logger.log(Level.INFO, "ImageProxyDAO:doSaveOrUpdate() - obj: " + obj);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSaveOrUpdate(obj, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doSaveOrUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doDelete(String condition) throws SQLException {

        condition = request.getParameter("condition");

        logger.log(Level.INFO, "ImageProxyDAO:doDelete() - condition: " + condition);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoDelete(condition, dao, out);

        logger.log(Level.INFO, "ImageProxyDAO:doDelete() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }
}
