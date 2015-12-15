package jesustoskno.practicasoap.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    public static final String TABLE = "Table";
    public static final String IS_PAID = "IsPaid";
    public static final String AVAILABLE_ON_SALES_CHANNEL = "AvailableOnSalesChannel";
    public static final String IS_DISPLAYEDsTANDARD = "IsDisplayedStandard";
    public static final String IS_LOYALTY_ONLY = "IsLoyaltyOnly";
    public static final String ORDER_PRICE_BY = "OrderPricesBy";
    public static final String TICKET_HAS_BARCODE = "TicketHasBarcode";
    public static final String CINEMA_STR_ID = "Cinema_strID";
    public static final String SESSION_STR_ID = "Session_strID";
    public static final String AREA_CAT_STR_CODE = "AreaCat_strCode";
    public static final String AREA_CAT_STR_SEAT_ALLOCATION = "AreaCat_strSeatAllocationOn";
    public static final String CINEMA_STR_ID1 = "Cinema_strID1";
    public static final String PRICE_STR_TICKET_TYPE_CODE = "Price_strTicket_Type_Code";
    public static final String PRICE_STR_TICKET_TYPE_DESCRIPTION = "Price_strTicket_Type_Description";
    public static final String PRICE_STR_TICKET_TYPE_DESCRIPTION2 = "Price_strTicket_Type_Description_2";
    public static final String PRICE_STR_GROUP_CODE = "Price_strGroup_Code";
    public static final String PRICE_INT_TICKET_PRICE = "Price_intTicket_Price";
    public static final String PRICE_STR_CHILD_TICKET = "Price_strChild_Ticket";
    public static final String AREA_CAT_STR_CODE1 = "AreaCat_strCode1";
    public static final String AREA_CAT_INT_SEQ = "AreaCat_intSeq";
    public static final String PRICE_STR_PACKAGE = "Price_strPackage";
    public static final String TTYPE_STR_AVAIL_LOYALTY_ONLY = "TType_strAvailLoyaltyOnly";
    public static final String TTYPE_STR_HOCODE = "TType_strHOCode";
    public static final String PRICE_STR_REDEPTION = "Price_strRedemption";
    public static final String PRICE_STR_COMP = "Price_strComp";
    public static final String TTYPE_STR_SALES_CHANNELS = "TType_strSalesChannels";
    public static final String PRICE_STR_ATM_AVAILABLE = "Price_strATMAvailable";
    public static final String TTYPE_STR_SHOWS_ON_POS = "TType_strShowOnPOS";
    public static final String PRICE_INT_SURCHARGE = "Price_intSurcharge";
    public static final String TTYPE_INT_BARCODE_MAX_REPEATS = "TType_intBarcodeMaxRepeats";
    public static final String TTYPE_STR_AX_REPEATS_CYCLE = "TType_strMaxRepeatsCycle";
    public static final String TTYPE_STR_LONG_DESCRPITION = "TType_strLongDescription";
    public static final String TTYPE_STR_LONG_DESCRPITION_ALT = "TType_strLongDescriptionAlt";
    public static final String TTYPE_STR_MEMBER_CARD = "TType_strMemberCard";
    public static final String TTYPE_STR_AVAIL_RECOGNITION_ONLY = "TType_strAvailRecognitionOnly";
    public static final String HOPK = "HOPK";
    public static final String AREA_CAT_STR_DESC = "AreaCat_strDesc";
    public static final String AREA_CAT_STR_DESC_ALT = "AreaCat_strDescAlt";
    public static final String MMC_STR_NAME = "MMC_strName";
    public static final String PRICE_INT_SEQUENCE = "Price_intSequence";
    public static final String TTYPE_STR_USE_LOYALTY_MEMBERCARD = "TType_strUseLoyaltyMemberCard";
    public static final String PRICE_STR_DYNAMIC_PRICING = "Price_strDynamicPricing";
    public static final String PRICE_INT_SUB_SEQUENCE = "Price_intSubSequence";
    public static final String TTYPE_STR_LIMIT_TO_FILM_PROMOTIONS = "TType_strLimitToFilmPromotions";
    public static final String PRICE_STR_IS_TICKET_PACKAGE = "Price_strIsTicketPackage";
    public static final String LOYALTY_REWARD_TICKET = "LoyaltyRewardTicket";
    public static final String LOYALTY_TICKET_NAME = "LoyaltyTicketName";
    public static final String LOYALTY_TICKET_DESCRIPTION = "LoyaltyTicketDescription";
    public static final String LOYALTY_TICKET_MESSAGE_TEXT = "LoyaltyTicketMessageText";
    public static final String ORDER_TICKETS_BY = "OrderTicketsBy";
    public static final String TICKET_CATEGORY = "TicketCategory";
    public static final String PRICE_STR_LOYALTY_RECOGNITION_ID = "Price_strLoyaltyRecognitionID";
    public static final String PRICE_INT_LOYALTY_DISPLAY_PRICE = "Price_intLoyaltyDisplayPrice";
    public static final String PRICE_INT_LOYALTY_PRICE_CENTS = "Price_intLoyaltyPriceCents";
    public static final String PRICE_INT_LOYALTY_PRICE_COST = "Price_intLoyaltyPointsCost";
    public static final String PRICE_INT_LOYALTY_QTY_AVAILABLE = "Price_intLoyaltyQtyAvailable";
    public static final String PRICE_INT_LOYALTY_RECOGNITION_SEQUENCE = "Price_intLoyaltyRecognitionSequence";
    public static final String RESULT = "Result";
    public static final String xmlFlv="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone= \"yes\"?>\n" +
            "<plasma>\n" +
            "    <version>P_5_12112015</version>\n" +
            "    <videos>\n" +
            "        <video id=\"1\" nombre=\"ICEE.flv\"/>\n" +
            "  <video id=\"2\" nombre=\"HOT DOG.flv\"/>\n" +
            "  <video id=\"3\" nombre=\"NACHOS.flv\"/>\n" +
            "  <video id=\"4\" nombre=\"SALCHIBOTANA.flv\"/>\n" +
            "  <video id=\"5\" nombre=\"PRODUCTOS_1.flv\"/>\n" +
            "    </videos>\n" +
            "</plasma>";


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
                //.setLogLevel(RestAdapter.LogLevel.FULL)
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
                        //mTexto.setText(Html.fromHtml(xmlStr).toString());
                        //mTexto.setText(xmlFlv);
                        XmlParser xmlParser = new XmlParser();
                        try {
                            xmlParser.readXml(xmlFlv);

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
