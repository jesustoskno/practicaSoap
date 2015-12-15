package jesustoskno.practicasoap.Soapeando;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtoscano on 14/12/2015.
 */
public class XmlParser {

    public List<Video> readXml(String dataset) throws XmlPullParserException, IOException {
        ArrayList<Video> videosItems = new ArrayList<Video>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(dataset));
        int eventType = parser.getEventType();
        String text = "";
        String id = "";
        String nombre = "";

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagname = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equals("video")) {
                        Video video = new Video();
                        id = parser.getAttributeValue(0);
                        video.setId(id);
                        nombre = parser.getAttributeValue(1);
                        video.setNombre(nombre);
                        videosItems.add(video);
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(tagname!=null&&tagname.equals("version"))
                {
                    Log.d("Version",parser.getText());
                }
                    break;
                case XmlPullParser.END_TAG:
                    break;

            }
            eventType = parser.next();
        }
        for (int i = 0; i < videosItems.size(); i++) {
            Log.d("Video " + (i+1), "Id: " + videosItems.get(i).getId() + " Nombre: " + videosItems.get(i).getNombre());
        }
        return videosItems;
    }

    public class Video {
        private String id;
        private String nombre;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}