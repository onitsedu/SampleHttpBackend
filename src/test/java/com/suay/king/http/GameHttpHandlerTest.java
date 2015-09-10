package com.suay.king.http;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.suay.king.server.impl.GameServerImpl;
import com.suay.king.utils.Constants;

/**
 * 
 * @author csuay
 *
 */
public class GameHttpHandlerTest {

    private static GameServerImpl server;

    private static final String BASE_URL = "http://localhost:8080/";

    @AfterClass
    public static void before() {
	server.stopServer();
    }

    @BeforeClass
    public static void after() {
	server = new GameServerImpl();
	server.startServer(8080);
    }

    @Test
    public void testLoginEntryPoint() throws IOException {
	HttpURLConnection con = createConnection(BASE_URL + "1/" + Constants.LOGIN_REQUEST, Constants.HTTP_GET);
	con.connect();
	int responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_OK);
	assertNotNull(getResponseBody(con.getInputStream()));
	con.disconnect();
    }

    @Test
    public void testScoreEntryPoint() throws IOException {
	HttpURLConnection con = createConnection(BASE_URL + "1/" + Constants.LOGIN_REQUEST, Constants.HTTP_GET);
	con.connect();
	int responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_OK);
	String sessionKey = getResponseBody(con.getInputStream());
	con.disconnect();
	con = createConnection(BASE_URL + "1/" + Constants.SCORE_REQUEST + Constants.HTTP_QS_BEGIN
		+ Constants.SESSION_KEY_PARAMETER + "=" + sessionKey, Constants.HTTP_POST);
	addBody(con, "1000");
	con.connect();
	responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_OK);
	con.disconnect();
    }

    @Test
    public void testRankingEntryPoint() throws IOException {
	HttpURLConnection con = createConnection(BASE_URL + "2/" + Constants.LOGIN_REQUEST, Constants.HTTP_GET);
	con.connect();
	int responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_OK);
	String sessionKey = getResponseBody(con.getInputStream());
	con.disconnect();
	con = createConnection(BASE_URL + "4/" + Constants.SCORE_REQUEST + Constants.HTTP_QS_BEGIN
		+ Constants.SESSION_KEY_PARAMETER + "=" + sessionKey, Constants.HTTP_POST);
	addBody(con, "9000");
	con.connect();
	responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_OK);
	con.disconnect();

	con = createConnection(BASE_URL + "4/" + Constants.HIGH_SCORE_LIST_REQUEST, Constants.HTTP_GET);
	con.connect();
	responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_OK);
	String body = getResponseBody(con.getInputStream());
	assertTrue("unexpected response = " + body, body.contains("2=9000"));
	con.disconnect();
	con = createConnection(BASE_URL + "2/" + Constants.SCORE_REQUEST + Constants.HTTP_QS_BEGIN
		+ Constants.SESSION_KEY_PARAMETER + "=" + sessionKey, Constants.HTTP_POST);
	addBody(con, "twenty-two");
	con.connect();
	responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_BAD_REQUEST);
	con.disconnect();
    }

    @Test
    public void testBadRequests() throws IOException {
	HttpURLConnection con = createConnection(BASE_URL + "1/" + "TEST", Constants.HTTP_GET);
	con.connect();
	int responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_NOT_FOUND);
	con.disconnect();

	con = createConnection(BASE_URL + "1/" + Constants.LOGIN_REQUEST, Constants.HTTP_POST);
	con.connect();
	responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_BAD_METHOD);
	con.disconnect();

	con = createConnection(BASE_URL + "one/" + Constants.LOGIN_REQUEST, Constants.HTTP_GET);
	con.connect();
	responsecode = con.getResponseCode();

	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_NOT_FOUND);
	con.disconnect();
	con = createConnection(BASE_URL + "2/" + Constants.SCORE_REQUEST, Constants.HTTP_POST);
	con.connect();
	responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_BAD_REQUEST);
	con.disconnect();

	con = createConnection(BASE_URL + "2/" + Constants.SCORE_REQUEST + Constants.HTTP_QS_BEGIN
		+ Constants.SESSION_KEY_PARAMETER + "=qwerty", Constants.HTTP_POST);
	addBody(con, "1500");
	con.connect();
	responsecode = con.getResponseCode();
	assertTrue("bad response code " + responsecode, responsecode == HttpURLConnection.HTTP_UNAUTHORIZED);
	con.disconnect();

    }

    private HttpURLConnection createConnection(String url, String requestMethod) throws IOException {
	HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
	httpConnection.setRequestMethod(requestMethod);
	return httpConnection;
    }

    private String getResponseBody(InputStream inputStream) {
	String responseBody = "";
	Scanner scanner = new Scanner(inputStream);
	if (scanner.hasNext()) {
	    responseBody = scanner.next();
	}
	scanner.close();
	return responseBody;
    }

    private void addBody(HttpURLConnection httpConnection, String body) throws IOException {
	httpConnection.setRequestProperty("Content-Type", "TEXT");
	httpConnection.setRequestProperty("Content-Length", Integer.toString(body.length()));
	httpConnection.setDoOutput(true);
	OutputStream os = httpConnection.getOutputStream();
	os.write(body.getBytes());
	os.flush();
	os.close();
    }

}
