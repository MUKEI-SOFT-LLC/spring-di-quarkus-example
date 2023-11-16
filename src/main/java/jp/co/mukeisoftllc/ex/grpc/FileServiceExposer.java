package jp.co.mukeisoftllc.ex.grpc;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;
import jp.co.mukeisoftllc.ex.quarkus.gpc.*;
import jp.co.mukeisoftllc.ex.spring.world.services.FileService;

@GrpcService
public class FileServiceExposer extends FileGrpcServiceGrpc.FileGrpcServiceImplBase {

    @Inject
    private FileService fileService;

    @Override
    public void saveText(TextWriteRequest request, StreamObserver<Response> responseObserver) {
        try  {
            fileService.writeText(request.getName(), request.getText());
            responseObserver.onNext(Response.newBuilder().setResult(true).build());
            responseObserver.onCompleted();
        } catch (Throwable th) {
            responseObserver.onError(th);
        }
    }

    @Override
    public void saveImage(ImageWriteRequest request, StreamObserver<Response> responseObserver) {
        try {
            fileService.writeImage(request.getName(), request.getContent().toByteArray());
            responseObserver.onNext(Response.newBuilder().setResult(true).build());
            responseObserver.onCompleted();
        } catch (Throwable th) {
            responseObserver.onError(th);
        }
    }

    @Override
    public void readText(SavedFileRequest request, StreamObserver<TextReadResponse> responseObserver) {
        try {
            final var result = fileService.readText(request.getName());
            responseObserver.onNext(TextReadResponse.newBuilder().setContent(result).build());
            responseObserver.onCompleted();
        } catch (Throwable th) {
            responseObserver.onError(th);
        }
    }

    @Override
    public void readImage(SavedFileRequest request, StreamObserver<ImageReadResponse> responseObserver) {
        try {
            final var result = fileService.readImage(request.getName());
            responseObserver.onNext(ImageReadResponse.newBuilder().setContent(ByteString.copyFrom(result)).build());
            responseObserver.onCompleted();
        } catch (Throwable th) {
            responseObserver.onError(th);
        }
    }
}
