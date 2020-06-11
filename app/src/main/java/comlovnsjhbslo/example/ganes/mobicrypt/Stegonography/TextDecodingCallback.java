package comlovnsjhbslo.example.ganes.mobicrypt.Stegonography;

public interface TextDecodingCallback {
    void onStartTextEncoding();

    void onCompleteTextEncoding(ImageSteganography result);

}
