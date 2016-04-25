package com.company;

import controllers.DatabaseController;
import org.apache.commons.net.ftp.*;

import java.io.*;

/**
 * Created by yapca on 20-4-2016.
 */
public class FTPDownload {
    final FTPClient ftp;
DatabaseController dc = new DatabaseController();
    public FTPDownload(String host_name, String directory, String user_name, String password, int port) {
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

            String[] file_names = ftp.listNames(directory);
            int breaek = 0;
            for(int i = file_names.length - 1; i>=0; i--)
            {
                if(file_names[i].endsWith(".nc"))
                {
                    dc.isFileNewInDatabase(
                            file_names[i].toString().startsWith("/subscriptions/MODISA/XM/carlos.yap/2727/A") && file_names[i].endsWith(".L2_LAC.Netherlands_new.nc")
                                    ? file_names[i].replace("/subscriptions/MODISA/XM/carlos.yap/2727/A", "").replace(".L2_LAC.Netherlands_new.nc", "")
                                    : file_names[i]);
                }
            }
            File downlfile1 = new File("C:\\Users\\yapca\\Downloads\\HDF_MODISA\\new\\A2016111121000.L2_LAC.Netherlands_new.nc");
            OutputStream output_stream = new BufferedOutputStream(new FileOutputStream(downlfile1));
            //ftp.retrieveFile(directory + file, output_stream);







            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

    private void isFileInDatabase(String s) {
        int breaek = 0;
    }
}
