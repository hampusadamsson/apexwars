package sockets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {
    Client client;

    @Disabled
    @BeforeEach
    public void setup() throws IOException {
        //new Thread(() -> Server.main(null));

        client = new Client();

        var working = false;
        while (!working) {
            try {
                client.startConnection("127.0.0.1", 6666);
                working = true;
            }catch (Exception ignored){
            }
        }
    }

    @Disabled
    @AfterEach
    public void tearDown() throws IOException {
        client.stopConnection();
    }

    @Disabled
    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() throws IOException {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");

        var c = new Client();
        c.startConnection("127.0.0.1", 29432);
        c.sendMessage("Who am I?");
        c.sendMessage(".");

        String resp4 = client.sendMessage(".");

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("bye", resp4);
    }

}