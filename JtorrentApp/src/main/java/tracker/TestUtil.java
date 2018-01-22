package tracker;

import org.azeckoski.reflectutils.ReflectUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Adrian Kalata on 2017-02-24.
 */
public class TestUtil {


    /**
     * The constant TEST_NAME.
     */
    public static final String TEST_NAME = "testname";

    /**
     * Private constructor.
     */
    private TestUtil() {
        // private constructor
    }

    /**
     * Generate random string string.
     *
     * @return the string
     */
    public static String generateRandomString(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    /**
     * Get test collection v.
     *
     * @param <V>    the type parameter
     * @param <K>    the type parameter
     * @param clazz  the clazz
     * @param params the params
     * @return the v
     */
    @SafeVarargs
    public static <V extends Collection<K>, K> V getTestCollection(Class<V> clazz, K... params){
        V collection = ReflectUtils.getInstance().constructClass(clazz);
        Arrays.asList(params).forEach(collection::add);
        return collection;
    }

}
