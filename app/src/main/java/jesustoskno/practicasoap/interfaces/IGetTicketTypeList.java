package jesustoskno.practicasoap.interfaces;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.mime.TypedString;

/**
 * Created by jtoscano on 09/12/2015.
 */
public interface IGetTicketTypeList {

    @POST("/wsvistawebclient/DataService.asmx")
    @Headers({"Content-Type: text/xml;charset=UTF-8","SOAPAction: http://vista.co.nz/services/WSVistaWebClient.ServiceContracts/1/GetTicketTypeList"})
    void post(@Body TypedString getTicketTypeList, Callback<Response> responseCallback);

}
