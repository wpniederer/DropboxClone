import java.nio.file.Files;
import java.nio.file.Path;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.sync.RequestBody;

public class S3Operations {
    private S3Client s3;
    private final String bucket = "storagecube-us-west-1";
    private Path filename;


    S3Operations(Path filename) {
        Region region = Region.US_WEST_1;
        s3 = S3Client.builder().region(region).build();
        this.filename = filename;
    }

    public void add() {
        System.out.println("Adding " + filename.toString() + " in " + bucket);
        s3.putObject(PutObjectRequest.builder().bucket(bucket).key(filename.toString()).build(),
                RequestBody.fromFile(filename));
    }

    public void delete() {
        System.out.println("Deleting a single file at " + filename.toString() + " in " + bucket);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(filename.toString()).build();
        s3.deleteObject(deleteObjectRequest);
    }

    public void update() {
        System.out.println("Modifying " + filename.toString() + " in " + bucket);
        delete();
        add();
    }

    public void list() {
        ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()
                .bucket(bucket)
                .maxKeys(1)
                .build();

        boolean done = false;
        while (!done) {
            ListObjectsV2Response listObjResponse = s3.listObjectsV2(listObjectsReqManual);
            for (S3Object content : listObjResponse.contents()) {
                System.out.println(content.key());
            }

            if (listObjResponse.nextContinuationToken() == null) {
                done = true;
            }

            listObjectsReqManual = listObjectsReqManual.toBuilder()
                    .continuationToken(listObjResponse.nextContinuationToken())
                    .build();
        }

    }






}






