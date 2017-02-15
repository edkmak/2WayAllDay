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
import java.util.*;


public class Main {


    public static List<StopSign> getStopSignData() throws Exception {


        Properties prop = new Properties();
        String propFileName = "../resources/config.properties";
        String[] credentials = new String[3];
        try {
            File file = new File("./resources/config.properties");
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            Enumeration enuKeys = properties.keys();

            int i =0;
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                credentials[i++] = value;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Soda2Consumer consumer = Soda2Consumer.newConsumer("https://data.sfgov.org/", credentials[0], credentials[1], credentials[2]);
        List<StopSign> stopSigns; //= consumer.query("s4tx-xevz", SoqlQuery.SELECT_ALL, StopSign.LIST_TYPE);
        //System.out.println(stopSigns.toString());

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
        return stopSigns;
    }


    private static String avenueString(String streetType){
        int start_ave = 37;
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

    public static void buildIntersections(List<StopSign> stopSigns){
        List<Intersection> intersectionsMap = new ArrayList<Intersection>();
        //build intersections
        for(StopSign stopSign : stopSigns){
            Intersection intersection = new Intersection(stopSign.getStreet(), stopSign.getCrossStreet());
            if(intersectionsMap.contains(intersection)){
                Intersection i = intersectionsMap.get(intersectionsMap.indexOf(intersection));
                i.add(stopSign);
            }else{
                intersectionsMap.add(intersection);
                intersection.add(stopSign);
            }
        }
        Collections.sort(intersectionsMap);
        Iterator<Intersection> i = intersectionsMap.iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }

    public static void createGraph(){
        Map<Intersection, List<Intersection>> map = new HashMap<Intersection, List<Intersection>>();
    }

    public static void main(String[] args) throws Exception {
        List<StopSign> stopSigns = getStopSignData();
        //put all stop sign data into intersections
        buildIntersections(stopSigns);

    }
}
