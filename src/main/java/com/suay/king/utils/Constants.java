package com.suay.king.utils;

/**
 * 
 * @author csuay
 *
 */
public final class Constants {

    public static final Long SESSION_EXPIRATION_TIME = 600000L;
    public static final Integer LEVEL_MAX_SCORES = 15;

    /* HTTP constants */

    public static final String HTTP_FILTER_DESCRIPTION = "HTTP filter";
    public static final String HTTP_ATT_BODY = "body";
    public static final String HTTP_ATT_PARAMS = "parameters";
    public static final String HTTP_ATT_PATH = "pathParams";
    public static final String HTTP_QS_BEGIN = "?";
    public static final String HTTP_QS_SEPARATOR = "&";
    public static final String HTTP_QS_PARAM_SEP = "=";
    public static final String HTTP_PATH_SEPARATOR = "/";
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TEXT = "text/plain";

    public static final String LEVEL_ID_PARAMETER = "levelid";
    public static final String SESSION_KEY_PARAMETER = "sessionkey";
    public static final String SCORE_PARAMETER = "score";
    public static final String USER_ID_PARAMETER = "userid";

    public static final Integer PORT = 8080;
    public static final String LOCALHOST = "localhost";

    public static final String LOGIN_REQUEST = "login";
    public static final String SCORE_REQUEST = "score";
    public static final String HIGH_SCORE_LIST_REQUEST = "highscorelist";

    public static final String LOGIN_PATTERN = "/(\\d*)/login";
    public static final String SCORE_PATTERN = "/(\\d*)/score.*";
    public static final String HIGH_SCORE_PATTERN = "/(\\d*)/highscorelist";

}
