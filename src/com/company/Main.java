package com.company;


public class Main {

    public static void main(String[] args){
        FTPDownload ftp_download = new FTPDownload("samoa.gsfc.nasa.gov", "/subscriptions/MODISA/XM/carlos.yap/2727/", "ftp", "carlos.yap@cgi.com", 21);
        ReadNetCDF rhdf = new ReadNetCDF("C:\\Users\\yapca\\Downloads\\" +
                "HDF_MODISA\\A2016098124000.L2_LAC.Netherlands_new.nc");
        try {
            rhdf.ParseFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
