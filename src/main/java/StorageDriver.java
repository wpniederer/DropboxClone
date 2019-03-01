import org.apache.tika.Tika;

import java.io.IOException;
import java.nio.file.*;

import static com.sun.jmx.mbeanserver.Util.cast;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.*;

public class StorageDriver {

    // Watches files and syncs as needed
    private static void driver() throws IOException{
        FileWatcher watchDir = new FileWatcher(Paths.get("my_dir"), true);

        for (;;) {

            WatchKey key = watchDir.getKey();
            if (watchDir.keyToDir(key) == null) {
                continue;
            }

            Path dir = watchDir.keyToDir(key);

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path filename = ev.context();
                Path child = dir.resolve(filename);
                Tika tika = new Tika();

                try {
                    // print out event
                    System.out.format("%s: %s\n", event.kind().name(), child);
                    System.out.println(tika.detect(child));

                    // ignore file system files and temp files
                    if (!"application/octet-stream".equals(tika.detect(child))) {
                        System.out.println("this is an syncable file");

                        if (kind == ENTRY_CREATE) {
                            new S3Operations(child).add();
                        }

                        if (kind == ENTRY_MODIFY) {
                            new S3Operations(child).update();
                        }

                        new S3Operations(child).list();
                    }

                } catch (NoSuchFileException notFound) {
                    if (kind == ENTRY_DELETE) {
                        new S3Operations(child).delete();
                    }
                    new S3Operations(child).list();

                } catch (IOException x) {
                    System.err.println(x);
                }




                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (kind == ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            watchDir.registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
//                keys.remove(key);
                watchDir.removeKeyFromKeys(key);

                // all directories are inaccessible
                if (watchDir.isEmpty()) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // Watches for Directory changes, syncs changes
        driver();

    }

}
