package com.ftdi;

import com.ftdi.FTD2XX.Loader;
import com.sun.jna.Native;

public class FTD2XXInstance {

    
    private static FTD2XX __instance = null;
    
    public static FTD2XX getInstance(){
    	if(__instance==null){
    		__instance=(FTD2XX) Native.loadLibrary(Loader.getNative(), FTD2XX.class);
    	}
    	return __instance;
    }
}
