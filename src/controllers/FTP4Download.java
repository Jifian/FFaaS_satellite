package controllers;


import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPReply;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yapca on 20-4-2016.
 */
public class FTP4Download {
    private final FTPClient ftp;
    private DatabaseController dc;
    private ReadNetCDF netCDF_Reader;
    private String host_name;
    private String user_name;
    private String password;
    private int port;
    public FTP4Download(String host_name, String user_name, String password, int port) {
        dc = new DatabaseController();
        netCDF_Reader = new ReadNetCDF();
        this.ftp = new FTPClient();
        this.host_name = host_name;
        this.user_name = user_name;
        this.password = password;
        this.port = port;
        openConnection();
    }

    public void openConnection()
    {
        try {
            ftp.connect(host_name, port);
            System.out.println("Connected to " + host_name + ".");

            // After connection attempt, you should check the reply code to verify
            // success.

            ftp.login(user_name, password);
            ftp.setPassive(true);
            ftp.setType(FTPClient.TYPE_AUTO);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } finally {
        }
    }
    public void findNewFiles(String directory, String download_folder)
    {
        String[] file_names = new String[0];
        try {
            if(!ftp.isConnected())
                openConnection();
            ftp.changeDirectory(directory);
            file_names = ftp.listNames();
        for(int i = file_names.length - 1; i>=0; i--)
        {
            if(file_names[i].endsWith(".L2_LAC.Netherlands_new.nc"))
            {
                DateTime date_time = parseSatelliteDate(file_names[i].toString().replace("A20", "20").replace(".L2_LAC.Netherlands_new.nc", ""));
                String datasource_name = directory.toString().replace("/subscriptions/", "").substring(0, 6);
                if(dc.isMeasurementNewInDatabase(
                        DateTimeFormat.forPattern("yyyy-M-d HH:m:s").print(date_time),
                        datasource_name))
                {
                    String create_file_folder = download_folder + file_names[i];
                    File create_file = new File(create_file_folder);

                    if(!ftp.isConnected())
                        openConnection();
                    ftp.download(directory + file_names[i], create_file);
                    SaveFile sf = new SaveFile();
                    sf.save(netCDF_Reader.parseFile(create_file_folder), datasource_name,
                            file_names[i].toString().substring(16, 17), "Chlorophyll-a", "μg/l",
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_time.toString("yyyy-MM-dd HH:mm:ss")));
                }
            }
        }

        //
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DateTime parseSatelliteDate(String parse) {
        DateTime date_time = new DateTime(0);
        date_time = date_time.withZone(DateTimeZone.UTC);
        // year from file_name minus the year date_time was initialized with
        date_time = date_time.plusYears(Integer.parseInt(parse.substring(0,4)) - date_time.getYear());

        // set current day minus the 1 day it adds at initialization.
        date_time = date_time.plusDays(Integer.parseInt(parse.substring(4,7)) - 1);

        //set Time
        date_time = date_time.plusHours(Integer.parseInt(parse.substring(7,9)))
                .plusMinutes(Integer.parseInt(parse.substring(9,11)))
                .plusSeconds(Integer.parseInt(parse.substring(11,13)));
        //String date =
        return date_time;
    }
}
