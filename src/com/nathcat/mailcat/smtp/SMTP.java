package com.nathcat.mailcat.smtp;

import com.nathcat.mailcat.smtp.exceptions.MissingConfigParameter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.IOException;

public class SMTP {
    public static final String CRLF = "\r\n";
    public static final String SP = "\\s+";
    public static final String CONFIG_PATH = "Assets/SMTP_Config.json";
    public static String domain;
    public static int port = -1;

    /**
     * Get the SMTP configuration. Should be specified at path <code>Assets/SMTP_Config.json</code>.
     * <pre>
     *     {
     *         "domain": String -> The local domain name,
     *         "port": Integer -> The port the server should run on,
     *     }
     * </pre>
     * @throws IOException Thrown in case the file cannot be read for whatever reason
     * @throws ParseException Thrown if the JSON contained within the file has a syntax error
     * @throws MissingConfigParameter Thrown if a required parameter is missing from the configuration
     */
    public static void getConfig() throws IOException, ParseException, MissingConfigParameter {
        JSONObject config = (JSONObject) new JSONParser().parse(
            new String(new FileInputStream(CONFIG_PATH).readAllBytes())
        );

        domain = (String) config.get("domain");
        if (domain == null) throw new MissingConfigParameter("domain");

        port = Math.toIntExact((long) config.get("port"));
        if (port == -1) throw new MissingConfigParameter("port");
    }
}
