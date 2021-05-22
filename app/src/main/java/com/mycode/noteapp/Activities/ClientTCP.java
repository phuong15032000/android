package com.mycode.noteapp.Activities;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientTCP
{

    public static Socket soc;
    public static DataInputStream din = null;
    public static DataOutputStream dout = null;
    public static int PortNo,dataPort;
    public static String Host;
    public String connect(String args[]) throws Exception
    {
        String msg="Failure";
        //System.out.println(args[0]);
        if (args.length == 2){
            //System.out.println(args[1]);
            Host=args[0];
            PortNo=Integer.parseInt(args[1]);
        }
        else {
            PortNo=21;
        }
        soc=new Socket(Host,PortNo);
        dataPort=PortNo-1;

        System.out.println(soc.getPort());
        din=new DataInputStream(soc.getInputStream());
        dout=new DataOutputStream(soc.getOutputStream());
        return din.readUTF();
    }
    public static void disconnect() throws IOException {
        soc.close();
        disconnect();
    }
}



