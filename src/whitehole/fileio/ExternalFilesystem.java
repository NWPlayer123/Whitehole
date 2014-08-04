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

import java.io.*;
import java.util.*;

public class ExternalFilesystem implements FilesystemBase
{
    public ExternalFilesystem(String basedir) throws IOException
    {
        if (basedir.endsWith("/") || basedir.endsWith("\\"))
            basedir = basedir.substring(0, basedir.length() - 1);

        baseDirectory = new File(basedir);
        if (!baseDirectory.exists()) throw new IOException("Directory '" + basedir + "' doesn't exist");
        if (!baseDirectory.isDirectory()) throw new IOException(basedir + " isn't a directory");
    }
    
    @Override
    public void save()
    {
    }
    
    @Override
    public void close()
    {
    }


    @Override
    public List<String> getDirectories(String directory)
    {
        directory = directory.substring(1);
        
        File[] files = new File(baseDirectory, directory).listFiles();
        List<String> ret = new ArrayList<>();

        for (File file: files)
        {
            if (!file.isDirectory()) continue;
            ret.add(file.getName());
        }

        return ret;
    }

    @Override
    public boolean directoryExists(String directory)
    {
        directory = directory.substring(1);
        
        File dir = new File(baseDirectory, directory);
        return dir.exists() && dir.isDirectory();
    }


    @Override
    public List<String> getFiles(String directory)
    {
        directory = directory.substring(1);
        
        File[] files = new File(baseDirectory, directory).listFiles();
        List<String> ret = new ArrayList<>();

        for (File file: files)
        {
            if (!file.isFile()) continue;
            ret.add(file.getName());
        }

        return ret;
    }

    @Override
    public boolean fileExists(String filename)
    { 
        filename = filename.substring(1);
        
        File file = new File(baseDirectory, filename);
        return file.exists() && file.isFile();
    }

    @Override
    public FileBase openFile(String filename) throws FileNotFoundException
    {
        if (!fileExists(filename)) throw new FileNotFoundException("File " + filename + "doesn't exist");
        return new ExternalFile(baseDirectory.getPath() + filename);
    }
    
    @Override
    public void createFile(String parent, String newfile)
    {
        throw new UnsupportedOperationException("not done lol");
    }

    @Override
    public void renameFile(String file, String newname)
    {
        throw new UnsupportedOperationException("not done lol");
    }

    @Override
    public void deleteFile(String file)
    {
        throw new UnsupportedOperationException("not done lol");
    }


    private File baseDirectory;
}
