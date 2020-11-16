package com.example.readfromonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView result;
    private Button fetch;
    private ArrayList<Restaurant> list = new ArrayList<>();
    private ArrayList<Integer> storedRandomInts = new ArrayList<>();
    private int lastNumber = -1;


    public static String Final_Text = "com.example.readfromonline.passed_text";
    private static String Final = "None Selected";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
//        fetch = (Button) findViewById(R.id.fetch);
//        fetch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // getBodyText();
//
//
//            }
//        });
    }

    public void openActivity2(View view) {
        Intent act2intent = new Intent(this, MainActivity2.class);

//        act4intent.putExtra(Price_Text, Price);
//        act4intent.putExtra(Style_Text, Style);
//        act4intent.putExtra(Group_Text, Group);
//        act4intent.putExtra(Food_Text, Food);
//        act4intent.putExtra(Final_Text, Final);

        getBodyText();
       // act2intent.putExtra(Final_Text, Final);
        //startActivity(act2intent);
    }


    public void getBodyText() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //converts our makeshift database into a String url
                String url="https://docs.google.com/document/d/e/2PACX-1vSSKMiZ9pAhMDyzzCu3U1v6SRjxd52SIlEZhy2kBZJLWYvtzPayWftNxZhpb5xVI2aDxa6lUx-VvSze/pub";

                // creates a Document object from the String url online
                // this is the Google Docs we created, the url present above is the published page NOT THE UPDATABLE VERSION
                Document onlineDoc = null;
                try {
                    onlineDoc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    System.out.println("Something went wrong with the online document. Please address immediately!!!");
                }

                //takes the online Google Docs and turns the whole published page into a String Object
                String wholePublishedPage = onlineDoc.text();
                getLines(wholePublishedPage);
            }
        }).start();
    } // end getBodyText method


    public void getLines(String docBody){

        // dynamically sets the body to exactly the information needed
        // parsed from online
        docBody = docBody.substring(300, docBody.length()-38);

        String wholeLine = "";
        int i = 0;
        int arraySize = 0;

        for(i = 0; i < docBody.length(); i++){

            if(docBody.substring(i, i+1).equals(" ")){

                //System.out.print(wholeLine + " <-- Number: " + arraySize);
                wholeLine = wholeLine.replaceAll("\\s", "");
                createArrayList(wholeLine);
                wholeLine = "";
                arraySize++;

                if(i != docBody.length()-1){
                    //wholeLine += "\b";
                    System.out.println();
                }
            }

            wholeLine += docBody.substring(i,i+1);

        }// end for loop
        System.out.println();


        //System.out.println("NAME OF LAST RESTAURANT ---> " + list.get(8).getName());
        processRestaurants("$$", arraySize);
        //result.setText(wholeLine);

    }// end getLines method


    public void createArrayList(String wholeLine){


        String[] tokens = wholeLine.split(",");

        Restaurant place = new Restaurant();
        place.setCost(tokens[0]);
        place.setName(tokens[1]);
        place.setTypeOfFood(tokens[2]);

        list.add(place);

    } // end createArrayList method


    public void processRestaurants(String cost, int arraySize){

        int breakValue = -1;
        int firstIndex = 0, lastIndex = 0;

        /*** TESTING CODE  ********************** CAN DELETE IF NEEDED ***********************************************
         System.out.println("ARRAYSIZE in processFile: ----> " + arraySize);
         System.out.println("IN PROCESSFILE: NAME OF SOMETHING IN ARRAYLIST ---> :" + list.get(4).getName());

         String trial = list.get(13).getCost();
         System.out.println(trial);

         if(list.get(13).getCost().equals(cost)){
         System.out.println("SAME COST");
         }
         else{
         System.out.println("NOT THE SAME COST");
         }
         ************************************************************************************************************/

        for(int i = 0 ; i < arraySize; i++){

            /**** TESTING CODE ****************************************** CAN DELETE IF NEEDED **********************
             System.out.println("TESTING SIZE OF i USING NAMES OF RESTAURANTS--> " + list.get(i).getName());
             System.out.println("Cost passed: <" + cost + ">");
             System.out.println("Cost passed: <" + list.get(i).getCost() + ">");
             **********************************************************************************************************/

            if(cost.equals(list.get(i).getCost()) && breakValue < 0){
                firstIndex = i;
                breakValue++;
                lastIndex = firstIndex;

                while(list.get(lastIndex).getCost().equals(cost)){
                    //System.out.println(list.get(lastIndex).getName());
                    if(lastIndex == arraySize-1){
                        break;
                    }
                    lastIndex++;
                }

                if(!(cost.equals("$$$"))){
                    lastIndex--;
                }
                else{
                    lastIndex++;
                }

                break;
            }
        }// END FOR LOOP

        //System.out.println("Values of " + cost + " in list are from " + firstIndex + " and " + lastIndex);
        int randomRestaurant = (int) (Math.random() *(lastIndex - firstIndex) + firstIndex);

        //randomRestaurant(firstIndex, lastIndex);
        checkRepeats(randomRestaurant, firstIndex, lastIndex);

//        System.out.println("number: " + randomRestaurant + " " + list.get(randomRestaurant).getName());
//
//        result.setText(list.get(randomRestaurant).getName());

    } // end processFile method

    public int randomRestaurant(int firstIndex, int lastIndex){
        return (int) (Math.random() *(lastIndex - firstIndex) + (firstIndex+1));
    }

    public void checkRepeats(int randomRestaurant, int firstIndex, int lastIndex){

        int i = 0;


        System.out.println("WILL CLEAR AT SIZE: " + ((lastIndex - firstIndex) + 1));
        System.out.println("LAST NUMBER STORED: "+ lastNumber);
        System.out.println(firstIndex + " - " + lastIndex);
        if(storedRandomInts.size() == ((lastIndex - firstIndex)+1)){
            System.out.println(" -------------------- CLEARED AT SIZE OF: ---> " + storedRandomInts.size());
            System.out.println();
            storedRandomInts.clear();
        }

        for(i = 0; i < storedRandomInts.size(); i++) {
            if (randomRestaurant == storedRandomInts.get(i) || randomRestaurant == lastNumber) {
                System.out.println("ALREADY STORED:   --> " + storedRandomInts.get(i) + " " + list.get(randomRestaurant).getName() + "    ARRAY SIZE: "  + storedRandomInts.size());
                randomRestaurant = randomRestaurant(firstIndex, lastIndex);
                System.out.println("reroll while loop: --> " + randomRestaurant + " " + list.get(randomRestaurant).getName());
                i = -1;
            }
        }


        System.out.println("out while loop:   --> " + randomRestaurant + " " + list.get(randomRestaurant).getName());

        storedRandomInts.add(randomRestaurant);
        lastNumber = randomRestaurant;
        System.out.println("ADDED THE NUMBER: --> " + randomRestaurant + " " + list.get(randomRestaurant).getName());


        Final_Text = list.get(randomRestaurant).getName();
        result.setText(list.get(randomRestaurant).getName());
        System.out.println(Final_Text);

        Intent newnew = new Intent(this, MainActivity2.class);
        newnew.putExtra("text", Final_Text);
        startActivity(newnew);


    }



}
