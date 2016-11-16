import sun.misc.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Ilya on 16.11.16.
 */
public class ZipAndMove {

    public static void main(String[] args) throws Exception {

        if (args.length != 2){
            System.exit(1);
        }

        String dir1 = args[0]; //path to dir_1
        String path = args[1]; //path to dir_2/file.*

        int k = 0;
        k = path.lastIndexOf('/');
        if (k == path.length() - 1){
            System.exit(1);
        }

        String name = path.substring(k + 1, path.length()); //name of file
        String dir2 = path.substring(0, k); //path to dir_2

        File file1 = new File(dir1);
        File file2 = new File(dir2);

        if ( file1.exists() && file2.exists() && file1.isDirectory() && file2.isDirectory() ){
            ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(path));
            toZip (file1, zOut, "");
            zOut.close();
        } else System.exit(1);

    }

    public static void toZip(File dir, ZipOutputStream zOut, String base) throws IOException{
        String entryName = base + dir.getName(); //path in zip
        if (dir.listFiles().length > 0) {
            for (File f : dir.listFiles()) {
                if (f.isDirectory()) {
                    toZip(f, zOut, entryName + "/");
                } else {
                    zOut.putNextEntry(new ZipEntry(entryName + "/" + f.getName()));
                    write (new FileInputStream(f), zOut);
                }
            }
        } else {
            zOut.putNextEntry(new ZipEntry(entryName + "/"));
        }
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }

}
