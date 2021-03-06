import org.json.*;
import java.util.*;

public class TextProcessor{
public void processText(JSONObject obj, String matchingWord){
        printMoneyVal(obj, getYVal(obj, matchingWord));
    }

    public ArrayList<String> getYVal(JSONObject obj, String matchingWord){
        //regions --> boundingBox, lines --> words --> text
        ArrayList<String> toBeReturned = new ArrayList<String>();

        //get the array of regions
        JSONArray regionsArr = obj.getJSONArray("regions");
        //get the array of lines

        //for every element in the array of regions
        for(int i = 0; i < regionsArr.size(); i++){
            JSONObject childOne = regionsArr.getJSONObject(i);
            JSONArray lineArray = childOne.getJSONArray("lines");
            for(int j = 0; j < lineArray.size(); j++){
                JSONObject childTwo = lineArray.getJSONObject(i);
                JSONArray wordArray = childTwo.getJSONArray("words");
                for(int k = 0; k < wordArray.size(); k++){
                    String text = wordArray.getJSONObject(k).getString("text");
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

    public void printMoneyVal(JSONObject obj, ArrayList<String> desiredYValues){
        for(int x = 0; x < desiredYValues.size(); x++){
            String desiredYVal = desiredYValues.get(x);
            int yValAsInt = Integer.parseInt(desiredYVal);
            int upperBound = yValAsInt + 10;
            int lowerBound = yValAsInt - 10;

            JSONArray regionsArr = obj.getJSONArray("regions");

            for(int i = 0; i < regionsArr.size(); i++){
                JSONObject childOne = regionsArr.getJSONObject(i);
                JSONArray lineArray = childOne.getJSONArray("lines");
                for(int j = 0; j < lineArray.size(); j++){
                    JSONObject childTwo = lineArray.getJSONObject(i);
                    JSONArray wordArray = childTwo.getJSONArray("words");
                    for(int k = 0; k < wordArray.size(); k++){
                        String boundingBox = wordArray.getJSONObject(k).getString("boundingBox");

                        String[] boundingValArray = boundingBox.split(",");
                        String yval = boundingValArray[1];
                        if(Integer.parseInt(yval) <= upperBound || Integer.parseInt(yval) >= lowerBound){
                            String returnedMoneyText = wordArray.getJSONObject(k).getString("text");
                            System.out.println(returnedMoneyText);
                        }
                    }
                }
            }

        }



    }

}
