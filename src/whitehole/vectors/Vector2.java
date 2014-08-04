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

package whitehole.vectors;

public class Vector2
{
    public Vector2()
    {
        x = y = 0f;
    }
    
    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vector2(Vector2 copy)
    {
        this.x = copy.x;
        this.y = copy.y;
    }
    
    
    public static boolean roughlyEqual(Vector2 a, Vector2 b)
    {
        float epsilon = 0.00001f;
        if (Math.abs(a.x - b.x) > epsilon) return false;
        if (Math.abs(a.y - b.y) > epsilon) return false;
        return true;
    }
    
    public float length()
    {
        return (float)Math.sqrt(x * x + y * y);
    }
    
    public static void normalize(Vector2 v, Vector2 out)
    {
        float len = v.length();
        if (len < 0.000001f) len = 1f;
        float x = v.x / len,
              y = v.y / len;
        out.x = x; out.y = y;
    }
    
    public static void add(Vector2 a, Vector2 b, Vector2 out)
    {
        out.x = a.x + b.x;
        out.y = a.y + b.y;
    }
    
    public static void subtract(Vector2 a, Vector2 b, Vector2 out)
    {
        out.x = a.x - b.x;
        out.y = a.y - b.y;
    }
    
    
    public float x, y;
}
