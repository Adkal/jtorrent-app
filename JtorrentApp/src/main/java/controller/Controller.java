package controller;

import client.JTorrentClient;
import client.JTorrentClientHandlersMap;
import com.turn.ttorrent.client.Client;
import exception.JTorrentClientException;
import generator.Torrent;
import model.StartDownloadResponse;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import serialization.SerializationUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Path("/controller")
public class Controller {

    private static JTorrentClientHandlersMap jTorrentClientHandlersMap;

    static{
        jTorrentClientHandlersMap = new JTorrentClientHandlersMap();
    }

    @POST
    @Path("/download")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON})
    public String startDownloadDefault(@Context HttpHeaders headers, @FormDataParam("torrentFile") InputStream uploadedInputStream) throws IOException {

        String fileName = headers.getHeaderString("X-File-Name");

            File cacheFolder = new File(System.getProperty("user.home") + "\\JTorrent\\cache");
        File targetFolder = new File(System.getProperty("user.home") + "\\JTorrent]\\targetFolder");
        cacheFolder.mkdirs();
        targetFolder.mkdirs();

        String fullPath = cacheFolder + "\\" + fileName;

        FileUtils.copyInputStreamToFile(uploadedInputStream, new File(fullPath));

        try{

            Client client = JTorrentClient.createDownloadClient(fullPath, targetFolder.getPath());
          //  client = JTorrentClient.startDownloadWithDefault(client);
          //  JTorrentClient.addObserver(client);

            //client.waitForCompletion();

            return registerNewClientAndPrepareResponse(client);


        } catch (JTorrentClientException e) {
            e.printStackTrace();
        }

        return "There is no data to download";

    }

    @GET
    @Path("/start")
    @Produces({MediaType.TEXT_HTML})
    public String startDownload(@QueryParam("clientId") String clientId){
        try {
            getClientById(clientId).download();
            return "downloading successfully started";
        }catch (NumberFormatException e){
            return "start failed";
        }

    }

    @GET
    @Path("/completion")
    @Produces({MediaType.TEXT_PLAIN})
    public float getCompletion(@QueryParam("clientId") String clientId){
        return getClientById(clientId).getTorrent().getCompletion();

    }

    @GET
    @Path("/stop")
    @Produces({MediaType.TEXT_HTML})
    public String stopDownload(@QueryParam("clientId") String clientId){
        try {
            getClientById(clientId).stop();
            return "downloading successfully stopped";
        }catch (NumberFormatException e){
            return "stop failed";
        }

    }

    @GET
    @Path("/home")
    @Produces({MediaType.TEXT_HTML})
    public InputStream getHomePage() throws Exception {
        return getClass().getResourceAsStream("/home.html");
    }

    @POST
    @Path("/generateTorrent")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.TEXT_PLAIN})
    public String generate(@Context HttpHeaders headers, @FormDataParam("torrentFile") InputStream uploadedInputStream){
        try {
            String fileName = headers.getHeaderString("X-File-Name");

            File cacheFolder = new File(System.getProperty("user.home") + "\\JTorrent\\cache");

            File targetFolder = new File(System.getProperty("user.home") + "\\JTorrent]\\targetFolder");
            cacheFolder.mkdirs();
            targetFolder.mkdirs();

            String fullPath = cacheFolder + "\\" + fileName;

            FileUtils.copyInputStreamToFile(uploadedInputStream, new File(fullPath));

            Torrent.createTorrent(new File(targetFolder + "\\generatedTorrent\\" +
            fileName), new File(fullPath), "http://project.uksw.pl");

            return "Successfully generated torrent " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failure generated torrent ";
    }

    private static Client getClientById(String clientId){
        return jTorrentClientHandlersMap.getClient(Integer.valueOf(clientId));
    }

    private static String registerNewClientAndPrepareResponse(Client client) throws IOException {
        return SerializationUtils.serializeToJson(
                new StartDownloadResponse(
                        jTorrentClientHandlersMap.registerNewClient(client),
                        client.getTorrent().getName(), 0, client.getPeers().size(),
                        0, client.getTorrent().getSize()
                )
        );
    }
}
