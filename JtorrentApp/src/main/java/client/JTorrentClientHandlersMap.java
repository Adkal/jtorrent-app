package client;

import com.turn.ttorrent.client.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class JTorrentClientHandlersMap {

    private Map<Integer, Client> clients = null;
    private static AtomicInteger id;

    public JTorrentClientHandlersMap() {
        if(Objects.isNull(clients)) {
            clients = new HashMap<>();
            id = new AtomicInteger(0);
        }
    }

    public Map<Integer, Client> getClients() {
        return clients;
    }

    public Client getClient(Integer id){
        return clients.get(id);
    }

    public Integer registerNewClient(Client client) {
        int thisId = id.getAndIncrement();
        this.clients.put(thisId, client);
        return thisId;
    }

}
