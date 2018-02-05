package controller;

import client.JTorrentClient;
import client.JTorrentClientHandlersMap;
import com.google.gson.Gson;
import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
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
import java.util.HashMap;
import java.util.Map;

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
        File targetFolder = new File(System.getProperty("user.home") + "\\JTorrent\\targetFolder");
        cacheFolder.mkdirs();
        targetFolder.mkdirs();

        String fullPath = cacheFolder + "\\" + fileName;

        FileUtils.copyInputStreamToFile(uploadedInputStream, new File(fullPath));

        try{

            Client client = JTorrentClient.createDownloadClient(fullPath, targetFolder.getPath());
            client = JTorrentClient.startDownloadWithDefault(client);
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
        String message;
        try {
            Client client = getClientById(clientId);
            if(client.getState() == Client.ClientState.SHARING) {
                client.download();
                message = "STOP";
            }else{
                message = "START";
            }
            return message;
        }catch (NumberFormatException e){
            return "Failed";
        }

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
    @Path("/completion")
    @Produces({MediaType.APPLICATION_JSON})
    public String getCompletion(@QueryParam("clientId") String clientId){
        SharedTorrent torrent = getClientById(clientId).getTorrent();
        float progress = torrent.getCompletion();
        long downloaded = torrent.getDownloaded()/1024;
        long uploaded = torrent.getUploaded()/1024;
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("id", clientId);
        responseMap.put("Progress", String.format("%.2f", progress) + "%");
        responseMap.put("Downloaded", String.valueOf(downloaded) + " MB");
        responseMap.put("Uploaded", String.valueOf(uploaded) + " MB");
        return new Gson().toJson(responseMap);

    }

    @GET
    @Path("/home")
    @Produces({MediaType.TEXT_HTML})
    public InputStream getHomePage() throws Exception {
        return getClass().getResourceAsStream("/home.html");
    }

    @GET
    @Path("/bg.jpg")
    @Produces({MediaType.TEXT_HTML})
    public InputStream getBG() throws Exception {
        return getClass().getResourceAsStream("/bg.jpg");
    }

    @POST
    @Path("/generateTorrent")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.TEXT_PLAIN})
    public String generate(@Context HttpHeaders headers, @FormDataParam("torrentFile") InputStream uploadedInputStream){
        try {
            String fileName = headers.getHeaderString("X-File-Name");

            File generatePath = new File(System.getProperty("user.home") + "\\JTorrent\\toGenerate");

            File targetFolder = new File(System.getProperty("user.home") + "\\JTorrent\\targetFolder\\generatedTorrent");
            generatePath.mkdirs();
            targetFolder.mkdirs();

            String fullPath = generatePath + "\\" + fileName;

            FileUtils.copyInputStreamToFile(uploadedInputStream, new File(fullPath));

            Torrent.createTorrent(new File(targetFolder + "\\" +
            fileName + ".torrent"), new File(fullPath), "http://project.uksw.pl");

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
                        client.getTorrent().getName(), client.getTorrent().getDownloaded(), client.getPeers().size(),
                        client.getTorrent().getUploaded(), client.getTorrent().getSize()/1048576
                )
        );
    }
}
