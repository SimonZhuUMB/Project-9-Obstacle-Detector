package io.github.project9;

import org.atsign.client.api.AtClient;
import org.atsign.common.AtSign;
import org.atsign.common.KeyBuilders;
import org.atsign.common.Keys.PublicKey;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        //initializing both @signs for receiving and sending
        AtSign atsign = new AtSign("@62sagittarius");
        AtSign pico = new AtSign("@present61");

        AtClient atClient = AtClient.withRemoteSecondary("root.atsign.org:64", atsign);

        // building 2 keys for 2 sensors
        PublicKey inProxPK = new KeyBuilders.PublicKeyBuilder(pico).key("inProximity").build();
        PublicKey inMotionPK = new KeyBuilders.PublicKeyBuilder(pico).key("inMotion").build();

        //previous value, initialized to null
        String _inprox = null;
        String _inmot = null;

        while(true){
            Thread.sleep(500);

            //clearing the cache of whats already there so new data can be received
            atClient.executeCommand("delete:cached:public:" + "inProximity" + pico.toString(), false);
            atClient.executeCommand("delete:cached:public:" + "inMotion" + pico.toString(), false);
            
            //upon reading new value, it will update
            String inProximity = atClient.get(inProxPK).get();
            if (_inprox == null || !inProximity.equals(_inprox)){
                System.out.println("In Proximity: " + inProximity);
                _inprox = inProximity;
            }

            String inMotion = atClient.get(inMotionPK).get();
            if (_inmot == null || !inMotion.equals(_inmot)){
                System.out.println("In Motion: " + inMotion);
                _inmot = inMotion;
            }
            continue;
        }
    }
}
