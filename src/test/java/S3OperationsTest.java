import java.nio.file.Paths;

import io.findify.s3mock.S3Mock;
import org.junit.*;

public class S3OperationsTest {
    private S3Operations s3Ops = new S3Operations(Paths.get("unit-test-dir/.png"), true);
    private S3Mock mockAPI = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();

    @Before
    public void setup() {
        mockAPI.start();
    }

    @Test
    public void testAdd() {
        s3Ops.add();
    }

    @Test
    public void testDelete() {
        s3Ops.delete();
    }

    @Test
    public void testUpdate() {
        s3Ops.update();
    }

    @After
    public void cleanUp() {
        mockAPI.shutdown();
    }

}
