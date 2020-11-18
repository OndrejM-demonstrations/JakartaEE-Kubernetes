/*
 * Copyright (c) 2014, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package firstcup.dukesage.resource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 */
@Path("dukesAge")
public class DukesAgeResource {

    private static final Logger logger =
            Logger.getLogger(DukesAgeResource.class.getName());

    /**
     * Retrieves Duke's age as an object mapped by JSON-B
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DukesAgeResult getText() {
        // Create a new Calendar for Duke's birthday
        Calendar dukesBirthday = new GregorianCalendar(1995, Calendar.MAY, 23);
        // Create a new Calendar for today
        Calendar now = GregorianCalendar.getInstance();

        // Subtract today's year from Duke's birth year, 1995
        int dukesAge = now.get(Calendar.YEAR) - dukesBirthday.get(Calendar.YEAR);
        dukesBirthday.add(Calendar.YEAR, dukesAge);

        // If today's date is before May 23, subtract a year from Duke's age
        if (now.before(dukesBirthday)) {
            dukesAge--;
        }
        // Return Duke's age as JSON
        DukesAgeResult result = new DukesAgeResult();
        result.setAge(dukesAge);
        result.setHost(getLocalHostAddress());
        return result;
    }

    private String getLocalHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            return "Unknown";
        }
    }

    public static class DukesAgeResult {

        private int age;
        private String host;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

    }

    /**
     * Retrieves representation of an instance of DukesAgeResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("difference")
    @Produces(MediaType.APPLICATION_JSON)
    public DukesAgeDifferenceResult getAgeDifference(@QueryParam("date") String date) throws ParseException {
        int ageDifference;

        Calendar theirBirthday = new GregorianCalendar();
        Calendar dukesBirthday = new GregorianCalendar(1995, Calendar.MAY, 23);

        // Set the Calendar object to the passed-in Date
        theirBirthday.setTime(new SimpleDateFormat("yyyy-mm-dd").parse(date));

        // Subtract the user's age from Duke's age
        ageDifference = dukesBirthday.get(Calendar.YEAR)
                - theirBirthday.get(Calendar.YEAR);
        logger.log(Level.INFO, "Raw ageDifference is: {0}", ageDifference);

        // Check to see if Duke's birthday occurs before the user's. If so,
        // subtract one from the age difference
        if (dukesBirthday.before(theirBirthday) && (ageDifference > 0)) {
            ageDifference--;
        }

        // Check to see if Duke's birthday occurs after the user's when the user 
        // is younger. If so, subtract one from the age difference
        if (dukesBirthday.after(theirBirthday) && (ageDifference < 0)) {
            ageDifference++;
        }

        DukesAgeDifferenceResult result = new DukesAgeDifferenceResult();
        result.setAgeDifference(ageDifference);
        result.setHost(getLocalHostAddress());
        return result;
    }
 
    public static class DukesAgeDifferenceResult {
        private int ageDifference;
        private String host;

        public int getAgeDifference() {
            return ageDifference;
        }

        public void setAgeDifference(int ageDifference) {
            this.ageDifference = ageDifference;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
        
    }
}
