package com.pwc.capstone.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CapstoneClassUtils {
    
    public static String getDate(Date date) {
        if(date != null) {
            SimpleDateFormat formatter =  new SimpleDateFormat("EEEE, dd MMMM yyyy"); 
            return formatter.format(date); 
        }
        return null;
    }
}
