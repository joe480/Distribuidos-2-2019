import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.*;
import java.util.List;
import java.util.LinkedList;

public class SimpleChat extends ReceiverAdapter {
    JChannel channel;
    //String user_name=System.getProperty("user.name", "n/a");
    String user_name="Joel";
    final List<String> state=new LinkedList<>();

    public void viewAccepted(View new_view) {
        System.out.println("** vista: " + new_view);
    }

    public void receive(Message msg) {
        /* Si el usuario ingresa "fact-n" el chat le devuelve el factorial de n */
        String line=msg.getObject();
        
        String[] mensaje=line.split(" "); 
        String [] factoS=mensaje[1].split("-");
        
        if("fact".equals(factoS[0])){
        int factorial=Integer.parseInt(factoS[1]);
        System.out.println(calcularFactorial(factorial));
        }
        else 
            System.out.println(line);
        synchronized(state) {
            state.add(line);
        }
    }
    
    String calcularFactorial(int factorial){
        int resultado=1;
        for (int i = 1; i <=factorial; i++) {
            resultado=resultado*i;
        }
        return "El Factorial de "+factorial+" es: "+resultado;
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
        List<String> list=(List<String>)Util.objectFromStream(new DataInputStream(input));
        synchronized(state) {
            state.clear();
            state.addAll(list);
        }
        System.out.println("estado recibido (" + list.size() + " mensajes en la historia del chat ):");
        for(String str: list) {
            System.out.println(str);
        }
    }


    private void start() throws Exception {
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
        eventLoop();
        channel.close();
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                line="[" + user_name + "] " + line;
                Message msg=new Message(null,line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new SimpleChat().start();
    }
}
