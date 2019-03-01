import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWatcherTest {
    private FileWatcher fileWatcher;
    private Path parentDir = Paths.get("unit-test-dir");

    @Before
    public void setup() throws IOException {
        fileWatcher = new FileWatcher(parentDir);
    }

    @Test
    public void testRegisterAll() throws IOException {
        FileWatcher testRegister = new FileWatcher();
        testRegister.registerAll(parentDir);

        if (!testRegister.dirList().containsAll(fileWatcher.dirList())) {
            Assert.fail();
        }
    }

    @Test
    public void testIsEmpty() {
        Assert.assertFalse(fileWatcher.isEmpty());
    }

    // NOTE: These don't work, just process forever
//    @Test
//    public void testGetKey() throws InterruptedException{
//        Assert.assertNotNull(fileWatcher.getKey());
//    }

//    @Test
//    public void testKeysToDir() throws InterruptedException{
//        Path dir = fileWatcher.keyToDir(fileWatcher.getKey());
//        Assert.assertNotNull(dir);
//    }



}
