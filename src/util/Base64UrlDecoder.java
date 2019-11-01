package util;

/**
 * @since 0.10.0
 */
public class Base64UrlDecoder extends Base64Decoder {

    Base64UrlDecoder() {
        super(Base64.URL_SAFE);
    }

}
