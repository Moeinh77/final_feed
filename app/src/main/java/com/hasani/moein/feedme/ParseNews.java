package com.hasani.moein.feedme;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Moein on 5/19/2017.
 */

public class ParseNews {

        private final String TAG = "Parse_News";
        private ArrayList<Item> itemsList;

        public ParseNews() {
            itemsList=new ArrayList<>();
        }

        public ArrayList<Item> getItemsList(){
            return itemsList;
        }

        public boolean parse(String xml){
            boolean status=true;
            Item current_item=null;
            String textvalue="";
            boolean inItem=false;

            try{
                XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xmlPullParser=factory.newPullParser();
                xmlPullParser.setInput(new StringReader(xml));
                int eventType=xmlPullParser.getEventType();

                while(eventType!= XmlPullParser.END_DOCUMENT){
                    String tagname = xmlPullParser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if ("item".equalsIgnoreCase(tagname)){
                                inItem=true;
                                current_item=new Item();
                            }
                            break;

                        case XmlPullParser.TEXT:
                            textvalue=xmlPullParser.getText();
                            Log.v(TAG,"textvalue is: "+textvalue);

                            break;

                        case XmlPullParser.END_TAG:
                            if(inItem){
                                if ("item".equalsIgnoreCase(tagname)) {

                                    itemsList.add(current_item);
                                    inItem = false;

                                } else if ("title".equalsIgnoreCase(tagname)) {

                                    current_item.setTitle(textvalue);
                                    Log.v(TAG,"Title set ######");

                                } else if ("description".equalsIgnoreCase(tagname)) {

                                    current_item.setDescription(textvalue);
                                    Log.v(TAG,"description set ######");

                                }else if ("pubdate".equalsIgnoreCase(tagname)){

                                    current_item.setPubdate(textvalue);
                                    Log.v(TAG,"Date set ######");

                                }
                                else if ("enclosure".equalsIgnoreCase(tagname)){

                                    String image_url=xmlPullParser.getAttributeValue(null,"url");
                                    current_item.setImageURL(image_url);//not workin

                                }
                                else if ("link".equalsIgnoreCase(tagname)){

                                    current_item.setURL_ADDRESS(textvalue);

                                }
                            }
                            break;
                    }
                    eventType = xmlPullParser.next();
                }

                for(Item i:itemsList){
                    Log.v(TAG,"########################");
                    Log.v(TAG,i.toString());
                }
            }catch(Exception e){
                status=false;
                e.printStackTrace();
            }
            return status;
        }

    }
