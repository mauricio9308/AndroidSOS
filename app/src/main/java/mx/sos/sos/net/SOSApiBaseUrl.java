package mx.sos.sos.net;


public final class SOSApiBaseUrl {

    private static final String PROTOCOL = "http";
    private static final String DOMAIN_NAME = "192.168.11.63";
    private static final int VERSION = 1;
    public static final String URL_API_BASE = PROTOCOL + "://" + DOMAIN_NAME + "/api/v" + VERSION;

    private SOSApiBaseUrl() {
        /* Do nothing */
    }
}
