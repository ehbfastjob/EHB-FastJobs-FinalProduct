package be.ehb.ipg13.fastjobs;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Nadir on 19/04/2015.
 */
public class XMLParser {
    public static ArrayList<ModelTest> deParser(String xx) {
        if (xx == null){
            return null;
        }
        boolean inDataItemTag = false;
        int test = 0;
        String CurrentTagName = "";
        //--------START-----------------------------------------------------------------------------------------
        ModelTest modelTest = new ModelTest();
        ArrayList<ModelTest> lijstje = new ArrayList<ModelTest>();
        //--------STOP------------------------------------------------------------------------------------------
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xx));

            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        CurrentTagName = xmlPullParser.getName();
                        if (CurrentTagName.equals("ZoekResultaat")) {
                            inDataItemTag = true;
                            //--------START--------------------------------------------------------------------------
                            modelTest = new ModelTest();
                            lijstje.add(modelTest);
                            //--------STOP---------------------------------------------------------------------------
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("ZoekResultaat")) {
                            inDataItemTag = false;
                            test = 0;
                        }
                        CurrentTagName = "";
                        break;
                    case XmlPullParser.TEXT:
                        if (CurrentTagName.equals("Property")) {
                            //--------START---------------------------------------------------------------------------
                            if (inDataItemTag && modelTest != null) {
                                if (test == 0) {
                                    modelTest.setId(xmlPullParser.getText());
                                    test++;
                                } else if(test == 1 ){
                                    modelTest.setTitel(xmlPullParser.getText());
                                    test++;
                                }else if(test == 2 ){
                                    modelTest.setGemeente(xmlPullParser.getText());
                                    test = 0;
                                }
                            }
                            //--------STOP----------------------------------------------------------------------------
                        }
                        CurrentTagName = "";
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            System.out.println("ER IS EEN FOUT GEBEURD BIJ HET PARSEN!!");
        }
        return lijstje;
    }
}
