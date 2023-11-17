package jp.co.mukeisoftllc.ex.grpc;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jp.co.mukeisoftllc.ex.quarkus.gpc.FileGrpcServiceGrpc;
import jp.co.mukeisoftllc.ex.quarkus.gpc.ImageWriteRequest;
import jp.co.mukeisoftllc.ex.quarkus.gpc.SavedFileRequest;
import jp.co.mukeisoftllc.ex.quarkus.gpc.TextWriteRequest;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FileGrpcServiceTest {
    private static ManagedChannel channel;

    @BeforeAll
    static void beforeAll() {
        int port = ConfigProvider.getConfig().getValue("quarkus.http.test-port", Integer.class);
        channel = ManagedChannelBuilder.forAddress("127.0.0.1", port)
                .usePlaintext()
                .build();
    }

    @AfterAll
    static void afterAll() {
        if (channel != null) {
            channel.shutdownNow();
            channel = null;
        }
    }

    @Test
    public void testWriteAndReadTextFile() {
        // Arrange.
        final var rand = new Random();
        final var fileSuffix = rand.nextInt();
        final var fileName = "testFile_" + fileSuffix;
        final var stub = FileGrpcServiceGrpc.newBlockingStub(channel);

        // Act & Assert.
        final var saveResult = stub.saveText(TextWriteRequest.newBuilder().setName(fileName).setText("hello world").build());
        assertTrue(saveResult.getResult());
        final var readResult = stub.readText(SavedFileRequest.newBuilder().setName(fileName).build());
        assertEquals("hello world", readResult.getContent());
    }

    @Test
    public void testWriteAndReadBinaryFile() {
        // Arrange.
        final var rand = new Random();
        final var fileSuffix = rand.nextInt();
        final var fileName = "testFile_" + fileSuffix;
        final var expectedContent = "こんにちは世界".getBytes(StandardCharsets.UTF_8);
        final var stub = FileGrpcServiceGrpc.newBlockingStub(channel);

        // Act & Assert.
        final var saveResult = stub.saveImage(ImageWriteRequest.newBuilder().setName(fileName).setContent(ByteString.copyFrom(expectedContent)).build());
        assertTrue(saveResult.getResult());
        final var readResult = stub.readImage(SavedFileRequest.newBuilder().setName(fileName).build());
        assertArrayEquals(expectedContent, readResult.getContent().toByteArray());
        assertEquals("こんにちは世界", new String(readResult.getContent().toByteArray(), StandardCharsets.UTF_8));
    }

}
