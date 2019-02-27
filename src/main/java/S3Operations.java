import java.io.File;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

public class S3Operations {
    private S3Client s3;
    private final String bucket = "storagecube-us-west-1";
    private String filename;


    S3Operations(String filename) {
        Region region = Region.US_WEST_1;
        s3 = S3Client.builder().region(region).build();
        this.filename = filename;
    }

    public void add() {
        s3.putObject(PutObjectRequest.builder().bucket(bucket).key(filename).build(),
                RequestBody.fromFile(new File(filename)));
    }

    public void delete() {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(filename).build();
        s3.deleteObject(deleteObjectRequest);
    }

    public void update() {
        delete();
        add();
    }




}






