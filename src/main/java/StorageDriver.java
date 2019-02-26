import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// Create a local file for keeping track of changes
// Check for file changes using either FileEntry or WatchService
        // A change can be either:
            // Adding a new file
            // Removing a file
            // Editing an existing file
// Sync those changes to the s3 instance
public class StorageDriver {


    private static void usage() {
        System.err.println("usage: java FileWatcher [-r] dir");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {

        // parse arguments
        if (args.length == 0 || args.length > 2)
            usage();
        boolean recursive = false;
        int dirArg = 0;
        if (args[0].equals("-r")) {
            if (args.length < 2)
                usage();
            recursive = true;
            dirArg++;
        }

        // register directory and process its events
        Path dir = Paths.get(args[dirArg]);
        new FileWatcher(dir, recursive).processEvents();
    }

}
