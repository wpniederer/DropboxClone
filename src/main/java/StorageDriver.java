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

    public static void main(String[] args) throws IOException {
        // Watches for Directory changes, syncs changes
        new FileWatcher(Paths.get("test_dir"), true).processEvents();
    }

}
