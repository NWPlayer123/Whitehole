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

public class GeneralObject extends LevelObject
{
    public GeneralObject(ZoneArchive zone, String filepath, Bcsv.Entry entry)
    {
        this.zone = zone;
        String[] stuff = filepath.split("/");
        directory = stuff[0];
        layer = stuff[1].toLowerCase();
        file = stuff[2];
        
        data = entry;
        
        name = (String)data.get("name");
        loadDBInfo();
        renderer = null;
        
        uniqueID = -1;
        
        position = new Vector3((float)data.get("pos_x"), (float)data.get("pos_y"), (float)data.get("pos_z"));
        rotation = new Vector3((float)data.get("dir_x"), (float)data.get("dir_y"), (float)data.get("dir_z"));
        scale = new Vector3((float)data.get("scale_x"), (float)data.get("scale_y"), (float)data.get("scale_z"));
    }
    
    public GeneralObject(ZoneArchive zone, String filepath, int game, String objname, Vector3 pos)
    {
        this.zone = zone;
        String[] stuff = filepath.split("/");
        directory = stuff[0];
        layer = stuff[1].toLowerCase();
        file = stuff[2];
        
        data = new Bcsv.Entry();
        
        name = objname;
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
        data.put("Obj_arg1", -1);
        data.put("Obj_arg2", -1);
        data.put("Obj_arg3", -1);
        data.put("Obj_arg4", -1);
        data.put("Obj_arg5", -1);
        data.put("Obj_arg6", -1);
        data.put("Obj_arg7", -1);
        
        data.put("l_id", 0);
        data.put("CameraSetId", -1);
        data.put("SW_APPEAR", -1);
        data.put("SW_DEAD", -1);
        data.put("SW_A",  -1);
        data.put("SW_B", -1);
        if (game == 2)
        {
            data.put("SW_AWAKE", -1);
            data.put("SW_PARAM", -1);
            data.put("ParamScale", 1f);
        }
        else
            data.put("SW_SLEEP", -1);
        
        data.put("CastId", -1);
        data.put("ViewGroupId", -1);
        data.put("ShapeModelNo", (short)-1);
        data.put("CommonPath_ID", (short)-1);
        data.put("ClippingGroupId", (short)-1);
        data.put("GroupId", (short)-1);
        data.put("DemoGroupId", (short)-1);
        data.put("MapParts_ID", (short)-1);
        if (game == 2)
            data.put("Obj_ID", (short)-1);
        
        data.put("MessageId", -1);
        if (game == 2)
            data.put("GeneratorID", (short)-1);
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
        if (zone.gameMask == 2)
            panel.addField("ParamScale", "ParamScale", "float", null, data.get("ParamScale"));

        // TODO nice object args (ObjectDB integration)

        panel.addCategory("obj_args", "Object arguments");
        panel.addField("Obj_arg0", "Obj_arg0", "int", null, data.get("Obj_arg0"));
        panel.addField("Obj_arg1", "Obj_arg1", "int", null, data.get("Obj_arg1"));
        panel.addField("Obj_arg2", "Obj_arg2", "int", null, data.get("Obj_arg2"));
        panel.addField("Obj_arg3", "Obj_arg3", "int", null, data.get("Obj_arg3"));
        panel.addField("Obj_arg4", "Obj_arg4", "int", null, data.get("Obj_arg4"));
        panel.addField("Obj_arg5", "Obj_arg5", "int", null, data.get("Obj_arg5"));
        panel.addField("Obj_arg6", "Obj_arg6", "int", null, data.get("Obj_arg6"));
        panel.addField("Obj_arg7", "Obj_arg7", "int", null, data.get("Obj_arg7"));
        
        panel.addCategory("obj_eventinfo", "Event IDs");
        panel.addField("SW_APPEAR", "SW_APPEAR", "int", null, data.get("SW_APPEAR"));
        panel.addField("SW_DEAD", "SW_DEAD", "int", null, data.get("SW_DEAD"));
        panel.addField("SW_A", "SW_A", "int", null, data.get("SW_A"));
        panel.addField("SW_B", "SW_B", "int", null, data.get("SW_B"));
        if (zone.gameMask == 2)
        {
            panel.addField("SW_AWAKE", "SW_AWAKE", "int", null, data.get("SW_AWAKE"));
            panel.addField("SW_PARAM", "SW_PARAM", "int", null, data.get("SW_PARAM"));
        }
        else
            panel.addField("SW_SLEEP", "SW_SLEEP", "int", null, data.get("SW_SLEEP"));

        panel.addCategory("obj_objinfo", "Object settings");
        panel.addField("l_id", "Object ID", "int", null, data.get("l_id"));
        panel.addField("MessageId", "Message ID", "int", null, data.get("MessageId"));
        if (zone.gameMask == 2)
            panel.addField("GeneratorID", "Generator ID", "int", null, data.get("GeneratorID"));
        
        panel.addField("ViewGroupId", "View group ID", "int", null, data.get("ViewGroupId"));
        panel.addField("CommonPath_ID", "Path ID", "int", null, data.get("CommonPath_ID"));
        panel.addField("ClippingGroupId", "Clipping group ID", "int", null, data.get("ClippingGroupId"));
        panel.addField("GroupId", "Group ID", "int", null, data.get("GroupId"));
        panel.addField("DemoGroupId", "Demo group ID", "int", null, data.get("DemoGroupId"));

        panel.addCategory("obj_misc", "Misc. settings");
        
        panel.addField("CameraSetId", "CameraSetId", "int", null, data.get("CameraSetId"));
        panel.addField("CastId", "CastId", "int", null, data.get("CastId"));
        panel.addField("ShapeModelNo", "ShapeModelNo", "int", null, data.get("ShapeModelNo"));
        panel.addField("MapParts_ID", "MapParts_ID", "int", null, data.get("MapParts_ID"));
        if (zone.gameMask == 2)
            panel.addField("Obj_ID", "Obj_ID", "int", null, data.get("Obj_ID"));
    }
    
    @Override
    public String toString()
    {
        String l = layer.equals("common") ? "Common" : "Layer"+layer.substring(5).toUpperCase();
        return dbInfo.name + " [" + l + "]";
    }
}
