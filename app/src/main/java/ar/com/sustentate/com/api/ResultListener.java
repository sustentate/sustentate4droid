package ar.com.sustentate.com.api;

/*
 * Created by mzorilla on 11/4/17.
 */

import java.text.ParseException;

public interface ResultListener<T> {
    void loading();
    void finish(T result) throws ParseException;
    void error(Throwable error);
}
