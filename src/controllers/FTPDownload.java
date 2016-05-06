package controllers;

import controllers.DatabaseController;
import org.apache.commons.net.ftp.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.io.*;

/**
 * Created by yapca on 20-4-2016.
 */
public class FTPDownload {
    final FTPClient ftp;
    DatabaseController dc;
    ReadNetCDF netCDF_Reader;
    public FTPDownload(String host_name, String user_name, String password, int port) {
        dc = new DatabaseController();
        netCDF_Reader = new ReadNetCDF();
        this.ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host_name, port);
            System.out.println("Connected to " + host_name + ".");
            System.out.print(ftp.getReplyString());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();


            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }


            ftp.login(user_name, password);
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }
    public void findNewFiles(String directory, String download_folder)
    {
        String[] file_names = new String[0];
        try {
            file_names = ftp.listNames(directory);

        int breaek = 0;
        for(int i = file_names.length - 1; i>=0; i--)
        {
            if(file_names[i].endsWith(".L2_LAC.Netherlands_new.nc"))
            {
                String date = parseSatelliteDate(file_names[i].toString().replace("/subscriptions/MODISA/XM/carlos.yap/2727/A", "").replace(".L2_LAC.Netherlands_new.nc", ""));

                if(dc.isMeasurementNewInDatabase(
                        date,
                        file_names[i].toString().replace("/subscriptions/", "").substring(0, 6)))
                {
                    String create_file_folder = download_folder + file_names[i].toString().replace("/subscriptions/MODISA/XM/carlos.yap/2727/", "");
                    File create_file = new File(create_file_folder);
                    OutputStream output_stream = new BufferedOutputStream(new FileOutputStream(create_file));
                    if(ftp.retrieveFile(file_names[i], output_stream))
                        netCDF_Reader.parseFile(create_file_folder);
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

    private String parseSatelliteDate(String parse) {
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
        String date = DateTimeFormat.forPattern("yyyy-M-d HH:m:s").print(date_time);
        return date;
    }
}
