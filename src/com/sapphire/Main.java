package com.sapphire;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
    Font structure
        Bit mapped font values stored as a block

    Per glyph
        pointer into the bitmap
        width of glyph in pixels
        height of glyph in pixels
        distance to advance along x axis
        x distance from position to upper left corner
        y distance from position to upper left corner

    Font as a whole
        pointer to bitmap (not used)
        pointer to glyph array (not used)
        first ASCII character
        last ASCII character
        advance distance (ie, width of the font)
 */

public class Main {

    public static void main(String[] args) {
        List<Short> bitmaps = new ArrayList<Short>();
        List<Short> glyphs = new ArrayList<Short>();

        Short first = 0;
        Short last = 0;
        Short fontWidth = 0;

        String fn = new String("Tiny3x3a2pt7b.h");

        int indent = 0;

        try
        {
            StreamTokenizer st = new StreamTokenizer( new BufferedReader( new FileReader( fn )));
//            st.ordinaryChar( '{' );
//            st.ordinaryChar( '}' );

            while( st.ttype != '{' )
                st.nextToken();

            indent = 1;
            while( indent > 0 )
            {
                st.nextToken();
                if( st.ttype == '}' )   indent--;
                if( st.ttype == '{' )   indent++;
                if (st.ttype == StreamTokenizer.TT_WORD)
                    bitmaps.add( Short.parseShort( st.sval.replaceAll( "x", "" ), 16 ) );
            }


            while( st.ttype != '{' )
                st.nextToken();

            indent = 1;
            while( indent > 0 )
            {
                st.nextToken();
                if( st.ttype == '}' )   indent--;
                if( st.ttype == '{' )   indent++;
                if (st.ttype == StreamTokenizer.TT_NUMBER)
                    glyphs.add((short) st.nval);
            }

            while( st.ttype != '{' )
                st.nextToken();

            // now into the parameter list for the font, five parameters, we need the third, fourth, fifth.
            // parse off two commas, that puts us at the third param
            st.nextToken();
            while( st.ttype != ',' )
                st.nextToken();

            st.nextToken();
            while( st.ttype != ',' )
                st.nextToken();

            first = getShort( st );

            while( st.ttype != ',' )
                st.nextToken();

            last = getShort( st );

            while( st.ttype != ',' )
                st.nextToken();

            fontWidth = getShort( st );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static short getShort( StreamTokenizer st ) throws IOException {
        short val = 0;

        st.nextToken();

        if( st.ttype == StreamTokenizer.TT_NUMBER)
            val = (short) st.nval;

        if( st.ttype == StreamTokenizer.TT_WORD)
            val = Short.parseShort( st.sval, 16 );
        st.nextToken();

        if( st.ttype == StreamTokenizer.TT_WORD)
            val = Short.parseShort( st.sval.replaceAll( "x", "" ), 16 );

        return val;
    }
}
