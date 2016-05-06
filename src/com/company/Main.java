package com.company;


import controllers.FTP4Download;

public class Main {

    public static void main(String[] args){
        FTP4Download ftp_download = new FTP4Download("samoa.gsfc.nasa.gov", "ftp", "carlos.yap@cgi.com", 21);
        ftp_download.findNewFiles("/subscriptions/MODISA/XM/carlos.yap/2727/", "C:\\Users\\yapca\\Downloads\\HDF_MODISA\\Download\\");

    }
}
