package mx.sos.sos.net;

import mx.sos.sos.model.ServicesPayload;
import retrofit.http.GET;
import retrofit.http.Path;

public interface SOSApiInterface {

    @GET("/user/searchByService?service_type=1")
    ServicesPayload getMedics();

    @GET("/user/searchByService?service_type=2")
    ServicesPayload getMechanics();

    @GET("/user/searchByService?service_type=3")
    ServicesPayload getPlumbers();

    @GET("/user/searchByService?service_type=4")
    ServicesPayload getElectricists();

    @GET("/user/view/?id={id}")
    ServicesPayload getService( @Path("id") long id );

}
