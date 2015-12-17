package jesustoskno.practicasoap.Soapeando;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by jtoscano on 14/12/2015.
 */
public class XmlParser {
    protected String RESULT = "Result";
    protected String OK = "OK";
    protected String DATASET_TAG = "DatasetXML";
    protected Boolean DATASET_STATUS = false;
    protected Boolean MODO=false;

    public void readXml(String dataset) throws XmlPullParserException, IOException {
        leerRespuesta(dataset);
    }

    private void leerRespuesta(String dataset) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(dataset));
        int eventType = parser.getEventType();
        String tagname = "";
        Boolean dataSetStatus = false;
        Boolean okStatus = false;
        if (MODO.equals(false)) {
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && parser.getName().equals(RESULT)) {
                    tagname = parser.getName();
                }
                if (eventType == XmlPullParser.TEXT && tagname.equals(RESULT)) {
                    if (parser.getText().equals(OK)) {
                        okStatus = true;
                    }
                }
                if (okStatus.equals(true)) {
                    if (eventType == XmlPullParser.START_TAG && parser.getName().equals(DATASET_TAG)) {
                        dataSetStatus = true;
                    }
                    if (dataSetStatus.equals(true) && eventType == XmlPullParser.TEXT) {
                        String dataSet= parser.getText();
                        DATASET_STATUS = true;
                        MODO = true;
                        readXml(dataSet);
                    }
                }
                eventType = parser.next();
            }
        }
        if(MODO.equals(true)){
            MODO=false;
            String tag = "";
            String endTag = "";
            String prefix = "";
            String nameSpace = "";
            eventType=parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        prefix = parser.getPrefix();
                        nameSpace = parser.getNamespace();
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        endTag = parser.getName();
                        break;
                }
                eventType = parser.next();
            }
        }
    }
}