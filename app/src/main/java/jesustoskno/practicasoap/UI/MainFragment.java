package jesustoskno.practicasoap.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import jesustoskno.practicasoap.R;
import jesustoskno.practicasoap.Soapeando.XmlParser;
import jesustoskno.practicasoap.interfaces.IGetTicketTypeList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedString;

/**
 * Created by jtoscano on 09/12/2015.
 */
public class MainFragment extends Fragment {

    private Button mBoton;
    private TextView mTexto;
    private RestAdapter restAdapter;
    protected static final String END_POINT = "http://inetvis.yelmocines.es";
    protected String request = "<v:Envelope\n" +
            "    xmlns:i = \"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "    xmlns:d = \"http://www.w3.org/2001/XMLSchema\"\n" +
            "    xmlns:c = \"http://schemas.xmlsoap.org/soap/encoding/\"\n" +
            "    xmlns:v = \"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <v:Header/>\n" +
            "    <v:Body>\n" +
            "        <n0:GetTicketTypeListRequest xmlns:n0 = \"http://vista.co.nz/services/WSVistaWebClient.DataTypes/1/\">\n" +
            "            <n0:OptionalUserSessionIdForLoyaltyTickets></n0:OptionalUserSessionIdForLoyaltyTickets>\n" +
            "            <n0:OptionalLoyaltyTicketMatchesHOCode>false</n0:OptionalLoyaltyTicketMatchesHOCode>\n" +
            "            <n0:CinemaId>782</n0:CinemaId>\n" +
            "            <n0:SessionId>2213</n0:SessionId>\n" +
            "            <n0:OptionalShowNonATMTickets>false</n0:OptionalShowNonATMTickets>\n" +
            "            <n0:OptionalReturnAllRedemptionAndCompTickets>false</n0:OptionalReturnAllRedemptionAndCompTickets>\n" +
            "            <n0:OptionalReturnAllLoyaltyTickets>false</n0:OptionalReturnAllLoyaltyTickets>\n" +
            "            <n0:OptionalAreaCategoryCode></n0:OptionalAreaCategoryCode>\n" +
            "            <n0:OptionalClientClass>WWW</n0:OptionalClientClass>\n" +
            "            <n0:OptionalReturnLoyaltyRewardFlag>false</n0:OptionalReturnLoyaltyRewardFlag>\n" +
            "            <n0:OptionalSeparatePaymentBasedTickets>false</n0:OptionalSeparatePaymentBasedTickets>\n" +
            "            <n0:OptionalShowLoyaltyTicketsToNonMembers>false</n0:OptionalShowLoyaltyTicketsToNonMembers>\n" +
            "            <n0:OptionalEnforceChildTicketLogic>false</n0:OptionalEnforceChildTicketLogic>\n" +
            "        </n0:GetTicketTypeListRequest>\n" +
            "    </v:Body>\n" +
            "</v:Envelope>";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        setUI(v);
        setRestAdapter();
        soapeameOnclick();
        return v;
    }

    public void setUI(View v) {
        mBoton = (Button) v.findViewById(R.id.boton);
        mTexto = (TextView) v.findViewById(R.id.texto);
    }

    private void setRestAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();
    }

    private void soapeameOnclick() {
        mBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypedString getTicketTypeList = new TypedString(request);
                IGetTicketTypeList listInterface = restAdapter.create(IGetTicketTypeList.class);
                listInterface.post(getTicketTypeList, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        String xmlStr = new String(((TypedByteArray) response.getBody()).getBytes());
                        XmlParser xmlParser = new XmlParser();
                        try {
                            xmlParser.readXml(xmlStr);

                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mTexto.setText(error.getBody().toString());
                    }
                });
            }
        });
    }
}
