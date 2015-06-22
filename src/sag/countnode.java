package sag;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class countnode {

    public static void main(String[] args) throws IOException {
       
        
        String loc="c:/output.txt"; 
        String grepfor = "Folder:C:/SoftwareAG/IntegrationServer";
        Path path = Paths.get(loc);

        String report = searchFor(grepfor, path);

        System.out.println(report);

    }

    private static final int MAPSIZE = 4 * 1024 ; // 4K - make this * 1024 to 4MB in a real system.

    private static String searchFor(String grepfor, Path path) throws IOException {
        final byte[] tosearch = grepfor.getBytes(StandardCharsets.UTF_8);
        StringBuilder report = new StringBuilder();
        int padding = 1; // need to scan 1 character ahead in case it is a word boundary.
        int linecount = 0;
        int matches = 0;
        boolean inword = false;
        boolean scantolineend = false;
        try  {
        	FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
            final long length = channel.size();
            int pos = 0;
            while (pos < length) {
                	long remaining = length - pos;
                	// int conversion is safe because of a safe MAPSIZE.. Assume a reaosnably sized tosearch.
                	int trymap = MAPSIZE + tosearch.length + padding;
                	int tomap = (int)Math.min(trymap, remaining);
                	// different limits depending on whether we are the last mapped segment.
                	int limit = trymap == tomap ? MAPSIZE : (tomap - tosearch.length);
                	MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY, pos, tomap);
                	System.out.println("Mapped from " + pos + " for " + tomap);
                	pos += (trymap == tomap) ? MAPSIZE : tomap;
                	for (int i = 0; i < limit; i++)
                		{
                			final byte b = buffer.get(i);
                			if (scantolineend) 
                				{
                					if (b == '\n') 
                						{
                							scantolineend = false;
                							inword = false;
                							linecount ++;
                						}
                				} 
                					else if (b == '\n') 
                						{
                							linecount++;
                							inword = false;
                						} 
                					else if (b == '\r' || b == ' ') 
                							{
                								inword = false;
                							} 
                					else if (!inword) 
                								{
                									if (wordMatch(buffer, i, tomap, tosearch)) 
                										{
	                											matches++;
	                											i += tosearch.length - 1;
	                											if (report.length() > 0) 
	                												{
	                													report.append(", ");
	                												}
	                											report.append(linecount);
	                											scantolineend = true;
                										} 
                									else {
                													inword = true;
                												}
                								}
                		}
            }
        }catch(IOException exception){}
        return "Times found at--" + matches + "\nWord found at--" + report;
    
    }

    private static boolean wordMatch(MappedByteBuffer buffer, int pos, int tomap, byte[] tosearch) {
        //assume at valid word start.
        for (int i = 0; i < tosearch.length; i++) {
            if (tosearch[i] != buffer.get(pos + i)) {
                return false;
            }
        }
        byte nxt = (pos + tosearch.length) == tomap ? (byte)' ' : buffer.get(pos + tosearch.length); 
        return nxt == ' ' || nxt == '\n' || nxt == '\r';
    }
}