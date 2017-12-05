package controller;

import client.JTorrentClient;
import com.turn.ttorrent.client.Client;
import exception.JTorrentClientException;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;

@Path("/controller")
public class Controller {

    private static final String TARGET_PATH = "D:\\Jtorrent\\targetFolder";
    private static final String TMP_PATH = "D:\\Jtorrent\\source\\tmp2.torrent";

    @POST
    @Path("/download")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    public void startDownloadDefault(@FormDataParam("torrentFile") InputStream uploadedInputStream) throws IOException {


        FileUtils.copyInputStreamToFile(uploadedInputStream, new File(TMP_PATH));

        try{

            Client client = JTorrentClient.createDownloadClient(TMP_PATH, TARGET_PATH);
            client = JTorrentClient.startDownloadWithDefault(client);
            JTorrentClient.addObserver(client);
            client.waitForCompletion();

        } catch (JTorrentClientException e) {
            e.printStackTrace();
        }

    }

    @GET
    @Path("/home")
    @Produces({MediaType.TEXT_HTML})
    public InputStream getHomePage() throws Exception {
        return getClass().getResourceAsStream("/home.html");
    }

}
