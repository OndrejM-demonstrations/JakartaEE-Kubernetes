package firstcup.connectors;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.*;

@RequestScoped
@Named("dukesAge")
public class DukesAgeConnector {

    private static final Logger logger = Logger.getLogger(DukesAgeConnector.class.getName());
    
    private String lastHost = "No external call";

    private String dukesAgeServiceRootUrlFromConfig() {
        String url = System.getenv("DUKES_AGE_URL");
        if (url == null) {
            logger.warning("URL of the Duke's age service not defined, using localhost (DUKES_AGE_URL is undefined)");
            url = "http://localhost:8080";
        }
        return url;
    }

    /**
     * Get the value of age
     *
     * @return the value of age
     */
    public int getAge() {
        int age = 0;
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(dukesAgeServiceRootUrlFromConfig() + "/webapi/dukesAge");
            DukesAgeResult response = target.request().get(DukesAgeResult.class);
            age = response.getAge();
            lastHost = response.getHost();
        } catch (IllegalArgumentException | NullPointerException | WebApplicationException ex) {
            logger.severe("processing of HTTP response failed");
        } 
        return age;
    }

    /**
     * Get the value of age difference
     *
     * @return the value of age
     */
    public int getAgeDifference(Date date) {
        int ageDifference = 0;
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(dukesAgeServiceRootUrlFromConfig() + "/webapi/dukesAge/difference");
            Calendar day = Calendar.getInstance();
            day.setTime(date);
            target = target.queryParam("date", day.get(Calendar.YEAR) + "-" + day.get(Calendar.MONTH)+1 + "-" + day.get(Calendar.DAY_OF_MONTH));
            DukesAgeDifferenceResult response = target.request().get(DukesAgeDifferenceResult.class);
            ageDifference = response.getAgeDifference();
            lastHost = response.getHost();
        } catch (IllegalArgumentException | NullPointerException | WebApplicationException ex) {
            logger.severe("processing of HTTP response failed");
        } 
        return ageDifference;
    }

    public String getLastHost() {
        return lastHost;
    }
    
}
