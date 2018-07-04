
package com.example.rafa.tfg.clases;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okio.Buffer;

public final class CustomTrust {
  private final OkHttpClient client;

  public CustomTrust() {
    X509TrustManager trustManager;
    SSLSocketFactory sslSocketFactory;
    try {
      trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, new TrustManager[] { trustManager }, null);
      sslSocketFactory = sslContext.getSocketFactory();
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }

    client = new OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustManager)
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            })
        .build();
  }

  public OkHttpClient getClient() {
    return client;
  }

  /**
   * Returns an input stream containing one or more certificate PEM files. This implementation just
   * embeds the PEM files in Java strings; most applications will instead read this from a resource
   * file that gets bundled with the application.
   */
  private InputStream trustedCertificatesInputStream() {
    // PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
    // https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
    // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
    // Typically developers will need to get a PEM file from their organization's TLS administrator.
    String comodoRsaCertificationAuthority = ""
      +"-----BEGIN CERTIFICATE-----\n"
      +"MIICmzCCAgSgAwIBAgIJALN4XkKZZNeHMA0GCSqGSIb3DQEBCwUAMGQxETAPBgNV\n"
      +"BAMMCE1pIENhc2EgMSQwIgYDVQQKDBtSYWZhZWxhbGVtYW4xOTg4QGdtYWlsLmNv\n"
      +"bSAxKTAnBgkqhkiG9w0BCQEWGlJhZmFlbGFsZW1hbjE5ODhAZ21haWwuY29tMCAX\n"
      +"DTE4MDcwNDEwMzcyMVoYDzIxMTgwNjEwMTAzNzIxWjBkMREwDwYDVQQDDAhNaSBD\n"
      +"YXNhIDEkMCIGA1UECgwbUmFmYWVsYWxlbWFuMTk4OEBnbWFpbC5jb20gMSkwJwYJ\n"
      +"KoZIhvcNAQkBFhpSYWZhZWxhbGVtYW4xOTg4QGdtYWlsLmNvbTCBnzANBgkqhkiG\n"
      +"9w0BAQEFAAOBjQAwgYkCgYEAyae3YZDt/fu0BnoovLZSkPeHunf9fE87jML2HSKz\n"
      +"rJVsMJPP2JZdt53r0TxVR1SVIZvPjMgXQ6pSHALf+mJaUKleDaFHbxGR7903dXR0\n"
      +"vozwsxXU9MAv9Q90PghivqZfeUQeMWWX7yuki0Z0ziY46JUyCmLl8Sh01JwOtH5m\n"
      +"sO0CAwEAAaNTMFEwHQYDVR0OBBYEFLk2o7jqJn1KS+AEJQg6ADwzI7kOMB8GA1Ud\n"
      +"IwQYMBaAFLk2o7jqJn1KS+AEJQg6ADwzI7kOMA8GA1UdEwEB/wQFMAMBAf8wDQYJ\n"
      +"KoZIhvcNAQELBQADgYEAOvLkBAinHkvFjPvBo3xXx2odBC7MYxjXTkBKSAwVgbBA\n"
      +"XyB0G6BhYyZ5kbLSBBTL6NH12ChNAaA8QNilPF9pg0Nxsz4/nBBAsmEqCJTEMG2i\n"
      +"DFoM966322/KZTD/IBBK6mNHYDS0t14Y3+4zILlAG3i3BhukeeEdA3SjPvAdvcg=\n"
      +"-----END CERTIFICATE-----\n";
    return new Buffer()
        .writeUtf8(comodoRsaCertificationAuthority)
        .inputStream();
  }

  /**
   * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
   * certificates have not been signed by these certificates will fail with a {@code
   * SSLHandshakeException}.
   *
   * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
   * set. This is useful in development where certificate authority-trusted certificates aren't
   * available. Or in production, to avoid reliance on third-party certificate authorities.
   *
   * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
   * the host platform's built-in trust store.
   *
   * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
   *
   * <p>Relying on your own trusted certificates limits your server team's ability to update their
   * TLS certificates. By installing a specific set of trusted certificates, you take on additional
   * operational complexity and limit your ability to migrate between certificate authorities. Do
   * not use custom trusted certificates in production without the blessing of your server's TLS
   * administrator.
   */
  private X509TrustManager trustManagerForCertificates(InputStream in)
      throws GeneralSecurityException {
    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
    Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
    if (certificates.isEmpty()) {
      throw new IllegalArgumentException("expected non-empty set of trusted certificates");
    }

    // Put the certificates a key store.
    char[] password = "password".toCharArray(); // Any password will work.
    KeyStore keyStore = newEmptyKeyStore(password);
    int index = 0;
    for (Certificate certificate : certificates) {
      String certificateAlias = Integer.toString(index++);
      keyStore.setCertificateEntry(certificateAlias, certificate);
    }

    // Use it to build an X509 trust manager.
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
        KeyManagerFactory.getDefaultAlgorithm());
    keyManagerFactory.init(keyStore, password);
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
        TrustManagerFactory.getDefaultAlgorithm());
    trustManagerFactory.init(keyStore);
    TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
    if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
      throw new IllegalStateException("Unexpected default trust managers:"
          + Arrays.toString(trustManagers));
    }
    return (X509TrustManager) trustManagers[0];
  }

  private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
    try {
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      InputStream in = null; // By convention, 'null' creates an empty key store.
      keyStore.load(in, password);
      return keyStore;
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
