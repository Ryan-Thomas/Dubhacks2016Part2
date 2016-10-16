package staticOCR;

import org.json.*;
import java.util.*;

public class TextProcessor{

public static void processText(JSONObject obj, String matchingWord){
        try {
            printMoneyVal(obj, getYVal(obj, matchingWord));
        }catch(JSONException e){
            System.out.println("Ya fucked up!");
        }
    }

    public static ArrayList<String> getYVal(JSONObject obj, String matchingWord) throws JSONException{
        //regions --> boundingBox, lines --> words --> text
        ArrayList<String> toBeReturned = new ArrayList<String>();

        //get the array of regions
        JSONArray regionsArr = obj.getJSONArray("regions");
        //get the array of lines

        //for every element in the array of regions
        for(int i = 0; i < regionsArr.length(); i++){
            JSONObject childOne = regionsArr.getJSONObject(i);
            JSONArray lineArray = childOne.getJSONArray("lines");
            //System.out.println(lineArray);
            for(int j = 0; j < lineArray.length(); j++){
                JSONObject childTwo = lineArray.getJSONObject(j);
                JSONArray wordArray = childTwo.getJSONArray("words");
                //System.out.println("    " + wordArray);
                for(int k = 0; k < wordArray.length(); k++){
                    String text = wordArray.getJSONObject(k).getString("text");
                    //System.out.println("        " + text);
                    if(text.equalsIgnoreCase(matchingWord)){
                        String boundingBox = wordArray.getJSONObject(k).getString("boundingBox");

                        String[] boundingValArray = boundingBox.split(",");
                        String yval = boundingValArray[1];
                        System.out.println(yval);
                        toBeReturned.add(yval);
                    }
                }
            }
        }
        return toBeReturned;

    }

    public static void printMoneyVal(JSONObject obj, ArrayList<String> desiredYValues) throws JSONException{
        for(int x = 0; x < desiredYValues.size(); x++){
            String desiredYVal = desiredYValues.get(x);
            int yValAsInt = Integer.parseInt(desiredYVal);

            JSONArray regionsArr = obj.getJSONArray("regions");

            for(int i = 0; i < regionsArr.length(); i++){
                JSONObject childOne = regionsArr.getJSONObject(i);
                JSONArray lineArray = childOne.getJSONArray("lines");
                for(int j = 0; j < lineArray.length(); j++){
                    JSONObject childTwo = lineArray.getJSONObject(j);
                    JSONArray wordArray = childTwo.getJSONArray("words");
                    for(int k = 0; k < wordArray.length(); k++){
                        String boundingBox = wordArray.getJSONObject(k).getString("boundingBox");

                        String[] boundingValArray = boundingBox.split(",");
                        String yval = boundingValArray[1];

                        String boxHeight = boundingValArray[3];
                        int lower = Integer.parseInt(yval) - Integer.parseInt(boxHeight);
                        int upper = Integer.parseInt(yval);

                        if(yValAsInt <= upper && yValAsInt >= lower){
                            String returnedMoneyText = wordArray.getJSONObject(k).getString("text");
                            System.out.println(returnedMoneyText);
                        }
                    }
                }
            }

        }



    }

}
