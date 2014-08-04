/*
    Copyright 2012 The Whitehole team

    This file is part of Whitehole.

    Whitehole is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option)
    any later version.

    Whitehole is distributed in the hope that it will be useful, but WITHOUT ANY 
    WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
    FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along 
    with Whitehole. If not, see http://www.gnu.org/licenses/.
*/

package whitehole;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.nio.charset.*;
import javax.swing.JLabel;

public class ObjectDBUpdater extends Thread
{
    public ObjectDBUpdater(JLabel status)
    {
        statusLabel = status;
    }
    
    @Override
    public void run()
    {
        try
        {
            String ts = String.format("&ts=%1$d", ObjectDB.timestamp);
            URL url = new URL(Whitehole.websiteURL + "whitehole/objectdb.php?whitehole" + ts);
            URLConnection conn = url.openConnection();
            DataInputStream dis = new DataInputStream(conn.getInputStream());
            
            int length = conn.getContentLength();
            if (length < 8)
            {
                statusLabel.setText("Failed to update object database: received invalid data.");
                return;
            }
            
            byte[] data = new byte[length];
            for (int i = 0; i < data.length; i++)
                data[i] = dis.readByte();
            
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder dec = charset.newDecoder();
            String strdata = dec.decode(ByteBuffer.wrap(data, 0, 8)).toString();
            
            if (strdata.equals("noupdate"))
            {
                statusLabel.setText("Object database already up-to-date.");
                return;
            }
            else if (data.length < 10)
            {
                statusLabel.setText("Failed to update object database: received invalid data.");
                return;
            }
            
            CRC32 crc = new CRC32();
            crc.update(data, 9, data.length-9);
            long crcref;
            try { crcref = Long.parseLong(strdata, 16); }
            catch (NumberFormatException ex) { crcref = -1; }
            if (crc.getValue() != crcref)
            {
                statusLabel.setText("Failed to update object database: received invalid data.");
                return;
            }
            
            File odbbkp = new File("objectdb.xml.bak");
            File odb = new File("objectdb.xml");

            try
            {
                if (odb.exists())
                {
                    odb.renameTo(odbbkp);
                    odb.delete();
                }
                
                odb.createNewFile();
                FileOutputStream odbstream = new FileOutputStream(odb);
                odbstream.write(data, 9, data.length-9);
                odbstream.flush();
                odbstream.close();
                
                if (odbbkp.exists())
                    odbbkp.delete();
            }
            catch (IOException ex)
            {
                statusLabel.setText("Failed to save new object database.");
                if (odbbkp.exists())
                    odbbkp.renameTo(odb);
                return;
            }
            
            statusLabel.setText("Object database updated.");
            ObjectDB.initialize();
        }
        catch (MalformedURLException ex)
        {
            statusLabel.setText("Failed to connect to update server.");
        }
        catch (IOException ex)
        {
            statusLabel.setText("Failed to save new object database.");
        }
    }
    
    
    private JLabel statusLabel;
}
