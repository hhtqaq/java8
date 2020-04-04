package com.tvt.hht;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考：https://docs.aws.amazon.com/zh_cn/sdk-for-java/v2/developer-guide/examples-s3-presign.html
 *
 * @author hht
 * @date 2020/4/3 14:22
 */
public class S3Test {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("111");
        list.add("111");
        list.add("111");

        for (String s : list) {
            System.out.println(s);
        }
    }

    public static String putObjectUrl() {
        String bucketName = "";
        String keyName = "";
        // Create a S3Presigner by using the default AWS Region and credentials
        S3Presigner presigner = S3Presigner.create();

        PresignedPutObjectRequest presignedRequest =
                presigner.presignPutObject(z -> z.signatureDuration(Duration.ofMinutes(10))
                        .putObjectRequest(por -> por.bucket(bucketName).key(keyName)));

        System.out.println("Pre-signed URL to upload a file to: " +
                presignedRequest.url());
        System.out.println("Which HTTP method needs to be used when uploading a file: " +
                presignedRequest.httpRequest().method());

        // Upload content to the bucket by using this URL
        URL url = presignedRequest.url();

        return url.toString();
    }
}
