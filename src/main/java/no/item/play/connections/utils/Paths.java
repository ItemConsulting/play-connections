package no.item.play.connections.utils;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Paths {
    public static String combineToUrl(String... urlParts) {
        return stripMultipleSlases(Arrays.stream(urlParts).collect(Collectors.joining("/")));
    }

    public static String stripMultipleSlases(String url){
        return url.replaceAll("([^:])\\/{2}", "$1/") ;// remove two slashes
    }
}
