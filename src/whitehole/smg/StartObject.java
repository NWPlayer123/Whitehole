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

package whitehole.smg;

import whitehole.PropertyPanel;
import whitehole.vectors.Vector3;

public class StartObject extends LevelObject
{
    public StartObject(ZoneArchive zone, String filepath, Bcsv.Entry entry)
    {
        this.zone = zone;
        String[] stuff = filepath.split("/");
        directory = stuff[0];
        layer = stuff[1].toLowerCase();
        file = stuff[2];
        
        data = entry;
        
        name = (String)data.get("name");
        if (!name.equals("Mario")) System.out.println("NON-MARIO START OBJECT -- WHAT THE HELL ("+name+")");
        loadDBInfo();
        renderer = null;
        
        uniqueID = -1;
        
        position = new Vector3((float)data.get("pos_x"), (float)data.get("pos_y"), (float)data.get("pos_z"));
        rotation = new Vector3((float)data.get("dir_x"), (float)data.get("dir_y"), (float)data.get("dir_z"));
        scale = new Vector3((float)data.get("scale_x"), (float)data.get("scale_y"), (float)data.get("scale_z"));
    }
    
    public StartObject(ZoneArchive zone, String filepath, int game, Vector3 pos)
    {
        this.zone = zone;
        String[] stuff = filepath.split("/");
        directory = stuff[0];
        layer = stuff[1].toLowerCase();
        file = stuff[2];
        
        data = new Bcsv.Entry();
        
        name = "Mario";
        loadDBInfo();
        renderer = null;
        
        uniqueID = -1;
        
        position = pos;
        rotation = new Vector3(0f, 0f, 0f);
        scale = new Vector3(1f, 1f, 1f);
        
        data.put("name", name);
        data.put("pos_x", position.x); data.put("pos_y", position.y); data.put("pos_z", position.z);
        data.put("dir_x", rotation.x); data.put("dir_y", rotation.y); data.put("dir_z", rotation.z);
        data.put("scale_x", scale.x); data.put("scale_y", scale.y); data.put("scale_z", scale.z);
        
        data.put("Obj_arg0", -1);
        data.put("MarioNo", 0);
        data.put("Camera_id", -1);
    }
    
    @Override
    public void save()
    {
        data.put("name", name);
        data.put("pos_x", position.x); data.put("pos_y", position.y); data.put("pos_z", position.z);
        data.put("dir_x", rotation.x); data.put("dir_y", rotation.y); data.put("dir_z", rotation.z);
        data.put("scale_x", scale.x); data.put("scale_y", scale.y); data.put("scale_z", scale.z);
    }

    
    @Override
    public void getProperties(PropertyPanel panel)
    {
        panel.addCategory("obj_position", "Position");
        panel.addField("pos_x", "X position", "float", null, position.x);
        panel.addField("pos_y", "Y position", "float", null, position.y);
        panel.addField("pos_z", "Z position", "float", null, position.z);
        panel.addField("dir_x", "X rotation", "float", null, rotation.x);
        panel.addField("dir_y", "Y rotation", "float", null, rotation.y);
        panel.addField("dir_z", "Z rotation", "float", null, rotation.z);
        panel.addField("scale_x", "X scale", "float", null, scale.x);
        panel.addField("scale_y", "Y scale", "float", null, scale.y);
        panel.addField("scale_z", "Z scale", "float", null, scale.z);

        // TODO nice object args (ObjectDB integration)

        panel.addCategory("obj_args", "Object arguments");
        panel.addField("Obj_arg0", "Obj_arg0", "int", null, data.get("Obj_arg0"));

        panel.addCategory("obj_objinfo", "Object settings");
        panel.addField("MarioNo", "Mario ID", "int", null, data.get("MarioNo"));
        panel.addField("Camera_id", "Camera ID", "int", null, data.get("Camera_id"));
    }
    
    @Override
    public String toString()
    {
        String l = layer.equals("common") ? "Common" : "Layer"+layer.substring(5).toUpperCase();
        return String.format("Starting point %1$d [%2$s]", (int)data.get("MarioNo"), l);
    }
}
