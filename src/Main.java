import com.socrata.api.*;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.model.soql.ConditionalExpression;
import com.socrata.model.soql.OrderByClause;
import com.socrata.model.soql.SortOrder;
import com.sun.jersey.api.client.ClientResponse;
import com.socrata.model.soql.SoqlQuery;
import model.Intersection;
import model.StopSign;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {


    public static void getStopSignData() throws Exception {
        Soda2Consumer consumer = Soda2Consumer.newConsumer("https://data.sfgov.org/",
                "edkmak@gmail.com", "Glasses1!", "IXr4p7R2f06HU5bzgQaM28DS7");

        //ClientResponse response = consumer.query("s4tx-xevz", HttpLowLevel.JSON_TYPE, SoqlQuery.SELECT_ALL);
        //String payload = response.getEntity(String.class);
        //System.out.println(payload);

        List<StopSign> stopSigns; //= consumer.query("s4tx-xevz", SoqlQuery.SELECT_ALL, StopSign.LIST_TYPE);
        //System.out.println(stopSigns.toString());

        //String avenuesWhereClause = avenueString();
        //String streetsWhereClause = streetsString();

        SoqlQuery   getAvenuesQuery = new SoqlQueryBuilder()
                .addSelectPhrase("object_id")
                .addSelectPhrase("street")
                .addSelectPhrase("cross_street")
                .addSelectPhrase("direction")
                .addSelectPhrase("facing_street")
                .setWhereClause("(" + avenueString("cross_street") + ") AND (" +
                        streetsString("street") + ")" + " OR " + "(" +
                        avenueString("street") + ") AND (" +
                        streetsString("cross_street") + ")")
                .addOrderByPhrase(new OrderByClause(SortOrder.Descending, "object_id"))
                .build();
        stopSigns = consumer.query("s4tx-xevz", getAvenuesQuery, StopSign.LIST_TYPE);
        for(StopSign sign : stopSigns){
            System.out.println(sign);
        }
        //insert into intersections.
        List<Intersection> intersections = new ArrayList<>();
        for(StopSign sign : stopSigns){
            //Intersection intersection = new Intersection();
        }
        //System.out.println(stopSigns.toString());
        //formulate graph of Intersections.
    }


    private static String avenueString(String streetType){
        int start_ave = 36;
        int end_ave = 48;
        StringBuffer sb = new StringBuffer();
        for(int i = start_ave; i <= end_ave; i++) {
            sb.append(streetType);
            sb.append("=");
            if(i % 10 == 1) {
                sb.append("'" + i + "ST AVE'");
            }else if(i % 10 == 2){
                sb.append("'" + i + "ND AVE'");
            }else if(i % 10 == 3) {
                sb.append("'" + i + "RD AVE'");
            }else {
                sb.append("'" + i + "TH AVE'");
            }
            if(i != end_ave){
                sb.append(" OR ");
            }
        }
        return sb.toString();
    }

    private static String streetsString(String streetType){
        StringBuilder sb = new StringBuilder();
        Scanner scan = null;
        try{
            File f = new File("src/model/avenues.txt");
            scan = new Scanner(f);
        }catch(FileNotFoundException s){
            System.out.println("File does Not Exist Please Try Again: ");
        }
        while(scan.hasNext())
        {
            sb.append(streetType);
            sb.append("=");
            sb.append("'" + scan.nextLine() + "'") ;
            if(scan.hasNext()) {
                sb.append(" OR ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        /*String avenuesWhereClause = avenueString();
        String streetsWhereClause = streetsString();
        System.out.println(avenuesWhereClause + " OR " + streetsWhereClause);
        System.out.println(avenuesWhereClause);*/
        getStopSignData();
        //System.out.println(buildWhere());
        //System.out.println(streetsString());
    }
}
