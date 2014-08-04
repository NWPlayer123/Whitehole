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

// inspired from http://www.azillionmonkeys.com/qed/hash.html

public class SuperFastHash 
{
    public static long calculate(byte[] data, long start, int offset, int len)
    {
        long hash = start & 0xFFFFFFFF;
        long tmp;
        int rem;
        
        if (len < 1) return hash;
        
        rem = len & 3;
        len >>>= 2;
        
        int pos = offset;
        for (; len > 0; len--)
        {
            hash += (data[pos++] | (data[pos++] << 8));
            tmp = ((data[pos++] | (data[pos++] << 8)) << 11) ^ hash;
            hash = ((hash << 16) ^ tmp);
            hash += (hash >>> 11);
        }
        
        switch (rem)
        {
            case 3:
                hash += (data[pos++] | (data[pos++] << 8));
                hash ^= (hash << 16);
                hash ^= (data[pos++] << 18);
                hash += (hash >>> 11);
                break;

            case 2:
                hash += (data[pos++] | (data[pos++] << 8));
                hash ^= (hash << 11);
                hash += (hash >>> 17);
                break;

            case 1:
                hash += data[pos++];
                hash ^= (hash << 10);
                hash += (hash >>> 1);
                break;
        }

        hash ^= (hash << 3);
        hash += (hash >>> 5);
        hash ^= (hash << 4);
        hash += (hash >>> 17);
        hash ^= (hash << 25);
        hash += (hash >>> 6);

        return hash & 0xFFFFFFFF;
    }
}
