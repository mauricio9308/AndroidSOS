package mx.sos.sos.net;

import mx.sos.sos.model.ServicesPayload;
import retrofit.http.GET;
import retrofit.http.Path;

public interface SOSApiInterface {

    @GET("/user/searchByService?service_type=1")
    public ServicesPayload getMedics();

    @GET("/user/searchByService?service_type=2")
    public ServicesPayload getMechanics();

    @GET("/user/searchByService?service_type=3")
    public ServicesPayload getPlumbers();

    @GET("/user/searchByService?service_type=4")
    public ServicesPayload getElectricists();

    @GET("/user/view/?id={id}")
    public ServicesPayload getService( @Path("id") long id );

}
