package info.wiwitadityasaputra.config;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AppJWTManager {
	private static Logger logger = LogManager.getLogger(AppJWTManager.class);

	private static String ENCODED_KEY = "rcb7I2sVE2+hEB5M9lXP7w==";

	public static SecretKey getSecretKey() {
		byte[] decodedKey = Base64.getDecoder().decode(ENCODED_KEY);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	}

	public static AuthModel fromQueryParamAuth(String queryParamAuth) {
		SecretKey secretKey = getSecretKey();
		String json = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(queryParamAuth).getBody().getSubject();
		logger.info("json = " + json);
		Gson gson = new Gson();
		return gson.fromJson(json, AuthModel.class);
	}

	public static String fromAuthModel(AuthModel authModel) {
		Gson gson = new Gson();
		return Jwts.builder().setSubject(gson.toJson(authModel)).signWith(SignatureAlgorithm.HS512, getSecretKey())
				.compact();
	}
}
