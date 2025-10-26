import java.io.*;
import java.time.*;
import java.util.*;
import test.Test;
import inv.Node;

public class Main {

    //-------------------------------------------------------------------------
    public static void main(String ... args) throws Exception {

        var nodes = new ArrayList<Node>();
        var read  = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            System.out.println("NODE>>");
            
            System.out.print("  IDENT>");
            var ident = read.readLine();
            if (ident == null || ident.trim().equals("")) {
                break;
            }

            System.out.print("   NAME>");
            var name = read.readLine();

            System.out.print("   DESC>");
            var description = read.readLine();

            System.out.print("  CONTAINER?:(y/n)>");
            var res = read.readLine();
            var container = res != null && res.equals("y");

            var node = new Node(ident,name,description,container,null);
            nodes.add(node);

        }

        var ts = LocalDateTime.now();
        var fname = "./csv/INV_" + ts.toString() + ".CSV";
        System.out.println(fname);
        var write = new PrintStream(new FileOutputStream(new File(fname)));
        write.println("\"UUID\";\"IDENT\";\"NAME\";\"DESCRIPTION\";\"CONTAINER\"");
        for (var node : nodes) {

            write.print("\"");
            write.print(node.uuid);
            write.print("\";\"");
            write.print(node.ident);
            write.print("\";\"");
            write.print(node.name);
            write.print("\";\"");
            write.print(node.description);
            write.print("\"");
            write.println();
            
        }
        write.close();

    }
    //-------------------------------------------------------------------------

}
