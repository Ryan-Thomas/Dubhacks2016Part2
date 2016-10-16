package staticOCR;

import org.json.*;
import java.util.*;

public class TextProcessor{

public static HashSet<String> processText(JSONObject obj, String matchingWords){
        try {
            ArrayList<String> word = new ArrayList<String>();
            word.add(matchingWords);
            HashSet<String> returnedVals = printMoneyVal(obj, getYVal(obj, word));
            return returnedVals;
        }catch(JSONException e){
            System.out.println("Ya fucked up!");
        }

        return new HashSet<String>();
    }

    public static ArrayList<String> getYVal(JSONObject obj, ArrayList<String> matchingWords) throws JSONException{
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
                    for(String s : matchingWords) {
                        if (text.equalsIgnoreCase(s)) {
                            String boundingBox = wordArray.getJSONObject(k).getString("boundingBox");

                            String[] boundingValArray = boundingBox.split(",");
                            String yval = boundingValArray[1];
                            //System.out.println(yval);
                            toBeReturned.add(yval);
                        }
                    }
                }
            }
        }
        return toBeReturned;

    }

    public static HashSet<String> printMoneyVal(JSONObject obj, ArrayList<String> desiredYValues) throws JSONException{
        HashSet<String> returnedVals = new HashSet<String>();
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
                        int lower = Integer.parseInt(yval) - Integer.parseInt(boxHeight) -100;
                        int upper = Integer.parseInt(yval) + 100;

                        if(yValAsInt <= upper && yValAsInt >= lower){
                            String returnedMoneyText = wordArray.getJSONObject(k).getString("text");
                            returnedVals.add(returnedMoneyText);
                            //System.out.println(returnedMoneyText);
                        }
                    }
                }
            }


        }
        return returnedVals;


    }

}
