import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashSet;

public class IntegrationTest {
    private int wait = 3000;
    private Path parentDir = Paths.get("test_dir");
    Path pathToFile = Paths.get("test_dir/testFile.txt");

    @Before
    public void setup() throws IOException {
        Files.createDirectories(parentDir);
    }


    @Test
    public void integrationTest() {
        HashSet<String> bucketContents;
        try {
            String fileName = "testFile.txt";
            File tagFile = new File("test_dir", fileName+".txt");
            tagFile.createNewFile();
            Files.write(pathToFile, Collections.singleton("hello"));
            new S3Operations(pathToFile).add();

            Thread.sleep(wait);
            bucketContents = new S3Operations(pathToFile).getBucketContents();
            if (!bucketContents.contains(pathToFile.toString())) {
                Assert.fail();
            }

            Thread.sleep(wait);
            Files.write(pathToFile, Collections.singleton("goodbye"));
            new S3Operations(pathToFile).update();

            Thread.sleep(wait);
            bucketContents = new S3Operations(pathToFile).getBucketContents();
            if (!bucketContents.contains(pathToFile.toString())) {
                Assert.fail();
            }

            Thread.sleep(wait);
            tagFile.delete();
            new S3Operations(pathToFile).delete();

            Thread.sleep(wait);
            bucketContents = new S3Operations(pathToFile).getBucketContents();
            if (!bucketContents.isEmpty()) {
                Assert.fail();
            }

        }
        catch(IOException io){
            // Fail
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @After
    public void cleanUp() throws IOException {
        Files.delete(Paths.get("test_dir/testFile.txt"));
        Files.delete(Paths.get("test_dir"));
    }

}
