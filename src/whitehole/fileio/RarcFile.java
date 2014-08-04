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

package whitehole.fileio;

import java.io.IOException;

public class RarcFile extends MemoryFile
{
    public RarcFile(RarcFilesystem fs, String fullname) throws IOException
    {
        super(fs.getFileContents(fullname));
        
        filesystem = fs;
        fileName = fullname;
    }

    @Override
    public void save() throws IOException
    {
        filesystem.reinsertFile(this);
    }


    public RarcFilesystem filesystem;
    public String fileName;
}
